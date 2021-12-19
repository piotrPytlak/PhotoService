package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tagName;

    @OneToMany
    @JoinColumn(name = "tagId")
    private List<TagPhoto> tagPhoto;

    public Tag(Long id, String tagName, List<TagPhoto> tagPhoto) {
        this.id = id;
        this.tagName = tagName;
        this.tagPhoto = tagPhoto;
    }

    public Tag() {

    }
}
