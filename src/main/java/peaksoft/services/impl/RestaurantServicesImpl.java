package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.exception.SaveRestaurantException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServicesImpl implements RestaurantServices {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final UserServices userServices;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemServices menuItemServices;
    private final CategoryServices categoryServices;
    private final ChequeRepository chequeServices;

    @Override
    public RestaurantResponse saveRestaurant(RestaurantRequest restaurantRequest) throws NotFoundException {
        int countRestaurant = getAllRestaurant().size();
        if (countRestaurant < 1) {
            if (restaurantRequest.getName().isBlank() ||
                    restaurantRequest.getRestaurantType().describeConstable().isEmpty() ||
                    restaurantRequest.getServices() == 0 ||
                    restaurantRequest.getLocation().isBlank()) {
                throw new BadRequestException("When saving the restaurant, one of the columns remained empty");
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
        } else {
            throw new BadRequestException("You cannot save more than one restaurant!");
        }
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        try {
            return restaurantRepository.findByIdRestaurant(restaurantId).orElseThrow(() ->
                    new NotFoundException(String.format("There is no restaurant with this ID %s", restaurantId)));
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteRestaurantById(Long id) {
        try {
            restaurantRepository.findByIdRestaurant(id).orElseThrow(() ->
                    new NotFoundException(String.format
                            ("There is no restaurant with this ID %s", id)));
//            for (User user : userRepository.findAll()) {
//                if (user.getRestaurant() != null) {
//                    if (user.getRestaurant().getId() == id) {
//                        if (user.getId() != 1L) {
//                            userServices.deleteUser(user.getId());
//                        }else {
//                            user.setRestaurant(null);
//                        }
//                    }
//                }
//            }
            for (MenuItem menuItem : menuItemRepository.findAll()) {
                menuItemServices.delete(menuItem.getId());
            }
            for (CategoryResponse categoryResponse : categoryServices.getAllCategory()) {
                categoryServices.deleteById(categoryResponse.getId());
            }
            for (Cheque cheque : chequeServices.findAll()) {
                chequeServices.deleteById(cheque.getId());
            }
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
                new NotFoundException(String.format
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
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id :%s already exists", userId)));
        if (restaurant1 != null) {
            if (takeOrNot) {
                if (restaurant1.getNumberOfEmployees() <= 15) {
                    user.setRestaurant(restaurant1);
                    restaurant1.assign(user);
                    restaurant1.setNumberOfEmployees(restaurant1.getNumberOfEmployees() + 1);
                    userRepository.save(user);
                    restaurantRepository.save(restaurant1);
                    return String.format("%s and %s assign!", restaurant1.getName(), user.getFirstName());
                } else {
                    throw new BadRequestException(String.format("%s and %s dont assign!", restaurant1.getName(), user.getFirstName()));
                }
            } else {
                userRepository.deleteById(user.getId());
                return String.format("%s and %s dont assign!", restaurant1.getName(), user.getFirstName());
            }
        }
        return null;
    }
}
