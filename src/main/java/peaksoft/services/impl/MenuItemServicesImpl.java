package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Subcategory;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.services.MenuItemServices;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

/**
 * name : kutman
 **/
@Service
@RequiredArgsConstructor
public class MenuItemServicesImpl implements MenuItemServices {
    private final MenuItemRepository menuItemRepository;
    private final SubcategoryRepository subcategoryRepository;
    private Boolean ifNot(MenuItemRequest request){
        return request.getName().isBlank() && request.getImage().isBlank() && request.getPrice() == 0 && request.getDescription().isBlank();
    }

    @Override
    public MenuItemResponse save(MenuItemRequest request) {
        if (request.getName().isBlank() && request.getImage().isBlank() && request.getPrice() == 0 && request.getDescription().isBlank()) {
            return null;
        } else {
            MenuItem menuItem = new MenuItem(
                    request.getName(),
                    request.getImage(),
                    request.getPrice(),
                    request.getDescription(),
                    request.getIsVegetarian()
            );
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
            MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();
            menuItem.setName(menuItem.getName());
            menuItem.setImage(menuItem.getImage());
            menuItem.setPrice(menuItem.getPrice());
            menuItem.setDescription(menuItem.getDescription());
            menuItem.setIsVegetarian(menuItem.getIsVegetarian());
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
                new NoSuchElementException(String.format("MenuItem with id :%s already exists", id)));
    }

    @Override
    public String delete(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("MenuItem with id :%s already exists", id)));
        for (Subcategory subcategory : subcategoryRepository.findAll()) {
            if(subcategory.getMenuItem().getId()== menuItem.getId()){
                subcategory.setMenuItem(null);
                menuItem.setSubcategories(null);
            }
        }
        menuItemRepository.save(menuItem);
        menuItemRepository.deleteById(id);
        return String.format("%s is deleted!",id);
    }

    @Override
    public List<MenuItemResponse> getAllOrder(Boolean descOrAsc) {
        if(descOrAsc==null) {
            return null;
        }else {
            if(descOrAsc){
                return menuItemRepository.getAllOrder().stream().sorted(Comparator.comparing(MenuItemResponse::getPrice)).toList();
            }else {
                return menuItemRepository.getAllOrder().stream().sorted(Comparator.comparing(MenuItemResponse::getPrice).reversed()).toList();
            }
        }
    }

    @Override
    public List<MenuItemResponse> getAllContains(String name) {

        if(name.isBlank()) {
            return null;
        }else {
            return menuItemRepository.getAllOrder().stream().filter(menuItemResponse -> menuItemResponse.getName().contains(name)).toList();
        }
    }

    @Override
    public List<MenuItemResponse> getAllVega(Boolean vegOrNot) {
        if(vegOrNot!=null) {
            if(vegOrNot){
                return menuItemRepository.getAllOrder().stream().filter(menuItemResponse -> menuItemResponse.getIsVegetarian()).toList();
            }else {
                return menuItemRepository.getAllOrder().stream().filter(menuItemResponse -> !menuItemResponse.getIsVegetarian()).toList();
            }
        }else {
            return null;
        }
    }
}
