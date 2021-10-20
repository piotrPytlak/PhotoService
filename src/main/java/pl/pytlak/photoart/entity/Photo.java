package pl.pytlak.photoart.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @CreationTimestamp
    Timestamp creationTime;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "albumId")
    private Album album;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tag> tags;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments;

//    @OneToOne(fetch = FetchType.LAZY)
//    @PrimaryKeyJoinColumn
//    private PhotoDetails photoDetails;

    private Photo(Long id, String title, Timestamp creationTime, String name, Album album, List<Tag> tags, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.creationTime = creationTime;
        this.name = name;
        this.album = album;
        this.tags = tags;
        this.comments = comments;
    }


    public Photo() {

    }
}
