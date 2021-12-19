package pl.pytlak.photoart.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entitiyKey.TagPhotoId;
import pl.pytlak.photoart.entity.TagPhoto;

public interface PhotoTagRepository extends CrudRepository<TagPhoto, TagPhotoId> {

}
