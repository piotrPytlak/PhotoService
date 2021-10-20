package pl.pytlak.photoart.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.Photo;

import java.util.Optional;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    @Modifying
    @Query("UPDATE Photo P SET P.name = ?2 WHERE P.id = ?1")
    void updateRowName(Long id, String name);


}
