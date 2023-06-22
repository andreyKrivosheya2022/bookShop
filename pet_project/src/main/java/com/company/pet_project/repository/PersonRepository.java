package com.company.pet_project.repository;

import com.company.pet_project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByUsername(String username);
    Optional<Person> findPersonByEmail(String email);
}
