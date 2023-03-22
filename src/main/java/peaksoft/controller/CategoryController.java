package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.services.CategoryServices;

import java.util.List;
@RestController
@RequestMapping("/api/restaurant/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServices categoryServices;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryServices.saveCategory(request);
    }

    @PostMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<CategoryResponse>getAll(){
        return categoryServices.getAllCategory();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        return categoryServices.deleteById(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public CategoryResponse getBiId(@PathVariable Long id){
        return categoryServices.getById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse update(@PathVariable Long id,@RequestBody CategoryRequest request){
        return categoryServices.update(id,request);
    }

}
