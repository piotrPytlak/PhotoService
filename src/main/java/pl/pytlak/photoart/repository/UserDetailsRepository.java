package pl.pytlak.photoart.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.UserDetails;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {


}
