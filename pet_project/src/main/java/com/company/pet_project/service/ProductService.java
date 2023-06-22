package com.company.pet_project.service;

import com.company.pet_project.model.Product;
import com.company.pet_project.model.productAttribute.Genre;
import com.company.pet_project.repository.GenreRepository;
import com.company.pet_project.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private GenreRepository genreRepository;

    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findProductsByGenre(String name){
        List<Genre> genre = genreRepository.findGenreByName(name);
        if (!genre.isEmpty()) {
            return productRepository.findAllByGenre(genre.get(0));
        }
        return Collections.emptyList();
    }

    public List<Product> findProductsByNumber(String number_of_pages){
        return productRepository.findAllByNumberOfPages(number_of_pages);
    }

    public List<Product> findAllByPrice(String price){
        return productRepository.findAllByPrice(price);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        String genreName = product.getGenre().getName();
        List<Genre> existingGenre = genreRepository.findGenreByName(genreName);
        if (existingGenre != null) {
            product.setGenre(existingGenre.get(0));
        }
        productRepository.save(product);
    }


    public void updateProduct(Long id, Product product){
        Product product1 = findById(id);
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setDescription(product.getDescription());
        product1.setImagePath(product.getImagePath());
        addProduct(product1);
    }
    public void deleteProduct(Long id){
        productRepository.delete(findById(id));
    }
}
