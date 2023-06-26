//package ru.skypro.homework.mappers;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import ru.skypro.homework.dto.CommentDto;
//import ru.skypro.homework.model.Comment;
//
//import java.util.Collection;
//
//@Configuration
//public class AppCommentConfig {
//    @Bean
//    public CommentMapper commentMapper() {
//        return new CommentMapper() {
//            @Override
//            public CommentDto commentToCommentDto(Comment comment) {
//                return null;
//            }
//
//            @Override
//            public Comment commentDtoToComment(CommentDto commentDto) {
//                return null;
//            }
//
//            @Override
//            public Collection<CommentDto> commentCollectionToCommentDto(Collection<Comment> commentCollection) {
//                return null;
//            }
//        };
//    }
//}
