package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.RestaurantRequestOfDay;
import peaksoft.dto.request.WaiterRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.RestaurantResponseOfDay;
import peaksoft.dto.response.WaiterResponseOfDay;
import peaksoft.services.ChequeServices;

import java.util.List;

/**
 * name : kutman
 **/
@RestController
@RequestMapping("/api/restaurant/cheque")
@RequiredArgsConstructor
public class ChequeController {
    private final ChequeServices chequeServices;
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public ChequeResponse save(@RequestBody ChequeRequest request){
        return chequeServices.save(request);
    }
    @GetMapping("/totalWaiter")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public WaiterResponseOfDay totalWaiter(@RequestBody WaiterRequest request){
        return chequeServices.totalPriceWalter(request);
    }

    @PostMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public ChequeResponse getById(@PathVariable Long id){
        return chequeServices.getById(id);
    }
    @GetMapping("/totalRestor")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public RestaurantResponseOfDay totalRestor(@RequestBody RestaurantRequestOfDay request){
        return chequeServices.totalPriceRestaurant(request);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<ChequeResponse>getAll(){
        return chequeServices.getAll();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequeResponse update(@PathVariable Long id,@RequestBody ChequeRequest request){
        return chequeServices.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id){
        return chequeServices.delete(id);
    }
}
