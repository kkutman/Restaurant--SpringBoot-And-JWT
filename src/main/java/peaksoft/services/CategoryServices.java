package peaksoft.services;


import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entity.Category;

import java.util.List;

/**
 * name : kutman
 **/
public interface CategoryServices {
    CategoryResponse saveCategory(CategoryRequest request);
    List<CategoryResponse>getAllCategory();
    Category get(Long id);
    CategoryResponse getById(Long id);
    String deleteById(Long id);
    CategoryResponse update(Long id,CategoryRequest request);
    void saves(Category category);

}
