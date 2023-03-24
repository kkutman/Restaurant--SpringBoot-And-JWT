package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.exception.SaveStopListException;
import peaksoft.services.StopListServices;

import java.util.List;

/**
 * name : kutman
 **/
@RestController
@RequestMapping("/api/restaurant/stopList")
@RequiredArgsConstructor
public class StopListController {
    private final StopListServices stopListServices;

    @PostMapping("/save/{menuId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public StopListResponse save(@PathVariable Long menuId, @RequestBody StopListRequest request){
        try {
            return stopListServices.save(menuId,request);
        } catch (SaveStopListException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<StopListResponse>getAll(){
        return stopListServices.getAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id){
        return stopListServices.delete(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public StopListResponse getById(@PathVariable Long id){
        return stopListServices.getById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF' )")
    public StopListResponse update(@PathVariable Long id,@RequestBody StopListRequest request){
        return stopListServices.update(id, request);
    }

}
