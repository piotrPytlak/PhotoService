package pl.pytlak.photoart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "tags", nullable = false)
    private Photo photo;


}
