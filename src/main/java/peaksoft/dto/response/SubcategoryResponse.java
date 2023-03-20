package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * name : kutman
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoryResponse {
    private Long id;
    private String name;
    private String categoryName;
}
