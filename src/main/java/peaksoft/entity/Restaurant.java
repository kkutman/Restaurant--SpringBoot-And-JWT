package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.RestaurantType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class  Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq")
    private Long id;
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;
    private int numberOfEmployees;
    private int services;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.PERSIST)
    private List<User> users = new ArrayList<>();

    public Restaurant(String name, String location, RestaurantType restaurantType, int numberOfEmployees, int services) {
        this.name = name;
        this.location = location;
        this.restaurantType = restaurantType;
        this.numberOfEmployees = numberOfEmployees;
        this.services = services;
    }

    public Restaurant() {
    }
    public void assign(User user){
        this.users.add(user);
    }
}