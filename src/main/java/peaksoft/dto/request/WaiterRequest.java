package peaksoft.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * name : kutman
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaiterRequest {
    private Long waiterId;
    private LocalDate day;
}
