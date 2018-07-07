package nz.co.hamishcundy.zomatoapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkInterfaceProvider {

    private static final String BASE_URL = "https://developers.zomato.com/";
    private static final String API_KEY = "3a00ce3d287622b222f0601ed70dd4b9";

    public static ZomatoApi buildZomatoApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ZomatoApi.class);
    }
}
