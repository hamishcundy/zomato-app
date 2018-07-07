package nz.co.hamishcundy.zomatoapp.network;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("name")
    public String name;

    public String featuredImage;

    @SerializedName("location")
    public Location location;

    public static class Location{
        public String address;
    }
}
