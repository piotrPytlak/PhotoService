package pl.pytlak.photoart.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.queryInterfaces.PhotoInformation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    @Modifying
    @Query("UPDATE Photo P SET P.name = ?2 WHERE P.id = ?1")
    void updateRowName(Long id, String name);


    @Query("SELECT P FROM Photo P join P.album A join fetch P.photoDetails PD WHERE A.user.id = ?1 ORDER BY P.creationTime DESC")
    List<Photo> getUserPhotos(Long userId, PageRequest pageRequest);

    @Query("SELECT P FROM Photo P join P.album A join fetch P.photoDetails PD WHERE A.user.id = ?1 ORDER BY P.creationTime DESC")
    List<Photo> getAllUserPhotos(Long userId);

    @Query("SELECT P FROM Photo P join P.album A join fetch P.photoDetails PD WHERE A.user.id = ?1 and P.creationTime < ?2 ORDER BY P.creationTime DESC ")
    List<Photo> getUserPhotosLoad(Long userId, Timestamp timestamp, PageRequest pageRequest);

    @Query("SELECT P from Photo P join P.album A where A.user.id = ?1 and P.id = ?2")
    Optional<Photo> getLastPhoto(Long userId, Long photoId);

    @Query("SELECT PD.Description as describe, P.title as title, PD.camera as camera, PD.model as model, PD.ISO as ISO, PD.exif as exif, P.id as photoId, " +
            "SUM(CASE WHEN RC.id = 1 THEN R.rateValue else 0 end ) as creativitySUM, " +
            "SUM(CASE WHEN RC.id = 2 THEN R.rateValue else 0 end ) as lightingSUM, " +
            "SUM(CASE WHEN RC.id = 3 THEN R.rateValue else 0 end ) as originalitySUM, " +
            "SUM(CASE WHEN RC.id = 4 THEN R.rateValue else 0 end ) as qualitySUM, " +
            "SUM(CASE WHEN RC.id = 5 THEN R.rateValue else 0 end ) as artisticImpressionsSUM, " +
            "SUM(CASE WHEN RC.id = 1 THEN 1 else 0 end ) as creativityCOUNT, " +
            "SUM(CASE WHEN RC.id = 2 THEN 1 else 0 end ) as lightingCOUNT, " +
            "SUM(CASE WHEN RC.id = 3 THEN 1 else 0 end ) as originalityCOUNT, " +
            "SUM(CASE WHEN RC.id = 4 THEN 1 else 0 end ) as qualityCOUNT, " +
            "SUM(CASE WHEN RC.id = 5 THEN 1 else 0 end ) as artisticImpressionsCOUNT " +
            "FROM Photo P " +
            "join P.photoDetails PD " +
            "left join P.rates R " +
            "left join R.rateCategories RC " +
            "where P.id=?1 " +
            "GROUP BY PD.Description, P.title, PD.camera, PD.model, PD.ISO, PD.exif, P.id ")
    Optional<PhotoInformation> getPhotoInformationByPhotoId(Long photoId);

    @Query("SELECT P from Photo P join fetch P.album A join fetch P.photoDetails PD where A.id = ?2 and A.user.id = ?1 ")
    List<Photo> getPhotosFromAlbum(Long userId, Long albumId);
}
