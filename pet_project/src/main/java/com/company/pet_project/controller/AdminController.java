package com.company.pet_project.controller;

import com.company.pet_project.model.Product;
import com.company.pet_project.model.productAttribute.Genre;
import com.company.pet_project.repository.GenreRepository;
import com.company.pet_project.service.ProductService;
import com.company.pet_project.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class AdminController {

    private final ProductService productService;

    @GetMapping("/admin/newProduct")
    public String newProduct(@ModelAttribute("product") Product product, Model model){
        List<Product> productList = productService.findAllProducts();
        Set<String> uniqueGenres = productList.stream()
                        .map(product1 -> product1.getGenre().getName())
                                .collect(Collectors.toSet());

        model.addAttribute("uniqueGenres", uniqueGenres);
        return "/admin/newProduct";
    }

    @GetMapping("/{id}/admin/editProduct")
    public String editProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productService.findById(id));
        return "/admin/editProduct";
    }

    @PostMapping("/newProduct")
    public String createProduct(@ModelAttribute("product") @Valid Product product,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/admin/newProduct";

        productService.addProduct(product);
        return "redirect:/shop/products";
    }


    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/shop/products";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") Long id){
        if(bindingResult.hasErrors())
            return "/admin/editProduct";
        productService.updateProduct(id, product);
        return "redirect:/shop/products";
    }
}
