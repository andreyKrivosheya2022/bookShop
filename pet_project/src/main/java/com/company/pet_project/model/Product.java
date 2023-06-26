package com.company.pet_project.model;

import com.company.pet_project.model.productAttribute.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(name = "name")
    @NotEmpty(message = "Product_name mustn`t be empty")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "Name`s size must be between 2 and 50 symbols")
    private String name;

    @Column(name = "imagePath")
    @NotEmpty(message = "Path mustn`t be empty ")
    private String imagePath;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @Column(name = "number_of_pages")
    @jakarta.validation.constraints.Size(min = 1, max = 1500, message = "Size must be between 1 and 1500 symbols")
    private String numberOfPages;

    @Column(name = "price")
    @Min(message = "Price must be more than 0", value = 0L)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    public Product(Long product_id, String name){
        this.product_id = product_id;
        this.name = name;
    }
}
