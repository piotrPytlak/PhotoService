package pl.pytlak.photoart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
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

}
