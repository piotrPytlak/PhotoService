package pl.pytlak.photoart.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.dto.response.AddCommentResponse;
import pl.pytlak.photoart.entity.Comment;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {


    @Query("SELECT c from Comment c join fetch c.user u join fetch u.userDetails us where c.photo.id = ?1 order by c.creationTime desc ")
    List<Comment> findAllCommentsByPhotoId(Long photoId, PageRequest pageRequest);

    @Query("SELECT C FROM Comment C join C.photo P where P.id = ?1 AND C.id = ?2")
    Optional<Comment> getLastComment(Long photoId, Long lastCommentId);

    @Query("SELECT C FROM Comment C join fetch C.photo P where P.id = ?1 and C.creationTime < ?2  ORDER BY C.creationTime  ")
    List<Comment> getUserCommentsLoad(Long photoId, Timestamp timestamp, PageRequest pageRequest);
}
