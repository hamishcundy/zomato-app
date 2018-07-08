package nz.co.hamishcundy.zomatoapp;

import org.junit.Test;

import java.util.ArrayList;

import nz.co.hamishcundy.zomatoapp.domain.RestaurantModel;
import nz.co.hamishcundy.zomatoapp.network.model.Restaurant;
import nz.co.hamishcundy.zomatoapp.network.model.RestaurantDetails;
import nz.co.hamishcundy.zomatoapp.network.model.SearchResponse;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void networkDomainConversion(){
        //Mock network side object
        Restaurant r = new Restaurant();
        r.restaurantDetails = new RestaurantDetails();
        r.restaurantDetails.name = "Dexter";
        r.restaurantDetails.featuredImage = "http://test";
        r.restaurantDetails.location = new RestaurantDetails.Location();
        r.restaurantDetails.location.address = "456 High Street, Preston";

        //convert
        RestaurantModel rm = new RestaurantModel(r);

        //test converted successfully
        assertEquals(rm.getAddress(), "456 High Street, Preston");
        assertEquals(rm.getImageUrl(), "http://test");
        assertEquals(rm.getName(), "Dexter");


    }
}