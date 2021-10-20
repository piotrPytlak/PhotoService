package pl.pytlak.photoart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entitiyKey.FollowId;
import pl.pytlak.photoart.entity.Follow;

import java.util.Optional;

public interface FollowRepository extends CrudRepository<Follow, FollowId> {

    @Query("SELECT F from Follow F where F.followerUser.id = ?1 and F.user.id = ?2")
    Optional<Follow> test(Long followerUserId, Long userId);
}
