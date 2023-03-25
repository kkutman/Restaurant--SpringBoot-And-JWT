package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.exception.SaveStopListException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.services.StopListServices;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StopListServicesImpl implements StopListServices {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public StopListResponse save(Long menuId, StopListRequest request) throws SaveStopListException {
        MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(() ->
                new NotFoundException(String.format("Menu Item with id :%s already exists!", menuId)));
        for (StopList stopList : stopListRepository.findAll()) {
            int date = stopList.getDate().compareTo(request.getDate());
            if (date==0) {
                if(stopList.getMenuItem().getId()==menuItem.getId()) {
                        throw new BadRequestException("Save Date Exception!");
                }
            }
        }
        StopList stopList = new StopList(
                request.getReason(),
                request.getDate()
        );
        menuItem.setIsBlocked(stopList.getDate());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return new StopListResponse(
                stopList.getId(),
                stopList.getReason(),
                stopList.getDate(),
                stopList.getMenuItem().getName()
        );
    }

    @Override
    public String delete(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("StopList with id :%s already exists", id)));
        stopListRepository.deleteStopList(id);
        return id+ " is deleted!!!";
    }

    @Override
    public StopListResponse getById(Long id) {
        if (id != null) {
            StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                    new NotFoundException(String.format("StopList with id :%s already exists", id)));
            return new StopListResponse(
                    stopList.getId(),
                    stopList.getReason(),
                    stopList.getDate(),
                    stopList.getMenuItem().getName()
            );
        }
        return null;
    }

    @Override
    public List<StopListResponse> getAll() {
        return stopListRepository.getAll();
    }

    @Override
    public StopListResponse update(Long id, StopListRequest request) {
        if (id != null) {
            StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                    new NotFoundException(String.format("StopList with id :%s already exists", id)));
            stopList.setReason(request.getReason());
            stopList.setDate(request.getDate());
            stopListRepository.save(stopList);
            return new StopListResponse(
                    stopList.getId(),
                    stopList.getReason(),
                    stopList.getDate(),
                    stopList.getMenuItem().getName()
            );
        }
        return null;
    }


}
