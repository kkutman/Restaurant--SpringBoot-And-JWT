package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
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
    @GetMapping("/totalWaiter/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public int totalWaiter(@PathVariable Long id){
        return chequeServices.totalPriceWalter(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<ChequeResponse>getAll(){
        return chequeServices.getAll();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public ChequeResponse update(@PathVariable Long id,@RequestBody ChequeRequest request){
        return chequeServices.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id){
        return chequeServices.delete(id);
    }
}
