package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.services.CategoryServices;
import peaksoft.services.SubcategoryServices;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class SubcategoryServicesImpl implements SubcategoryServices {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SubcategoryResponse saveSubcategory(Long id, SubcategoryRequest request) {
        if (id != null) {
            if (!request.getName().isBlank()) {
                Category category = categoryRepository.findById(id).orElseThrow();
                Subcategory subcategory = new Subcategory();
                subcategory.setName(request.getName());
                subcategory.setCategory(category);
                subcategoryRepository.save(subcategory);
                return new SubcategoryResponse(subcategory.getId(), subcategory.getName());
            }
        }

        return null;
    }

     @Override
    public SubcategoryResponse getById(Long id) {
        return subcategoryRepository.getByCategoryResponse(id).orElseThrow(() ->
                new NoSuchElementException(String.format
                        ("There is no SubCategory with this ID %s", id)));
    }

    @Override
    public String delete(Long id) {
        if (id != null) {
            Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException(String.format
                            ("There is no SubCategory with this ID %s", id)));
            subcategoryRepository.deleteById(id);
            return String.format("Subcategory %s is deleted", subcategory.getName());
        }
        return null;
    }

    @Override
    public List<SubcategoryResponse> getAll(Long id) {
        if (id != null) {
            return subcategoryRepository.getAllOrderAndCategory(id);
        }
        return null;
    }

    @Override
    public SubcategoryResponse update(Long id, SubcategoryRequest request) {
        if (id != null) {
            Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException(String.format
                            ("There is no SubCategory with this ID %s", id)));
            subcategory.setName(request.getName());
            subcategoryRepository.save(subcategory);
            return new SubcategoryResponse(subcategory.getId(), subcategory.getName());
        }
        return null;
    }

    @Override
    public Map<CategoryResponse, SubcategoryResponse> getMap() {
        return subcategoryRepository.getMap();
    }
}
