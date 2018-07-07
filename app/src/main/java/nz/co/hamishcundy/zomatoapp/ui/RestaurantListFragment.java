package nz.co.hamishcundy.zomatoapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nz.co.hamishcundy.zomatoapp.R;
import nz.co.hamishcundy.zomatoapp.network.NetworkInterfaceProvider;
import nz.co.hamishcundy.zomatoapp.network.Restaurant;
import nz.co.hamishcundy.zomatoapp.network.SearchResponse;
import nz.co.hamishcundy.zomatoapp.network.ZomatoApi;

public class RestaurantListFragment extends Fragment {

    @BindView(R.id.progress_loading)
    ProgressBar loadingProgress;

    @BindView(R.id.recycler_restaurants)
    RecyclerView restaurantsRecycler;

    @BindView(R.id.text_error)
    TextView errorLabel;

    public static final int ABBOTSFORD_ENTITY_ID = 98284;


    private ZomatoApi zomatoApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zomatoApi = NetworkInterfaceProvider.buildZomatoApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRestaurants();

    }

    private void loadRestaurants(){
        showLoadingIndicator();
        if(deviceHasInternet()) {
            //internet available, go to api
            zomatoApi.searchRestaurants(ABBOTSFORD_ENTITY_ID, "subzone",null, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<SearchResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(SearchResponse searchResponse) {
                            showRestaurants(searchResponse.restaurants);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showNetworkError();
                        }
                    });
        }else{//no internet, check cache

        }
    }



    private boolean deviceHasInternet() {
        return true;
    }

    private void showRestaurants(List<Restaurant> restaurants) {
        restaurantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantsRecycler.setAdapter(new RestaurantAdapter(restaurants));

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

        private final List<Restaurant> restaurantList;

        public RestaurantAdapter(List<Restaurant> restaurantList){
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

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Restaurant restaurant){
            nameLabel.setText(restaurant.name);
            addressLabel.setText(restaurant.location.address);

            Glide.with(RestaurantListFragment.this).load(restaurant.featuredImage).into(photoImageview);


        }
    }
}
