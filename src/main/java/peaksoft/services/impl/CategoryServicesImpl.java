package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.exception.SaveCategoryException;
import peaksoft.repository.CategoryRepository;
import peaksoft.services.CategoryServices;
import peaksoft.services.SubcategoryServices;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * name : kutman
 **/
@Service
@RequiredArgsConstructor
public class CategoryServicesImpl implements CategoryServices {
    private final CategoryRepository categoryRepository;
    private final SubcategoryServices subcategoryServices;

    @Override
    public CategoryResponse saveCategory(CategoryRequest request) {
        if (request.getName().isBlank()) {
            try {
                throw new SaveCategoryException("When saving the restaurant, one of the columns remained empty");
            } catch (SaveCategoryException e) {
                System.out.println(e.getMessage());
            }
        } else {
            Category category = new Category(request.getName());
            categoryRepository.save(category);
            return new CategoryResponse(category.getId(), category.getName());
        }
        return null;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.getByIdCategory(id).orElseThrow(() ->
                new NoSuchElementException(String.format
                        ("There is no Category with this ID %s", id)));
    }

    @Override
    public String deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format
                        ("There is no Category with this ID %s", id)));
        for (Subcategory subcategory : category.getSubcategories()) {
            subcategoryServices.delete(subcategory.getId());
        }
        categoryRepository.deleteById(id);
        return category.getName().concat(" id deleted");
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        if (id != null || request != null) {
            if (!request.getName().isBlank()) {
                assert id != null;
                Category category = categoryRepository.findById(id).orElseThrow(() ->
                        new NoSuchElementException(String.format
                                ("There is no Category with this ID %s", id)));
                category.setName(request.getName());
                categoryRepository.save(category);
                return new CategoryResponse(
                        category.getId(),
                        category.getName()
                );
            }
        }
        return null;
    }

    @Override
    public Category get(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format
                        ("There is no Category with this ID %s", id)));
    }

    @Override
    public void saves(Category category) {
        categoryRepository.save(category);
    }
}
