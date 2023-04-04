package peaksoft.annptaion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import peaksoft.entity.Category;
import peaksoft.exception.BadRequestException;
import peaksoft.repository.CategoryRepository;
import peaksoft.services.CategoryServices;

import java.util.List;

/**
 * name : kutman
 **/
@RequiredArgsConstructor
public class UnicValidate implements ConstraintValidator<Unic, String> {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<Category> categories = categoryRepository.findAll().stream().filter(category -> category.getName().equalsIgnoreCase(s)).toList();
        if (categories.size() == 0) {
            return false;
        } else {
            throw new BadRequestException("name unice!");
        }
    }
}
