package pl.pytlak.photoart.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.PhotoDetails;

public interface PhotoDetailsRepository extends CrudRepository<PhotoDetails, Long> {
}
