package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * Класс CommentController является контроллером, который отвечает за обработку HTTP-запросов,
 *  связанных с комментариями. Он содержит различные методы, которые позволяют получать,
 * добавлять, обновлять и удалять комментарии. Класс также использует CommentService
 * для выполнения операций с комментариями.
 */

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;


    @Operation(
            operationId = "getComments",
            summary = "Получить комментарии объявления",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    /**
     * Для получения комментариев используется сервисный метод "getComments" с параметром "id".
     * Результат вызова метода оборачивается в объект "ResponseWrapper<CommentDto>".
     *
     * @param id идентификатор объявления
     * @return HTTP-ответ с комментариями объявления
     */
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapper<CommentDto>> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok((ResponseWrapper<CommentDto>) commentService.getComments(id));
    }

    @Operation(
            operationId = "addComment",
            summary = "Добавить комментарий к объявлению",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    /**
     * В результате выполнения метода, возвращается объект класса "ResponseEntity<CommentDto>".
     * Ответ содержит статус код "CREATED" и объект "CommentDto",
     * который является результатом вызова  метода "addComment" с переданными параметрами
     * "id", "commentDto" и "authentication".
     *
     * @param id          идентификатор объявления, к которому нужно добавить комментарий.
     * @param commentDto  объект  содержащий данные комментария. Данные комментария передаются в теле запроса.
     * @return HTTP-ответ с добавленным комментарием
     * @throws IOException если произошла ошибка ввода-ввывода
     */
    @PreAuthorize("isAuthenticated()")
//    @PostMapping("{id}/comments")
//    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id, @Parameter(description = "Необходимо корректно" +
//            " заполнить комментарий", example = "Тест"
//    ) @RequestBody CommentDto commentDto) throws IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(id, commentDto, authentication));
//    }
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CommentDto commentDto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(id, commentDto, authentication));
    }

    @Operation(
            operationId = "deleteComment",
            summary = "Удалить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем комментарий ",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }

    )
    /**
     * Метод возвращает `ResponseEntity<Void>`, который содержит статус код ответа NO_CONTENT в случае успешного
     * удаления комментария. В случае, если комментарий не найден, возвращается статус код NOT_FOUND.
     *
     * @param adId       Идентификатор объявления, к которому относится комментарий.
     * @param commentId  Идентификатор комментария, который нужно удалить.
     * @return HTTP-ответ со статусом удаления комментария
     */
//    @DeleteMapping("{adId}/comments/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
//        boolean result = commentService.deleteComment(adId, commentId);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @PreAuthorize("hasAuthority('ADMIN') or @commentServiceImpl.findCommentById(#commentId)?.authorId?.email == authentication.name")
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        boolean result = commentService.deleteComment(adId, commentId);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "updateComment",
            summary = "Обновить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемый комментарий ",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    /**
     * Метод возвращает `ResponseEntity<CommentDto>`, который содержит обновленный комментарий в случае успешного
     * выполнения операции обновления.
     * authentication: Объект аутентификации для проверки прав доступа.
     * @param adId        Идентификатор объявления, к которому относится комментарий.
     * @param commentId   Идентификатор комментария, который нужно обновить.
     * @param commentDto   Обновленные данные комментария. Они передаются в теле запроса в виде объекта CommentDto.
     * @return HTTP-ответ с обновленным комментарием
     */
//    @PatchMapping("{adId}/comments/{commentId}")
//    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer adId,
//                                                    @PathVariable Integer commentId,
//                                                    Authentication authentication) throws IOException {
//        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(adId, commentDto,
//                commentId, authentication));
//    }
    @PreAuthorize("hasAuthority('ADMIN') or @commentServiceImpl.findCommentById(#commentId).authorId.email == authentication.name")
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    Authentication authentication) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(adId, commentDto,
                commentId, authentication));
    }
}