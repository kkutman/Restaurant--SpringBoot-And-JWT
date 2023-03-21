package peaksoft.controller;

import lombok.RequiredArgsConstructor;
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
    public StopListResponse save(@PathVariable Long menuId, @RequestBody StopListRequest request){
        try {
            return stopListServices.save(menuId,request);
        } catch (SaveStopListException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAll")
    public List<StopListResponse>getAll(){
        return stopListServices.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return stopListServices.delete(id);
    }

    @GetMapping("/get/{id}")
    public StopListResponse getById(@PathVariable Long id){
        return stopListServices.getById(id);
    }

    @PutMapping("/update/{id}")
    public StopListResponse update(@PathVariable Long id,@RequestBody StopListRequest request){
        return stopListServices.update(id, request);
    }

}
