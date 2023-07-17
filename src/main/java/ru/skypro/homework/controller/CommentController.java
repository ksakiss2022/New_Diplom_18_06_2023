package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.CommentService;

import java.io.IOException;

/**
 * Класс `CommentController` является контроллером REST API и содержит методы для обработки HTTP-запросов,
 * связанных с комментариями.
 */
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    //Возвращает ответ, содержащий список комментариев для объявления с указанным {id}.
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapper<CommentDto>> getComments(@PathVariable Integer id) {
        ResponseWrapper<CommentDto> ads = new ResponseWrapper<>(commentService.getComments(id));
        return ResponseEntity.ok(ads);
    }

    //Принимает входной JSON-объект `CommentDto`, содержащий информацию о комментарии, и добавляет этот комментарий к объявлению с указанным {id}.
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id, @Parameter(description = "Необходимо корректно" +
            " заполнить комментарий", example = "Тест"
    ) @RequestBody CommentDto commentDto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(id, commentDto, authentication));
    }

    // Удаляет комментарий с указанным {commentId} из объявления с указанным {adId}.
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.findCommentById(#commentId)?.authorId?.email == authentication.name")
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        boolean result = commentService.deleteComment(adId, commentId);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //обновляет данный комментарий в объявлении с указанным {adId} и {commentId}.
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.findCommentById(#commentId).authorId.email == authentication.name")
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    Authentication authentication) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(adId, commentDto,
                commentId, authentication));
    }
}