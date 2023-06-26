package ru.skypro.homework.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;

@Service
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }



    @Override
    public Collection<CommentDto> getComments() {
        Collection<Comment> comments = commentRepository.findAll();
        log.info("Get all comments: " + comments);
        return commentMapper.commentCollectionToCommentDto(comments);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        Comment newComment = commentMapper.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        return commentMapper.commentToCommentDto(newComment);
    }

    @Override
    public boolean deleteComment(Long id) {
        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return false;
    }

    @Override
    public boolean deleteComment(Integer id) {
        return false;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Long id) {
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        log.info("Update comment: " + comment);
        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }
}
