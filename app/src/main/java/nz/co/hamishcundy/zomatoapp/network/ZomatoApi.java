package nz.co.hamishcundy.zomatoapp.network;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ZomatoApi {


    @GET("/api/v2.1/search")
    Single<SearchResponse> searchRestaurants();
}
