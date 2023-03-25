package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.Subcategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.services.MenuItemServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuItemServicesImpl implements MenuItemServices {
    private final MenuItemRepository menuItemRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final RestaurantRepository restaurantRepository;

    private Boolean ifNot(MenuItemRequest request) {
        return request.getName().isBlank() && request.getImage().isBlank() && request.getPrice() == 0 && request.getDescription().isBlank();
    }

    @Override
    public MenuItemResponse save(MenuItemRequest request) {
        if (request.getName().isBlank() && request.getImage().isBlank() && request.getPrice() == 0 && request.getDescription().isBlank()) {
            return null;
        } else {
            Restaurant restaurant1 = null;
            for (Restaurant restaurant : restaurantRepository.findAll()) {
                restaurant1 = restaurant;
            }
            Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).orElseThrow(() -> new NoSuchElementException("N Subcategory By Id " + request.getSubcategoryId()));
            MenuItem menuItem = new MenuItem(
                    request.getName(),
                    request.getImage(),
                    request.getPrice(),
                    request.getDescription(),
                    request.getIsVegetarian()
            );
            menuItem.setSubcategories(subcategory);
            menuItem.setRestaurant(restaurant1);
            menuItemRepository.save(menuItem);
            return new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.getIsVegetarian()
            );
        }
    }


    @Override
    public MenuItemResponse update(Long id, MenuItemRequest request) {
        if (ifNot(request)) {
            return null;
        } else {
            Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).orElseThrow(() -> new NoSuchElementException("N Subcategory By Id " + request.getSubcategoryId()));
            MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();
            menuItem.setName(request.getName());
            menuItem.setImage(request.getImage());
            menuItem.setSubcategories(subcategory);
            menuItem.setPrice(request.getPrice());
            menuItem.setDescription(request.getDescription());
            menuItem.setIsVegetarian(request .getIsVegetarian());
            menuItemRepository.save(menuItem);
            return new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.getIsVegetarian()
            );
        }
    }

    @Override
    public MenuItemResponse getById(Long id) {
        return menuItemRepository.getByMenu(id).orElseThrow(() ->
                new NotFoundException(String.format("MenuItem with id :%s already exists", id)));
    }

    @Override
    public String delete(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("MenuItem with id :%s already exists", id)));
        menuItem.setSubcategories(null);
        menuItemRepository.save(menuItem);
        menuItemRepository.deleteById(id);
        return String.format("%s is deleted!", id);
    }
    public List<MenuItemResponse> getAllResponse() {
        List<MenuItemResponse> getAllMenuItemResponse = new ArrayList<>();
        List<MenuItem> all = menuItemRepository.findAll();
        for (MenuItem menuItem : all) {
            if(menuItem.getIsBlocked()!=null) {
                int result = menuItem.getIsBlocked().compareTo(LocalDate.now());
                if (result != 0) {
                    getAllMenuItemResponse.add(new MenuItemResponse(
                            menuItem.getId(),
                            menuItem.getName(),
                            menuItem.getImage(),
                            menuItem.getPrice(),
                            menuItem.getDescription(),
                            menuItem.getIsVegetarian()
                    ));
                }
            }else {
                getAllMenuItemResponse.add(new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getImage(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.getIsVegetarian()
                ));
            }
        }
        return getAllMenuItemResponse;
    }


    @Override
    public List<MenuItemResponse> getAllOrder(Boolean descOrAsc) {
        if(descOrAsc==null) {
            return null;
        }else {
            if(descOrAsc){
                return getAllResponse().stream().sorted(Comparator.comparing(MenuItemResponse::getPrice)).toList();
            }else {
                return getAllResponse().stream().sorted(Comparator.comparing(MenuItemResponse::getPrice).reversed()).toList();
            }
        }
    }

    @Override
    public List<MenuItemResponse> getAllContains(String name) {
        return getAllResponse().stream().filter(menuItemResponse -> menuItemResponse.getName().contains(name)).toList();
    }

    @Override
    public List<MenuItemResponse> getAllVega(Boolean vegOrNot) {
        if (vegOrNot != null) {
            if (vegOrNot) {
                return getAllResponse().stream().filter(MenuItemResponse::getIsVegetarian).toList();
            } else {
                return getAllResponse().stream().filter(menuItemResponse -> !menuItemResponse.getIsVegetarian()).toList();
            }
        } else {
            return null;
        }
    }
}
