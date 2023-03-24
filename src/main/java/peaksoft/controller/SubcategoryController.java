package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.services.SubcategoryServices;

import java.util.List;
import java.util.Map;

/**
 * name : kutman
 **/
@RestController
@RequestMapping("/api/restaurant/category/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryServices subcategoryServices;

    @PostMapping("/save/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SubcategoryResponse save(@PathVariable Long categoryId, @RequestBody SubcategoryRequest request) {
        return subcategoryServices.saveSubcategory(categoryId,request);
    }

    @GetMapping("/getAll/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<SubcategoryResponse> getAll(@PathVariable Long id) {
        return subcategoryServices.getAll(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SubcategoryResponse getById(@PathVariable Long id) {
        return subcategoryServices.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id) {
        return subcategoryServices.delete(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubcategoryResponse update(@PathVariable Long id,@RequestBody SubcategoryRequest request){
        return subcategoryServices.update(id, request);
    }
    @GetMapping("/map")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public Map<String, List<SubcategoryResponse>> map(){
        return subcategoryServices.getMap();
    }


}
