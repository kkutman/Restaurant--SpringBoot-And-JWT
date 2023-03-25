package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * name : kutman
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseOfDay {
    private Long id;
    private String name;
    private List<ChequeResponse>chequeResponses;
    private int allDayChequePrice;
}
