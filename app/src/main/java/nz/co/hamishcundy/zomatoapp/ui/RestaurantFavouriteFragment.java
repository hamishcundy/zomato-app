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
        showRestaurantList();
    }

    @Override
    public RealmResults<RestaurantModel> getRestaurantsToDisplay() {
        return realm.where(RestaurantModel.class).equalTo("favourite", true).findAll();

    }
}
