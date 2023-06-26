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
    private String addProduct(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity, Model model){
        Product product = productService.findById(productId);
        product.setQuantity(quantity);
        cartService.addProduct(product);
        model.addAttribute("quantity", quantity);
        return "redirect:/shop/cart/cartProducts";
    }

    @DeleteMapping("/cart/{id}")
    private String deleteProductFromCart(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        cartService.deleteProductFromCart(product);
        return "redirect:/shop/cart/cartProducts";
    }
    @PatchMapping("/cart/{id}")
    private String updateQuantity(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity, Model model){
        Product product = productService.findById(productId);
        product.setQuantity(quantity);
        cartService.updateQuantity(product);
        model.addAttribute("quantity", quantity);
        return "redirect:/shop/cart/cartProducts";
    }

}
