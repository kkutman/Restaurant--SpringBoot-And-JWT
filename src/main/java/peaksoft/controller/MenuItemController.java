package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.repository.MenuItemRepository;
import peaksoft.services.MenuItemServices;

import java.util.List;

/**
 * name : kutman
 **/
@RestController
@RequestMapping("/api/menuItem")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemServices menuItemServices;
    @PostMapping("/save")
    public MenuItemResponse save(@RequestBody MenuItemRequest request){
        return menuItemServices.save(request);
    }

    @GetMapping("/get/{id}")
    public MenuItemResponse getById(@PathVariable Long id){
        return menuItemServices.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return menuItemServices.delete(id);
    }

    @GetMapping("/getAllOrder/{descOrAsc}")
    public List<MenuItemResponse> getAllOrder(@PathVariable Boolean descOrAsc){
        return menuItemServices.getAllOrder(descOrAsc);
    }

    @GetMapping("/getAllVega/{descOrAsc}")
    public List<MenuItemResponse> getAllVega(@PathVariable Boolean descOrAsc){
        return menuItemServices.getAllVega(descOrAsc);
    }

    @GetMapping("/getAllContains/{name}")
    public List<MenuItemResponse> getAllContains(@PathVariable String name) {
        return menuItemServices.getAllContains(name);
    }
}
