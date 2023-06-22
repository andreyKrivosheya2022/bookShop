package com.company.pet_project.controller;

import com.company.pet_project.model.Product;
import com.company.pet_project.service.CartService;
import com.company.pet_project.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class CartController {

    private CartService cartService;
    private ProductService productService;

    @GetMapping("/cart/cartProducts")
    private String showAllProductsFromCart(Model model){
        model.addAttribute("products", cartService.findAllProductsFromCart());
        return "/cart/cartProducts";
    }

    @PostMapping("/cart/cartProducts")
    private String addProduct(@RequestParam("productId") Long productId){
        Product product = productService.findById(productId);
        cartService.addProduct(product);
        return "redirect:/shop/cart/cartProducts";
    }

    @DeleteMapping("/cart/cartProducts")
    private String deleteProductFromCart(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        cartService.deleteProductFromCart(product);
        return "cart/cartProducts";
    }
}
