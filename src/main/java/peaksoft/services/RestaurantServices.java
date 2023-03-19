package peaksoft.services;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;

import java.util.List;

/**
 * name : kutman
 **/
public interface RestaurantServices {
    RestaurantResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse getRestaurantById(Long restaurantId);
    String deleteRestaurantById(Long id);
    List<RestaurantResponse>getAllRestaurant();
    RestaurantResponse updateRestaurant(Long id,RestaurantRequest restaurantRequest);
    String hiringEmployee(Long userId,Boolean TakeOrNot);
}
