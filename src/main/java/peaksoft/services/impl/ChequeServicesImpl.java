package peaksoft.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.ChequeServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * name : kutman
 **/
@Service
@RequiredArgsConstructor
public class ChequeServicesImpl implements ChequeServices {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public ChequeResponse save(ChequeRequest request) {
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        User user = userRepository.findById(request.getWaiterId())
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("Author with email :%s already exists", request.getWaiterId())));
        Cheque cheque = new Cheque();
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        List<MenuItem> menuItemList = new ArrayList<>();
        for (String s : request.getMenuName()) {
            MenuItem byName = menuItemRepository.findByName(s);
            MenuItemResponse byNameResponse = menuItemRepository.findByNameResponse(s);
            menuItemResponses.add(byNameResponse);
            cheque.setMenuItems_id(List.of(byName));
        }
        cheque.setUser(user);
        cheque.setCreatedAt(LocalDate.now());
        int num = 0;
        int count = 0;
        for (MenuItemResponse menuItem : menuItemResponses) {
            num = num + menuItem.getPrice();
            count++;
        }
        int totalPrice = num + ((num / 100) * restaurant1.getServices());
        cheque.setPriceAverage(num / count);
        cheque.setTotal(num);
        cheque.setGrandTotal(totalPrice);
        chequeRepository.save(cheque);
        return new ChequeResponse(
                cheque.getId(),
                cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                menuItemResponses,
                num,
                restaurant1.getServices(),
                totalPrice
        );
    }

    @Override
    public int totalPriceWalter(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("Author with email :%s already exists", id)));
        List<Cheque> cheques = chequeRepository.findAll().stream().filter(cheque -> cheque.getUser().getId() == id).toList();
        int total = 0;
        for (Cheque cheque : cheques) {
            total += cheque.getPriceAverage();
        }
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        int num = total / 100;
        assert restaurant1 != null;
        int s = total + (num * restaurant1.getServices());
        return s;
    }

    @Override
    public ChequeResponse update(Long id, ChequeRequest request) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Cheque with id :%s already exists", id)));
        User user = userRepository.findById(request.getWaiterId()).orElseThrow(() ->
                new NoSuchElementException(String.format("Cheque with id :%s already exists", id)));
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        List<MenuItem> menuItemList = new ArrayList<>();
        for (String s : request.getMenuName()) {
            MenuItem byName = menuItemRepository.findByName(s);
            MenuItemResponse byNameResponse = menuItemRepository.findByNameResponse(s);
            menuItemResponses.add(byNameResponse);
            cheque.setMenuItems_id(List.of(byName));
        }
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        cheque.setUser(user);
        cheque.setCreatedAt(LocalDate.now());
        int num = 0;
        int count = 0;
        for (MenuItemResponse menuItem : menuItemResponses) {
            num = num + menuItem.getPrice();
            count++;
        }
        cheque.setPriceAverage(num / count);
        cheque.setMenuItems_id(menuItemList);
        int number = num / 100;
        int totalPrice = num + (number * restaurant1.getServices());
        cheque.setPriceAverage(num / count);
        cheque.setTotal(num);
        cheque.setGrandTotal(totalPrice);
        chequeRepository.save(cheque);
        return new ChequeResponse(
                cheque.getId(),
                cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                menuItemResponses,
                num,
                restaurant1.getServices(),
                totalPrice
        );

    }

    @Override
    public List<ChequeResponse> getAll() {
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        List<ChequeResponse> chequeResponseList = new ArrayList<>();
        for (Cheque cheque : chequeRepository.findAll()) {
            List<MenuItemResponse> menuItemResponseList = new ArrayList<>();
            for (MenuItem menuItem : menuItemRepository.findAll()) {
                for (Cheque menuItemCheque : menuItem.getCheques()) {
                    if (menuItemCheque.getId() == cheque.getId()) {
                        menuItemResponseList.add(new MenuItemResponse(
                                menuItem.getId(),
                                menuItem.getName(),
                                menuItem.getImage(),
                                menuItem.getPrice(),
                                menuItem.getDescription(),
                                menuItem.getIsVegetarian()
                        ));
                    }
                }
            }
            chequeResponseList.add(new ChequeResponse(
                    cheque.getId(),
                    cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                    menuItemResponseList,
                    cheque.getTotal(),
                    restaurant1.getServices(),
                    cheque.getGrandTotal()
            ));
        }
        return chequeResponseList;
    }

    @Override
    public int totalPriceRestaurant() {
        return 0;
    }

    @Override
    public ChequeResponse getById(Long id) {
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        Cheque cheque = chequeRepository.findById(id).orElseThrow();
        List<MenuItemResponse>menuItemResponseList = new ArrayList<>();
        for (MenuItem menuItem : cheque.getMenuItems_id()) {
            menuItemResponseList.add(
                    new MenuItemResponse(
                            menuItem.getId(),
                            menuItem.getName(),
                            menuItem.getImage(),
                            menuItem.getPrice(),
                            menuItem.getDescription(),
                            menuItem.getIsVegetarian()
                    )
            );
        }
        return new ChequeResponse(
                cheque.getId(),
                cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                menuItemResponseList,
                cheque.getTotal(),
                restaurant1.getServices(),
                cheque.getGrandTotal()
        );
    }


    @Override
    public String delete(Long id) {
        chequeRepository.delete(id);
        return id+" is deleted!";
    }


}
