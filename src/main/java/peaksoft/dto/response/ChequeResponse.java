package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * name : kutman
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItemResponse>itemResponseList;
    private int avaPrice;
    private int services;
    private int grandTotal;
    private LocalDate date;

}
