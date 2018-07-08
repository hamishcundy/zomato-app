package nz.co.hamishcundy.zomatoapp.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("restaurants")
    public List<Restaurant> restaurants;

}
