package pl.pytlak.photoart.repository;


import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.Rate;

public interface RateRepository extends CrudRepository<Rate, Long> {


}
