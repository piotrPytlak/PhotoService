package pl.pytlak.photoart.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.queryInterfaces.UserInformation;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT U FROM User U WHERE U.id = ?1")
    Optional<User> findUserById(Long id);

    @Query("SELECT U.username, AP.name, SUM(case when F.followerUser.id is not null then 1  else 0 end) as followersCount, U.id " +
            "FROM User U " +
            "left join U.userDetails UD " +
            "left join UD.avatarPhoto AP " +
            "left join U.follows F " +
            "where upper(U.username) " +
            "like concat('%', upper(?1),'%') " +
            "GROUP BY U.id, U.username, AP.name " +
            "ORDER BY followersCount DESC ")
    List<Object[]> searchByUsername(String username, PageRequest pageRequest);


    @Query(value =
            "Select U.id as userId," +
                    "       U.age as userAge," +
                    "       U.username as username," +
                    "       U.first_name as firstName," +
                    "       U.last_name as lastName," +
                    "       UD.about_me as aboutMe," +
                    "       PA.name as avatarPath," +
                    "       PB.name as backgroundPath," +
                    "       sum(case when p.id is not null then 1 else 0 end)             as photoCount," +
                    "       (SELECT count(*) FROM album a where a.user_id = U.id)         as albumCount," +
                    "       (SELECT count(*) FROM follow f where id_follower_user = U.id) as followers," +
                    "       (SELECT count(*) FROM follow f where id_user = U.id)          as following " +
                    "FROM user_table U" +
                    "         left join album a on U.id = a.user_id" +
                    "         left join photo p on a.id = p.album_id" +
                    "         join user_details UD on U.id = UD.user_id" +
                    "         join photo PA on UD.id_avatar_photo = PA.id" +
                    "         join photo PB on UD.id_background_photo = PB.id " +
                    "WHERE U.id = ?1 " +
                    "GROUP BY U.id, " +
                    "         U.age," +
                    "         U.username," +
                    "         U.first_name," +
                    "         U.last_name," +
                    "         UD.about_me," +
                    "         PA.name," +
                    "         PB.name", nativeQuery = true)
    Optional<UserInformation> getAllUserInformation(Long userId);

}
