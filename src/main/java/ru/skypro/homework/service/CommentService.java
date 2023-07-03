package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;

import java.io.IOException;
import java.util.Collection;

@Service
public interface CommentService {
    public Iterable<CommentDto> getComments();

    Collection<CommentDto> getComments(Integer id);

    CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) throws IOException;

    boolean deleteComment(Integer adId, Integer id);

    CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) throws IOException;
}
