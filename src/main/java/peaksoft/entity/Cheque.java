package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq")
    private Long id;
    private int priceAverage;
    private LocalDate createdAt;
    private int total;
    private int grandTotal;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "cheques_menu_items",
            joinColumns = @JoinColumn(name = "cheque_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_items_"))
    private List<MenuItem> menuItems_id;

    public Cheque(int priceAverage, LocalDate createdAt, int grandTotal, User user, List<MenuItem> menuItems_id) {
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
        this.grandTotal = grandTotal;
        this.user = user;
        this.menuItems_id = menuItems_id;
    }
}