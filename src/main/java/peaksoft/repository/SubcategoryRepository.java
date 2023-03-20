package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    @Query("select new peaksoft.dto.response.SubcategoryResponse(c.id,c.name) from Subcategory c where c.id=?1")
    Optional<SubcategoryResponse> getByCategoryResponse(Long id);
    @Query("select new peaksoft.dto.response.SubcategoryResponse(c.id,c.name) from Subcategory c where c.category=?1 order by c.name")
    List<SubcategoryResponse> getAllOrderAndCategory(Long id);

    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name),new peaksoft.dto.response.SubcategoryResponse(s.id,s.name) from Category c join Subcategory s where c.id=s.category.id group by c")
    Map<CategoryResponse,SubcategoryResponse>getMap();
}