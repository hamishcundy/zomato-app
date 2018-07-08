package nz.co.hamishcundy.zomatoapp.network.model;

import com.google.gson.annotations.SerializedName;

/**Network model representing a restaurant from Zomato's api
 *
 */
public class Restaurant {

    @SerializedName("restaurant")
    public RestaurantDetails restaurantDetails;


}
