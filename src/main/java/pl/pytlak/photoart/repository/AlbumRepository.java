package pl.pytlak.photoart.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pytlak.photoart.dto.response.UserAlbumWithThumbnailResponse;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.queryInterfaces.UserAlbumsWithThumbnails;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    @Query("SELECT A FROM Album A where A.id = ?1 and A.user.id = ?2")
    Optional<Album> findByIdAndUserId(Long albumId, Long userId);

    @Query("SELECT A FROM Album A where A.user.id = ?1 ORDER BY A.creationTime DESC")
    List<Album> getUserAlbums(Long userId, PageRequest pageRequest);

    @Query("SELECT A FROM Album A where A.user.id = ?1 ORDER BY A.creationTime DESC")
    List<Album> getCurrentUserAlbumsById(Long userId);

    @Query("SELECT count(*) from Album A where A.user.id = ?1 GROUP BY A.user.id")
    Integer countUserAlbums(Long userId);

    @Query("SELECT A from Album A where A.user.id = ?1 and A.creationTime < ?2 ORDER BY A.creationTime DESC")
    List<Album> getUserAlbumsLoad(Long userId, Timestamp timestamp, PageRequest pageRequest);

    @Query("SELECT A from Album A where A.user.id = ?1 and A.id = ?2")
    Optional<Album> getLastAlbum(Long userId, Long albumId);


    @Query("select A from Album A where A.id=?1 and A.user.id=?2")
    Optional<Album> getAlbumsByIdAndUserId(Long albumId, Long userId);

    @Query("select A from Album A where A.name=?1 and  A.user.id=?2")
    Optional<Album> findAlbumByNameAndUserId(String name, Long userId);

    @Query(value = "SELECT X.name as albumName, X.creation_time as albumData, X.photoName as photoPath, X.countPhotos as countPhotos " +
            "FROM (SELECT A.name, " +
            "             A.creation_time, " +
            "             row_number() over (partition by A.id order by P.creation_time desc)        as rank, " +
            "             sum(case when P.id is not null then 1 else 0 end) over (partition by A.id) as countPhotos, " +
            "             P.name                                                                     as photoName " +
            "      FROM album A " +
            "               left join photo P on P.album_id = A.id " +
            "      WHERE A.user_id = ?1) as X " +
            "WHERE X.rank = 1 " +
            "order by X.creation_time desc", nativeQuery = true)
    List<UserAlbumsWithThumbnails> getUserAlbumWithThumbnails(Long userId);

}
