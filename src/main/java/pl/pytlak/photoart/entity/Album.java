package pl.pytlak.photoart.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column
    String description;

    @ManyToOne
    @JoinColumn(name = "albums", nullable = false)
    User user;

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Photo> photos = new ArrayList<>();

    public Album(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public void addPhoto(Photo photo){
        photos.add(photo);
        photo.setAlbum(this);
    }

    public void deletePhoto(Photo photo){
        photos.remove(photo);
    }
}
