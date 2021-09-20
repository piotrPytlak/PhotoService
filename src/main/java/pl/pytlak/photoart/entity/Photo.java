package pl.pytlak.photoart.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String link;

    @Column(nullable = false)
    String title;

    @Column
    String description;

    @ManyToOne
    @JoinColumn(name="photos", nullable = false)
    Album album;


    public Photo(Long id, String link, String title, String description) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
    }
}
