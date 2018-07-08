package nz.co.hamishcundy.zomatoapp.ui;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import nz.co.hamishcundy.zomatoapp.R;
import nz.co.hamishcundy.zomatoapp.domain.RestaurantModel;

public class RestaurantFavouriteFragment extends RestaurantListFragment {

    @Override
    void loadRestaurants() {
        RealmResults<RestaurantModel> results = realm.where(RestaurantModel.class).equalTo("favourite", true).findAll();

        showFavouriteRestaurantList(results);
    }

    private void showFavouriteRestaurantList(RealmResults<RestaurantModel> results) {
        restaurantsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantsRecycler.setAdapter(new FavouriteRestaurantsAdapter(results));

        showRestaurants();
    }

    public class FavouriteRestaurantsAdapter extends RealmRecyclerViewAdapter<RestaurantModel, RestaurantViewHolder> {

        public FavouriteRestaurantsAdapter(OrderedRealmCollection<RestaurantModel> data){
            super(data, true);
        }

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
            return new RestaurantViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            holder.bind(getItem(position));
        }
    }
}
