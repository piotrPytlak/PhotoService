package pl.pytlak.photoart.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class RateCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "rateCategories", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Rate> rates;

    public RateCategories(Long id, String name, List<Rate> rates) {
        this.id = id;
        this.name = name;
        this.rates = rates;
    }

    public RateCategories() {
    }
}
