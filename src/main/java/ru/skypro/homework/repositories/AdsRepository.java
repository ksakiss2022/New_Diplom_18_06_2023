package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ads;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findAll();

    Optional<Ads> findById(Long id);

    void deleteById(Long id);

    Collection<Ads> findAllByAuthorId(Long authorId);
}
