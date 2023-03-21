package peaksoft.services;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.exception.SaveStopListException;

import java.util.List;

/**
 * name : kutman
 **/
public interface StopListServices {
    StopListResponse save(Long menuId,StopListRequest request) throws SaveStopListException;
    String delete(Long id);
    StopListResponse getById(Long id);
    List<StopListResponse>getAll();
    StopListResponse update(Long id,StopListRequest request);

}
