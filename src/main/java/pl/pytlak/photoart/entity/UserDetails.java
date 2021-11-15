package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class UserDetails {

    @Id
    @Column(name = "userId")
    private Long id;

    // Drop row in userDetails causes drop associate user.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAvatarPhoto")
    private Photo avatarPhoto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBackgroundPhoto")
    private Photo backgroundPhoto;

    @Column(columnDefinition = "varchar(10485760)")
    private String aboutMe;

    public UserDetails() {

    }

    public UserDetails(Long id, User user, Photo avatarPhoto, Photo backgroundPhoto, String aboutMe) {
        this.id = id;
        this.user = user;
        this.avatarPhoto = avatarPhoto;
        this.backgroundPhoto = backgroundPhoto;
        this.aboutMe = aboutMe;
    }
}

