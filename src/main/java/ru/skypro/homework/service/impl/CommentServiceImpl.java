package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment findCommentById(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }

    @Override
    //Метод `getComments` без параметров возвращает все комментарии. В данной реализации он возвращает `null`.
    public Iterable<CommentDto> getComments() {
        return null;
    }

    @Override
    //Метод `getComments` с параметром `id` возвращает все комментарии для объявления с указанным `id`.
    public Collection<CommentDto> getComments(Integer id) {
        //Сначала он находит все комментарии в репозитории `commentRepository`, используя
        // `commentRepository.findCommentsByAds_Id(id)`.
        Collection<Comment> comments = commentRepository.findCommentsByAds_Id(id);
        log.info("Get all comments for ad: " + id);
        //Затем он преобразует список комментариев в список `CommentDto` с помощью
        // `commentMapper` и возвращает результат.
        return commentMapper.commentCollectionToCommentDto(comments);
    }

    //    @Override
//    //Метод `addComment` добавляет новый комментарий к объявлению с указанным `id`.
//    public CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) {
//        //Сначала он проверяет, существует ли объявление с указанным `id`, используя `adsRepository.existsById(id)`.
//        if (!adsRepository.existsById(id)) {
//            throw new IllegalArgumentException("Ad not found");
//            //Если объявление не найдено, выбрасывается исключение `IllegalArgumentException`.
//        }
//        //Затем он преобразует `commentDto` в объект `Comment` с помощью `commentMapper`.
//        Comment newComment = commentMapper.commentDtoToComment(commentDto);
//
//        log.info("Save comment: " + newComment);
//        newComment.setAds(adsRepository.findById(id).orElseThrow(()
//                //Затем он устанавливает объявление и автора комментария в объект `Comment`.
//                -> new IllegalArgumentException("Ad not found")));
//        newComment.setAuthorId(userRepository.findUserByUsername(authentication.getName()));
//        commentRepository.save(newComment);
//        //Затем он сохраняет новый комментарий в репозитории `commentRepository`
//        // и возвращает результат в виде объекта `CommentDto`.
//        return commentMapper.commentToCommentDto(newComment);
//    }
    @Override
    public CommentDto addComment(Integer adId, CommentDto commentDto, Authentication authentication) {
        Ads ad = adsRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));

        commentDto.setCreatedAt(System.currentTimeMillis());
        Comment newComment = commentMapper.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        newComment.setAds(ad);

        User author = userRepository.findUserByUsername(authentication.getName());
        newComment.setAuthorId(author);

        commentRepository.save(newComment);
        return commentMapper.commentToCommentDto(newComment);
    }

    //    @Override
//    //Метод `deleteComment` удаляет комментарий с указанным `id` для объявления с указанным `adId`.
//    public boolean deleteComment(Integer adId, Integer id) {
//        //Сначала метод проверяет, существует ли объявление с указанным `adId`,
//        // используя `adsRepository.existsById(adId)`.
//        if (!adsRepository.existsById(adId)) {
//            //Если объявление не найдено, метод возвращает `false`.
//            return false;
//        }
//        //Затем метод удаляет комментарий с указанным `id` с использованием `commentRepository.deleteById(id)`
//        // и возвращает `true` в случае успешного удаления комментария.
//        log.info("Delete comment: " + id);
//        commentRepository.deleteById(id);
//        return true;
//    }

    @Override
    public boolean deleteComment(Integer adId, Integer id) {
        if (!commentRepository.existsById(id)) {
            log.error("Comment not found");
            return false;
        }

        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return false;
    }

//    @Override
//    //Метод `updateComment` обновляет комментарий с указанным `id` для объявления с указанным `adId`
//    // с использованием данных из объекта `commentDto`.
//    public CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) {
//        if (!adsRepository.existsById(adId)) {
//            //Сначала метод проверяет, существует ли объявление с указанным `adId`,
//            // используя `adsRepository.existsById(adId)`.
//            throw new IllegalArgumentException("Ad not found");
//            //Если объявление не найдено, выбрасывается исключение `IllegalArgumentException`.
//        }
//        Comment comment = commentMapper.commentDtoToComment(commentDto);
//        //Затем метод преобразует `commentDto` в объект `Comment` с помощью `commentMapper`.
//        comment.setAuthorId(userRepository.findUserByUsername(authentication.getName()));
//        //Затем он устанавливает автора комментария в объект `Comment` с использованием `userRepository.
//        // findUserByUsername(authentication.getName())`.
//        log.info("Update comment: " + comment);
//        //Затем метод сохраняет обновленный комментарий в репозитории `commentRepository` и возвращает результат в
//        // виде объекта `CommentDto`.
//        return commentMapper.commentToCommentDto(commentRepository.save(comment));
//    }


    @Override
    public CommentDto updateComment(Integer adId, CommentDto commentDto, Integer commentId, Authentication authentication) {
        log.info("Update comment: " + commentDto);
        if (!adsRepository.existsById(adId)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment not found"));
        String newCommentText = commentDto.getText();
        if (StringUtils.isNotBlank(newCommentText)) {
            comment.setText(newCommentText);
            comment = commentRepository.save(comment);
        }
        return commentMapper.commentToCommentDto(comment);
    }
}