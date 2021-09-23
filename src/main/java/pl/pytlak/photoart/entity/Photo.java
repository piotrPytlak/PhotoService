package pl.pytlak.photoart.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String title;

    @CreationTimestamp
    Timestamp creationTime;

    @ManyToOne
    @JoinColumn(name = "photos")
    private Album album;


    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tag> tags;

    @OneToMany(mappedBy = "photo", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private PhotoDetails photoDetails;

}
