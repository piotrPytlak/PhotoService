package pl.pytlak.photoart.entity;

import lombok.*;
import pl.pytlak.photoart.type.Gender;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    Integer age;

    @Column()
    Gender gender;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Album> albums = new ArrayList<>();


    public User(String email, String firstName, String lastName, String password, Integer age, Gender gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }

    public void addAlbum(Album album) {
        albums.add(album);
        album.setUser(this);
    }


    public void deleteAlbum(Album album) {
        albums.remove(album);
    }


}
