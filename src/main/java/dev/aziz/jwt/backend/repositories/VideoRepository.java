package dev.aziz.jwt.backend.repositories;

import dev.aziz.jwt.backend.entites.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT id from videos where name = :name"
    )
    Long getIdByName(@Param("name") String name);
}
