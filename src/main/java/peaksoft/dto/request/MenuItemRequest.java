package peaksoft.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * name : kutman
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemRequest {
    private String name;
    private String image;
    private int price;
    private String description;
    private Boolean isVegetarian;
    private Long subcategoryId;
}
