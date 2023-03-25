package peaksoft.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * name : kutman
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestOfDay {
    private Long id;
    private LocalDate day;
}
