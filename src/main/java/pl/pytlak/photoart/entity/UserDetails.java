package pl.pytlak.photoart.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class UserDetails implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBackgroundPhoto")
    private Photo backgroundPhoto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAvatarPhoto")
    private Photo avatarPhoto;


}
