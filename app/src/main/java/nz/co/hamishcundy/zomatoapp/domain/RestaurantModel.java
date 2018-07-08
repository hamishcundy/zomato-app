package nz.co.hamishcundy.zomatoapp.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import nz.co.hamishcundy.zomatoapp.network.model.Restaurant;

/**Domain object to represent a restaurant (easier for caching purposes)
 * Would use autovalue here but doesn't play nicely with Realm
 *
 */
public class RestaurantModel extends RealmObject{

    @PrimaryKey
    private String name;

    private String address;
    private String imageUrl;
    private boolean favourite;

    public RestaurantModel(){}

    public RestaurantModel(Restaurant restaurant){
        this.name = restaurant.restaurantDetails.name;
        this.address = restaurant.restaurantDetails.location.address;
        this.imageUrl = restaurant.restaurantDetails.featuredImage;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
