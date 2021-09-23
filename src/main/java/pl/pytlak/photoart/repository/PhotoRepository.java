package pl.pytlak.photoart.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.Photo;

public interface PhotoRepository  extends CrudRepository<Photo, Long> {




}
