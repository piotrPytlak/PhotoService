package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.dto.request.AddCommentRequest;
import pl.pytlak.photoart.dto.response.AddCommentResponse;
import pl.pytlak.photoart.service.comment.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class CommentController {

    private final CommentService commentService;


    @PostMapping(value = "/addComment")
    public ResponseEntity<AddCommentResponse> addComment(@Valid @RequestBody AddCommentRequest addCommentRequest) {

        try {
            return new ResponseEntity<>(commentService.addCommentRequest(addCommentRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getComments/{photoId}")
    public ResponseEntity<List<AddCommentResponse>> getComments(@PathVariable("photoId") @NotBlank Long photoId) {
        return new ResponseEntity<>(commentService.getCommentsByPhotoId(photoId), HttpStatus.OK);

    }

    @GetMapping("/pullComments")
    public ResponseEntity<List<AddCommentResponse>> pullComments(@RequestParam("photoId") Long photoId,
                                                                 @RequestParam("lastCommentId") Long lastCommentId) throws Exception {
        return new ResponseEntity<>(commentService.pullCommentsByPhotoAndLastPhotoId(photoId, lastCommentId), HttpStatus.OK);
    }


}
