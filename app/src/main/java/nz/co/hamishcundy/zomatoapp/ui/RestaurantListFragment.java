package nz.co.hamishcundy.zomatoapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.hamishcundy.zomatoapp.R;
import nz.co.hamishcundy.zomatoapp.network.Restaurant;

public class RestaurantListFragment extends Fragment {

    @BindView(R.id.progress_loading)
    ProgressBar loadingProgress;

    @BindView(R.id.recycler_restaurants)
    RecyclerView restaurantsRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantItem> {

        private final List<Restaurant> restaurantList;

        public RestaurantAdapter(List<Restaurant> restaurantList){
            this.restaurantList = restaurantList;
        }

        @NonNull
        @Override
        public RestaurantItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
            return new RestaurantItem(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantItem holder, int position) {
            holder.bind(restaurantList.get(position));
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }
    }



    public class RestaurantItem extends RecyclerView.ViewHolder {

        @BindView(R.id.text_restaurant_name)
        TextView nameLabel;

        @BindView(R.id.text_restaurant_address)
        TextView addressLabel;

        @BindView(R.id.image_restaurant_photo)
        ImageView photoImageview;

        public RestaurantItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Restaurant restaurant){
            nameLabel.setText(restaurant.name);
            addressLabel.setText(restaurant.location.address);


        }
    }
}
