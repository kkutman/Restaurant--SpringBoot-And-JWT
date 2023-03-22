package peaksoft.controller;

import lombok.RequiredArgsConstructor;
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
    public ChequeResponse save(@RequestBody ChequeRequest request){
        return chequeServices.save(request);
    }
    @GetMapping("/totalWaiter/{id}")
    public int totalWaiter(@PathVariable Long id){
        return chequeServices.totalPriceWalter(id);
    }

    @GetMapping("/getAll")
    public List<ChequeResponse>getAll(){
        return chequeServices.getAll();
    }
}
