package peaksoft.services;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

/**
 * name : kutman
 **/
public interface StopListServices {
    StopListResponse save(Long menuId,StopListRequest request);
    String delete(Long id);
    StopListResponse getById(Long id);
    List<StopListResponse>getAll();
    StopListResponse update(Long id,StopListRequest request);

}
