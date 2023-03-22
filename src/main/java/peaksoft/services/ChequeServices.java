package peaksoft.services;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;

import java.util.List;

/**
 * name : kutman
 **/
public interface ChequeServices {
    ChequeResponse save(ChequeRequest request);
    int totalPriceWalter(Long id);
    ChequeResponse update(Long id,ChequeRequest request);
    List<ChequeResponse>getAll();
    int totalPriceRestaurant();

    String delete(Long id);
    ChequeResponse getById(Long id);
}
