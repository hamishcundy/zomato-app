package nz.co.hamishcundy.zomatoapp.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("restaurants")
    public List<Restaurant> restaurants;

}
