package nz.co.hamishcundy.zomatoapp.network;

import io.reactivex.Single;
import nz.co.hamishcundy.zomatoapp.network.model.SearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ZomatoApi {


    @Headers("user-key: 3a00ce3d287622b222f0601ed70dd4b9")
    @GET("api/v2.1/search")
    Single<SearchResponse> searchRestaurants(@Query("q") String query,
                                             @Query("count") Integer count,
                                             @Query("sort") String sort,
                                             @Query("order") String order);
}
