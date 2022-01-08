package pl.pytlak.photoart.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Builder

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "userId"})
)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @CreationTimestamp
    Timestamp creationTime;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Photo> photos;

    public Album(Long id, String name, String description, Timestamp creationTime, User user, List<Photo> photos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationTime = creationTime;
        this.user = user;
        this.photos = photos;
    }

    public Album() {

    }
}
