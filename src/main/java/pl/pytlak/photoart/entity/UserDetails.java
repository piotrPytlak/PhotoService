package pl.pytlak.photoart.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class UserDetails implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAvatarPhoto")
    private Photo avatarPhoto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBackgroundPhoto")
    private Photo backgroundPhoto;


}

