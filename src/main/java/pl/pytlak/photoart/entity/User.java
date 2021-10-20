package pl.pytlak.photoart.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.type.UserRoles;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @CreationTimestamp
    Timestamp creationTime;

    @Column()
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Album> albums;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFollowerUser", referencedColumnName = "id")
    private List<Follow> follows;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UserDetails userDetails;


    public User(String email, String username, String firstName, String lastName, String password, Integer age, Gender gender) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.role = UserRoles.USER;
        this.gender = gender;
    }

    public User() {

    }
}
