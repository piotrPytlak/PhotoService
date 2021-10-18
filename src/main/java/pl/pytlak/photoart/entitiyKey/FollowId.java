package pl.pytlak.photoart.entitiyKey;

import lombok.Data;

import java.io.Serializable;

@Data
public class FollowId implements Serializable {

    private Long idUser;
    private Long idFollowerUser;


}
