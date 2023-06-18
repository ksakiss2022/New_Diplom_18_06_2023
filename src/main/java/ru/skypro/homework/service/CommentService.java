package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;

import java.io.IOException;

@Service
public interface CommentService {public Iterable<CommentDto> getComments();

    public CommentDto addComment(CommentDto commentDto) throws IOException;

    public boolean deleteComment(Long id);

    boolean deleteComment(Integer id);

    public CommentDto updateComment(CommentDto commentDto, Long id);
}
