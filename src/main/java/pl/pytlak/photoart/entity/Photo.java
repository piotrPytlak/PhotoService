package pl.pytlak.photoart.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PhotoDetails photoDetails;

    @ManyToOne
    @JoinColumn(name = "albumId")
    private Album album;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY)
    private List<TagPhoto> tagPhotos;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY)
    private List<Comment> comments;


    public Photo(Long id, String title, Timestamp creationTime, String name, PhotoDetails photoDetails, Album album, List<TagPhoto> tagPhotos, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.creationTime = creationTime;
        this.name = name;
        this.photoDetails = photoDetails;
        this.album = album;
        this.tagPhotos = tagPhotos;
        this.comments = comments;
    }

    public Photo() {

    }
}
