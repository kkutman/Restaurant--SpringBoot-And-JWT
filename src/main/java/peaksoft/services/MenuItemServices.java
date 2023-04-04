package peaksoft.services;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;

import java.util.List;

/**
 * name : kutman
 **/
public interface MenuItemServices {
    MenuItemResponse save(MenuItemRequest request);

    MenuItemResponse update(Long id, MenuItemRequest request);

    MenuItemResponse getById(Long id);

    String delete(Long id);

    List<MenuItemResponse> getAllResponse();

    List<MenuItemResponse> getAllOrder(Boolean descOrAsc);

    List<MenuItemResponse> getAllVega(Boolean vegOrNot);

    List<MenuItemResponse> getAllContains(String name);

}
