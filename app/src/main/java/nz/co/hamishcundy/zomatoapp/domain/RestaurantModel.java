package nz.co.hamishcundy.zomatoapp.domain;

import nz.co.hamishcundy.zomatoapp.network.model.Restaurant;

/**Domain object to represent a restaurant (easier for caching purposes)
 *
 */
public class RestaurantModel {

    private String name;
    private String address;
    private String imageUrl;

    public RestaurantModel(Restaurant restaurant){
        this.name = restaurant.restaurantDetails.name;
        this.address = restaurant.restaurantDetails.location.address;
        this.imageUrl = restaurant.restaurantDetails.featuredImage;
    }



}
