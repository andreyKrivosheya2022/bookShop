package com.company.pet_project.repository;

import com.company.pet_project.model.productAttribute.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findGenreByName(String name);
}
