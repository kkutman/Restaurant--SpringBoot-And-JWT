package peaksoft.dto.request;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.RestaurantType;

/**
 * name : kutman
 **/
@Getter
@Setter
public class RestaurantRequest {
    private String name;
    private String location;
    private RestaurantType restaurantType;
    private int services;
}
