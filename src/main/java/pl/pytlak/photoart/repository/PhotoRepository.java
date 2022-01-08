package pl.pytlak.photoart.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.Photo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    @Modifying
    @Query("UPDATE Photo P SET P.name = ?2 WHERE P.id = ?1")
    void updateRowName(Long id, String name);


    @Query("SELECT P FROM Photo P join P.album A join fetch P.photoDetails PD WHERE A.user.id = ?1 ORDER BY P.creationTime DESC")
    List<Photo> getUserPhotos(Long userId, PageRequest pageRequest);

    @Query("SELECT P FROM Photo P join P.album A join fetch P.photoDetails PD WHERE A.user.id = ?1 and P.creationTime < ?2 ORDER BY P.creationTime DESC ")
    List<Photo> getUserPhotosLoad(Long userId, Timestamp timestamp, PageRequest pageRequest);

    @Query("SELECT P from Photo P join P.album A where A.user.id = ?1 and P.id = ?2")
    Optional<Photo> getLastPhoto(Long userId, Long photoId);

}
