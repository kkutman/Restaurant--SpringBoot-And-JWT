package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
@AllArgsConstructor
@NoArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stop_list_seq")
    @SequenceGenerator(name = "stop_list_seq")
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MenuItem menuItem;

    public StopList(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }
}