package pl.pytlak.photoart.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    Timestamp creationTime;

    @ManyToOne
    @JoinColumn(name = "userComments", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "photoComments", nullable = false)
    private Photo photo;

    public Comment(Long id, String contents, Timestamp creationTime, User user, Photo photo) {
        this.id = id;
        this.contents = contents;
        this.creationTime = creationTime;
        this.user = user;
        this.photo = photo;
    }

    public Comment() {
    }
}
