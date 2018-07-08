package nz.co.hamishcundy.zomatoapp.network.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantDetails {

    @SerializedName("name")
    public String name;

    @SerializedName("featured_image")
    public String featuredImage;

    @SerializedName("location")
    public Location location;

    public static class Location{

        @SerializedName("address")
        public String address;
    }
}
