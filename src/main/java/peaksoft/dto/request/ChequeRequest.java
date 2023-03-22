package peaksoft.dto.request;

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
public class ChequeRequest {
    private Long waiterId;
    private List<String>menuName;
}
