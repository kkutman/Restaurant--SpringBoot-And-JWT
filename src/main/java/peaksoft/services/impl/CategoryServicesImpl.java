package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entity.Category;
import peaksoft.exception.SaveCategoryException;
import peaksoft.repository.CategoryRepository;
import peaksoft.services.CategoryServices;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * name : kutman
 **/
@Service
@RequiredArgsConstructor
public class CategoryServicesImpl implements CategoryServices {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse saveCategory(CategoryRequest request) {
        if(request.getName().isBlank()){
            try {
                throw new SaveCategoryException("When saving the restaurant, one of the columns remained empty");
            } catch (SaveCategoryException e) {
                System.out.println(e.getMessage());
            }
        }else {
            Category category = new Category(request.getName());
            categoryRepository.save(category);
            return new CategoryResponse(category.getId(),category.getName());
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
        CategoryResponse categoryResponse = categoryRepository.getByIdCategory(id).orElseThrow(() ->
                new NoSuchElementException(String.format
                        ("There is no Category with this ID %s", id)));
        categoryRepository.deleteById(id);
        return categoryResponse.getName().concat(" id deleted");
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        if(id!=null||request!=null) {
            if(!request.getName().isBlank()) {
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
}
