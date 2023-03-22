package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public MenuItemResponse save(@RequestBody MenuItemRequest request){
        return menuItemServices.save(request);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public MenuItemResponse getById(@PathVariable Long id){
        return menuItemServices.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id){
        return menuItemServices.delete(id);
    }

    @GetMapping("/getAllOrder/{descOrAsc}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public List<MenuItemResponse> getAllOrder(@PathVariable Boolean descOrAsc){
        return menuItemServices.getAllOrder(descOrAsc);
    }

    @GetMapping("/getAllVega/{descOrAsc}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public List<MenuItemResponse> getAllVega(@PathVariable Boolean descOrAsc){
        return menuItemServices.getAllVega(descOrAsc);
    }

    @GetMapping("/getAllContains/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public List<MenuItemResponse> getAllContains(@PathVariable String name) {
        return menuItemServices.getAllContains(name);
    }
}
