package pl.pytlak.photoart.service.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.dto.request.AddCommentRequest;
import pl.pytlak.photoart.dto.response.AddCommentResponse;
import pl.pytlak.photoart.dto.response.UserPhotoResponse;
import pl.pytlak.photoart.entity.Comment;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.repository.CommentRepository;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AuthenticationService authenticationService;


    public AddCommentResponse addCommentRequest(AddCommentRequest addCommentRequest) {
        User currentUser = authenticationService.getCurrentUser();
        Comment comment = commentRepository.save(Comment.builder()
                .contents(addCommentRequest.getCommentText())
                .photo(Photo.builder()
                        .id(addCommentRequest.getPhotoId())
                        .build())
                .user(currentUser)
                .build());

        String formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(comment.getCreationTime());

        return AddCommentResponse.builder()
                .commentId(comment.getId())
                .commentText(comment.getContents())
                .date(formatDate)
                .userId(currentUser.getId())
                .avatarPath(currentUser.getUserDetails().getAvatarPhoto().getName())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .username(currentUser.getUsername())
                .build();


    }

    public List<AddCommentResponse> getCommentsByPhotoId(Long photoId) {

        return commentRepository.findAllCommentsByPhotoId(photoId, PageRequest.of(0, 6)).stream()
                .sorted(Comparator.comparing(x -> x.getCreationTime().getTime()))
                .map(x -> AddCommentResponse.builder()
                        .username(x.getUser().getUsername())
                        .lastName(x.getUser().getLastName())
                        .commentId(x.getId())
                        .firstName(x.getUser().getFirstName())
                        .avatarPath(x.getUser().getUserDetails().getAvatarPhoto().getName())
                        .date(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(x.getCreationTime()))
                        .commentText(x.getContents())
                        .userId(x.getUser().getId())
                        .build())
                .collect(Collectors.toList());


    }

    public List<AddCommentResponse> pullCommentsByPhotoAndLastPhotoId(Long photoId, Long lastCommentId) throws Exception {
        Optional<Comment> lastComment = commentRepository.getLastComment(photoId, lastCommentId);

        //User currentUser = authenticationService.getCurrentUser();

        return lastComment.map(x -> commentRepository.getUserCommentsLoad(photoId, x.getCreationTime(), PageRequest.of(0, 3)).stream()
                .map(y -> AddCommentResponse.builder()
                        .userId(y.getUser().getId())
                        .commentId(y.getId())
                        .date(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(y.getCreationTime()))
                        .commentText(y.getContents())
                        .username(y.getUser().getUsername())
                        .firstName(y.getUser().getFirstName())
                        .lastName(y.getUser().getLastName())
                        .avatarPath(y.getUser().getUserDetails().getAvatarPhoto().getName())
                        .build()).collect(Collectors.toList())).orElseThrow(Exception::new);


    }
}
