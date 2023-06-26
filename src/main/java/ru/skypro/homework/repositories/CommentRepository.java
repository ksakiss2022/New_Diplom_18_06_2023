package ru.skypro.homework.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import javax.validation.constraints.NotNull;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @NotNull
    List<Comment> findAll();
    void deleteById(Long id);
}