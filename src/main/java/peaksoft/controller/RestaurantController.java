package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantServices.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RestaurantResponse> getAllRestaurant() {
        return restaurantServices.getAllRestaurant();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantResponse findByRestaurantId(@PathVariable Long id) {
        return restaurantServices.getRestaurantById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteByRestaurantId(@PathVariable Long id) {
        return restaurantServices.deleteRestaurantById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantResponse updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantServices.updateRestaurant(id, restaurantRequest);
    }

    @PutMapping("/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String hiringEmployee(@RequestBody AssignUser assignUser){
        return restaurantServices.hiringEmployee(assignUser.getUserId(),assignUser.getTakeOrNot());
    }
}
