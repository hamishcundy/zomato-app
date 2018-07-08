package nz.co.hamishcundy.zomatoapp.domain;

import nz.co.hamishcundy.zomatoapp.network.model.Restaurant;

/**Immutable domain object to represent a restaurant (easier for caching purposes)
 * Would use autovalue here but doesn't play nicely with Realm
 *
 */
public class RestaurantModel {

    private final String name;
    private final String address;
    private final String imageUrl;

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



}
