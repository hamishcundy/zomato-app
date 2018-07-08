package nz.co.hamishcundy.zomatoapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import nz.co.hamishcundy.zomatoapp.R;
import nz.co.hamishcundy.zomatoapp.domain.RestaurantModel;
import nz.co.hamishcundy.zomatoapp.network.NetworkInterfaceProvider;
import nz.co.hamishcundy.zomatoapp.network.model.RestaurantDetails;
import nz.co.hamishcundy.zomatoapp.network.model.Restaurant;
import nz.co.hamishcundy.zomatoapp.network.model.SearchResponse;
import nz.co.hamishcundy.zomatoapp.network.ZomatoApi;

public class RestaurantListFragment extends Fragment {

    @BindView(R.id.progress_loading)
    ProgressBar loadingProgress;

    @BindView(R.id.recycler_restaurants)
    RecyclerView restaurantsRecycler;

    @BindView(R.id.text_error)
    TextView errorLabel;



    private ZomatoApi zomatoApi;
    Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zomatoApi = NetworkInterfaceProvider.buildZomatoApi();
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadRestaurants();
    }

    void loadRestaurants(){
        showLoadingIndicator();
        if(deviceHasInternet()) {
            //internet available, go to api
            zomatoApi.searchRestaurants("Abbotsford",10,"rating", null)
                    .flatMap(new Function<SearchResponse, SingleSource<List<RestaurantModel>>>() {
                        @Override
                        public SingleSource<List<RestaurantModel>> apply(SearchResponse searchResponse) throws Exception {
                            List<RestaurantModel> restaurants = new ArrayList<>();
                            for(Restaurant restaurant:searchResponse.restaurants){
                                restaurants.add(new RestaurantModel(restaurant));
                            }
                            return Single.just(restaurants);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<RestaurantModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<RestaurantModel> restaurants) {
                            cacheRestaurants(restaurants);
                            showRestaurantList(restaurants);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showNetworkError();
                        }
                    });
        }else{//no internet, check cache
            List<RestaurantModel> cached = fetchFromCache();
            if(cached != null && !cached.isEmpty()){//cache hit
                showRestaurantList(cached);
            }else{//cache miss
                showNetworkError();
            }
        }
    }

    private void cacheRestaurants(List<RestaurantModel> restaurants) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(restaurants);
        realm.commitTransaction();
    }

    private List<RestaurantModel> fetchFromCache(){
        RealmResults<RestaurantModel> queryResults = realm.where(RestaurantModel.class).findAll();
        if(queryResults.size() == 0){
            return null;
        }
        return realm.copyFromRealm(queryResults);
    }

    /**Note: this will only confirm the device is connected to a network, not whether there is access to the internet
     *
     */
    private boolean deviceHasInternet() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void showRestaurantList(List<RestaurantModel> restaurants) {
        restaurantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantsRecycler.setAdapter(new RestaurantAdapter(restaurants));

        showRestaurants();
    }

    void showRestaurants(){
        loadingProgress.setVisibility(View.GONE);
        errorLabel.setVisibility(View.GONE);
        restaurantsRecycler.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        loadingProgress.setVisibility(View.VISIBLE);
        errorLabel.setVisibility(View.GONE);
        restaurantsRecycler.setVisibility(View.GONE);
    }

    private void showNetworkError() {
        loadingProgress.setVisibility(View.GONE);
        errorLabel.setVisibility(View.VISIBLE);
        restaurantsRecycler.setVisibility(View.GONE);
    }


    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

        private final List<RestaurantModel> restaurantList;

        public RestaurantAdapter(List<RestaurantModel> restaurantList){
            this.restaurantList = restaurantList;
        }

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
            return new RestaurantViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            holder.bind(restaurantList.get(position));
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }
    }



    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_restaurant_name)
        TextView nameLabel;

        @BindView(R.id.text_restaurant_address)
        TextView addressLabel;

        @BindView(R.id.image_restaurant_photo)
        ImageView photoImageview;

        @BindView(R.id.button_favourite)
        ToggleButton favouriteButton;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final RestaurantModel restaurantDetails){
            //Set label text
            nameLabel.setText(restaurantDetails.getName());
            addressLabel.setText(restaurantDetails.getAddress());
            favouriteButton.setChecked(restaurantDetails.isFavourite());
            favouriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(restaurantDetails);
                    realm.commitTransaction();
                }
            });


            //Image display setup
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            //Load image
            Glide.with(RestaurantListFragment.this).load(restaurantDetails.getImageUrl())
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoImageview);


        }
    }
}
