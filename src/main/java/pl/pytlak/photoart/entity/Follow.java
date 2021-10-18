package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Data;
import pl.pytlak.photoart.entitiyKey.FollowId;

import javax.persistence.*;

@Entity
@Data
@Builder
@IdClass(FollowId.class)
public class Follow {

    @Id
    @Column(name = "idUser")
    private Long idUser;

    @Id
    @Column(name = "idFollowerUser")
    private Long idFollowerUser;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId(value = "idUser")
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId(value = "idFollowerUser")
    @JoinColumn(name = "idFollowerUser")
    private User followerUser;

    public Follow(Long idUser, Long idFollowerUser, User user, User followerUser) {
        this.idUser = idUser;
        this.idFollowerUser = idFollowerUser;
        this.user = user;
        this.followerUser = followerUser;
    }

    public Follow() {

    }

}
