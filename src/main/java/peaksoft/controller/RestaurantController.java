package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AssignUser;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.services.RestaurantServices;

import java.util.List;

/**
 * name : kutman
 **/
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServices restaurantServices;

    @PostMapping("/save")
    public RestaurantResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantServices.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/getAll")
    public List<RestaurantResponse> getAllRestaurant() {
        return restaurantServices.getAllRestaurant();
    }

    @GetMapping("/get/{id}")
    public RestaurantResponse findByRestaurantId(@PathVariable Long id) {
        return restaurantServices.getRestaurantById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteByRestaurantId(@PathVariable Long id) {
        return restaurantServices.deleteRestaurantById(id);
    }

    @PutMapping("/update/{id}")
    public RestaurantResponse updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantServices.updateRestaurant(id, restaurantRequest);
    }

    @PutMapping("/assign")
    public String hiringEmployee(@RequestBody AssignUser assignUser){
        return restaurantServices.hiringEmployee(assignUser.getUserId(),assignUser.getTakeOrNot());
    }
}
