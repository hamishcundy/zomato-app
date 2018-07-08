package nz.co.hamishcundy.zomatoapp.ui;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import nz.co.hamishcundy.zomatoapp.R;
import nz.co.hamishcundy.zomatoapp.domain.RestaurantModel;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    private final Fragment fragment;
    @BindView(R.id.text_restaurant_name)
    TextView nameLabel;

    @BindView(R.id.text_restaurant_address)
    TextView addressLabel;

    @BindView(R.id.image_restaurant_photo)
    ImageView photoImageview;

    @BindView(R.id.button_favourite)
    ToggleButton favouriteButton;
    private RestaurantModel item;

    public RestaurantViewHolder(View itemView, Fragment fragment) {
        super(itemView);
        this.fragment = fragment;
        ButterKnife.bind(this, itemView);
    }

    public void bind(final RestaurantModel restaurantDetails){
        this.item = restaurantDetails;
        //Set label text
        nameLabel.setText(restaurantDetails.getName());
        addressLabel.setText(restaurantDetails.getAddress());

        favouriteButton.setOnCheckedChangeListener(null);

        favouriteButton.setChecked(restaurantDetails.isFavourite());

        favouriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(item.isFavourite() != b) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    item.setFavourite(b);
                    realm.commitTransaction();
                }
            }
        });



        //Image display setup
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        //Load image
        Glide.with(fragment).load(restaurantDetails.getImageUrl())
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoImageview);


    }
}
