package pl.pytlak.photoart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rateValue;

    @ManyToOne
    @JoinColumn(name = "categoriesRates", nullable = false)
    private RateCategories rateCategories;

    @ManyToOne
    @JoinColumn(name = "photoRates", nullable = false)
    private Photo photo;

    @ManyToOne
    @JoinColumn(name = "userRates", nullable = false)
    private User user;

}
