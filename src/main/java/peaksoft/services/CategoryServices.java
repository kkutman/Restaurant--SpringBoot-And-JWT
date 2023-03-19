package peaksoft.services;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;

import java.util.List;

/**
 * name : kutman
 **/
public interface CategoryServices {
    CategoryResponse saveCategory(CategoryRequest request);
    List<CategoryResponse>getAllCategory();
    CategoryResponse getById(Long id);
    String deleteById(Long id);
    CategoryResponse update(Long id,CategoryRequest request);
}
