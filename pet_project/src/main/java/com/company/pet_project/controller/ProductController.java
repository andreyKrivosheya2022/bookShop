package com.company.pet_project.controller;

import com.company.pet_project.model.Person;
import com.company.pet_project.model.Product;
import com.company.pet_project.repository.CartRepository;
import com.company.pet_project.repository.PersonRepository;
import com.company.pet_project.security.PersonDetails;
import com.company.pet_project.service.CartService;
import com.company.pet_project.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class ProductController {

    private final ProductService productService;
    private final PersonRepository personRepository;
    private final CartService cartService;

    @GetMapping("/products")
    public String showProducts(Model model,
                               @RequestParam(value="genre", required=false) String genre,
                               @RequestParam(value = "number_of_pages", required = false) String number_of_pages,
                               @RequestParam(value = "price", required = false) String price){
        Optional<Person> person = authenticatePerson();
        List<Product> productList = productService.findAllProducts();
        Set<String> uniqueGenres = productList.stream()
                .map(product -> product.getGenre().getName())
                .collect(Collectors.toSet());

        Set<String> uniqueNumberOfPages = productList.stream()
                .map(Product::getNumberOfPages)
                .collect(Collectors.toSet());

        Set<BigDecimal> uniquePrices = productList.stream()
                .map(Product::getPrice)
                .collect(Collectors.toSet());

        model.addAttribute("person", person.get());
        model.addAttribute("genres", uniqueGenres);
        model.addAttribute("uniqueNumberOfPages", uniqueNumberOfPages);
        model.addAttribute("uniquePrices", uniquePrices);

        if (genre != null && !genre.equals("")) {
            model.addAttribute("products", productService.findProductsByGenre(genre));
        } else if (number_of_pages != null && !number_of_pages.equals("")) {
            model.addAttribute("products", productService.findProductsByNumber(number_of_pages));
        } else if (price != null && !price.equals("")) {
            model.addAttribute("products", productService.findAllByPrice(price));
        } else {
            model.addAttribute("products", productService.findAllProducts());
        }

        return "/products";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") Long id, Model model){
        Optional<Person> person = authenticatePerson();
        model.addAttribute("person", person.get());

        Product product = productService.findById(id);
        model.addAttribute("product", product);

        model.addAttribute("booleaan", cartService.findProductInCart(product));
        return "/product";
    }

    private Optional<Person> authenticatePerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return personRepository.findPersonByUsername(username);
    }
}