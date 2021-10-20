package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Data;
import pl.pytlak.photoart.entitiyKey.FollowId;

import javax.persistence.*;

@Entity
@Data
@Builder
public class Follow {

    @EmbeddedId
    private FollowId followId;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "followerUserId")
    @JoinColumn(name = "idFollowerUser")
    private User followerUser;

    public Follow(FollowId followId, User user, User followerUser) {
        this.followId = followId;
        this.user = user;
        this.followerUser = followerUser;
    }



    public Follow() {

    }

}
