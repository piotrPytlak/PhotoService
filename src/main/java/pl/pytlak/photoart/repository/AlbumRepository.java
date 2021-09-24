package pl.pytlak.photoart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pytlak.photoart.entity.Album;

import java.util.Optional;


@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    @Query("SELECT A FROM Album A where A.id = ?1 and A.user.id = ?2")
    Optional<Album> findByIdAndUserId(Long albumId, Long userId);
}
