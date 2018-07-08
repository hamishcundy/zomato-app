package nz.co.hamishcundy.zomatoapp.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import nz.co.hamishcundy.zomatoapp.R;
import nz.co.hamishcundy.zomatoapp.domain.RestaurantModel;

public class RestaurantRealmAdapter extends RealmRecyclerViewAdapter<RestaurantModel, RestaurantViewHolder> {

    private final Fragment fragment;

    public RestaurantRealmAdapter(OrderedRealmCollection<RestaurantModel> data, Fragment fragment){
        super(data, true);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(v, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
