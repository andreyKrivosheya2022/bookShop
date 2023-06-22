package com.company.pet_project.controller;

import com.company.pet_project.model.Product;
import com.company.pet_project.service.ProductService;
import com.company.pet_project.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class AdminController {

    private final ProductService productService;

    @GetMapping("/admin/products")
    public String showProducts(Model model,
                               @RequestParam(value="genre", required=false) String genre,
                               @RequestParam(value = "number_of_pages", required = false) String number_of_pages){
        if (genre != null) model.addAttribute("products", productService.findProductsByGenre(genre));
        else if(number_of_pages != null) model.addAttribute("products", productService.findProductsByNumber(number_of_pages));
        else model.addAttribute("products", productService.findAllProducts());
        return "/products";
    }


    @GetMapping("/admin/newProduct")
    public String newProduct(@ModelAttribute("product") Product product){
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
