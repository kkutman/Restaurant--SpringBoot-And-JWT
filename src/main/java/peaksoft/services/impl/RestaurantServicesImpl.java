package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exception.SaveRestaurantException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.RestaurantServices;

import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class RestaurantServicesImpl implements RestaurantServices {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userServices;

    @Override
    public RestaurantResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        int countRestaurant = getAllRestaurant().size();
        if (countRestaurant < 1) {
            try {
                if (restaurantRequest.getName().isBlank() ||
                        restaurantRequest.getRestaurantType().describeConstable().isEmpty() ||
                        restaurantRequest.getServices() == 0 ||
                        restaurantRequest.getLocation().isBlank()) {
                    throw new SaveRestaurantException("When saving the restaurant, one of the columns remained empty");
                } else {
                    Restaurant restaurant = new Restaurant(
                            restaurantRequest.getName(),
                            restaurantRequest.getLocation(),
                            restaurantRequest.getRestaurantType(),
                            0,
                            restaurantRequest.getServices()
                    );
                    restaurantRepository.save(restaurant);
                    return new RestaurantResponse(
                            restaurant.getId(),
                            restaurant.getName(),
                            restaurant.getLocation(),
                            restaurant.getRestaurantType(),
                            restaurant.getNumberOfEmployees(),
                            restaurant.getServices()
                    );
                }
            } catch (SaveRestaurantException e) {
                System.out.println(e.getMessage());
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        try {
            return restaurantRepository.findByIdRestaurant(restaurantId).orElseThrow(() ->
                    new NoSuchElementException(String.format
                            ("There is no restaurant with this ID %s", restaurantId)));
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteRestaurantById(Long id) {
        try {
            restaurantRepository.findByIdRestaurant(id).orElseThrow(() ->
                    new NoSuchElementException(String.format
                            ("There is no restaurant with this ID %s", id)));
            restaurantRepository.deleteById(id);
            return String.format("The restaurant with this ID : %s has been deleted!", id);
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<RestaurantResponse> getAllRestaurant() {
        return restaurantRepository.findAllRestaurant();
    }

    @Override
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format
                        ("There is no restaurant with this ID %s", id)));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestaurantType(restaurantRequest.getRestaurantType());
        restaurant.setServices(restaurantRequest.getServices());
        restaurantRepository.save(restaurant);
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getRestaurantType(),
                restaurant.getNumberOfEmployees(),
                restaurant.getServices()
        );
    }

    @Override
    public String hiringEmployee(Long userId, Boolean takeOrNot) {
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        User user = userServices.findById(userId).orElseThrow(() ->
                new NoSuchElementException(String.format("User with id :%s already exists", userId)));
        if (restaurant1 != null) {
            if (takeOrNot) {
                user.setRestaurant(restaurant1);
                restaurant1.assign(user);
                restaurant1.setNumberOfEmployees(restaurant1.getNumberOfEmployees()+1);
                userServices.save(user);
                restaurantRepository.save(restaurant1);
                return String.format("%s and %s assign!", restaurant1.getName(), user.getFirstName());
            } else {
                userServices.deleteById(user.getId());
                return String.format("%s and %s dont assign!", restaurant1.getName(), user.getFirstName());
            }
        }
        return null;
    }
}
