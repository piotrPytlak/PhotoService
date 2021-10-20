package pl.pytlak.photoart.entitiyKey;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class FollowId implements Serializable {

    private Long userId;
    private Long followerUserId;

    public FollowId(Long userId, Long followerUserId) {
        this.userId = userId;
        this.followerUserId = followerUserId;
    }

    public FollowId() {

    }

    public Long getUserId() {
        return userId;
    }

}
