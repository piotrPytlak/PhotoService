package pl.pytlak.photoart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query("select T from Tag T where T.tagName in ?1")
    List<Tag> findTagByNameList(List<String> name);
}
