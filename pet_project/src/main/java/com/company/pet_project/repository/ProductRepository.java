package com.company.pet_project.repository;

import com.company.pet_project.model.Product;
import com.company.pet_project.model.productAttribute.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByGenre(Genre genre);
    List<Product> findAllByNumberOfPages(String number);
    List<Product> findAllByPrice(String price);
}

