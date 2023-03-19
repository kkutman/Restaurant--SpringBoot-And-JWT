package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restaurantType,r.numberOfEmployees,r.services) from Restaurant r")
    List<RestaurantResponse>findAllRestaurant();
    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restaurantType,r.numberOfEmployees,r.services) from Restaurant r where r.id=?1")
    Optional<RestaurantResponse> findByIdRestaurant(Long aLong);
}