package peaksoft.services;

import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SubcategoryResponse;

import java.util.List;
import java.util.Map;

/**
 * name : kutman
 **/
public interface SubcategoryServices {
    SubcategoryResponse saveSubcategory(Long id,SubcategoryRequest request);
    SubcategoryResponse getById(Long id);
    String delete(Long id);
    List<SubcategoryResponse>getAll(Long id);
    SubcategoryResponse update(Long id,SubcategoryRequest request);
    Map<CategoryResponse,SubcategoryResponse>getMap();

}
