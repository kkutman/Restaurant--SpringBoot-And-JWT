package peaksoft.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String categoryName;
}
