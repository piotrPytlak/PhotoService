package pl.pytlak.photoart.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.User;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT U FROM User U WHERE U.id = ?1")
    Optional<User> findUserById(Long id);

    @Query("SELECT U.username, AP.name, SUM(case when F.followerUser.id is not null then 1  else 0 end) as followersCount " +
            "FROM User U " +
            "left join U.userDetails UD " +
            "left join UD.avatarPhoto AP " +
            "left join U.follows F " +
            "where upper(U.username) " +
            "like concat('%', upper(?1),'%') " +
            "GROUP BY U.id, U.username, AP.name " +
            "ORDER BY followersCount DESC ")
    List<Object[]> searchByUsername(String username, PageRequest pageRequest);


}
