package ru.skypro.homework.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Integer id);

    Optional<User> findUserByEmailIs(String email);

    Optional<User> findByEmail(String email);

}