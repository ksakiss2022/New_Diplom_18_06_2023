package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

/**
 * Класс CommentServiceImpl представляет собой сервис для работы с комментариями.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override//получает все комментарии из репозитория
    public Iterable<CommentDto> getComments() {
        return null;
    }

    @Override// получает все комментарии для объявления с заданным идентификатором из репозитория
    public Collection<CommentDto> getComments(Integer id) {
        Collection<Comment> comments = commentRepository.findCommentsByAds_Id(id);
        log.info("Get all comments for ad: " + id);
        return commentMapper.toCommentsListDto(comments);
    }

    @Override//добавляет новый комментарий для объявления с заданным идентификатором в репозиторий
    public CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) {
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ad not found");
        }
        commentDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        Comment newComment = commentMapper.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        newComment.setAds(adsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Ad not found")));
        newComment.setAuthorId(userRepository.findUserByUsername(authentication.getName()));
        commentRepository.save(newComment);
        return commentMapper.commentToCommentDto(newComment);
    }

    @Override// удаляет комментарий с заданным идентификатором для объявления с заданным идентификатором из репозитория.
    public boolean deleteComment(Integer adId, Integer id) {
        if (!adsRepository.existsById(adId)) {
            return false;
        }
        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return true;
    }

    @Override// обновляет информацию о комментарии с заданным идентификатором для объявления с заданным идентификатором.
    public CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) {
        log.info("Update comment: " + commentDto);
        if (!adsRepository.existsById(adId)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Comment not found"));
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }
}