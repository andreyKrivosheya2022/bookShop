package com.company.pet_project.service;

import com.company.pet_project.model.Cart;
import com.company.pet_project.model.Product;
import com.company.pet_project.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private ProductService productService;
    public List<Product> findAllProductsFromCart(){
        return cartRepository.findById(1L).orElse(new Cart()).getProductList();
    }

    public void addProduct(Product product) {
        Cart cart = findCartById(1L);
        product.setCart(cart); // Встановлення зв'язку між продуктом і корзиною
        productService.addProduct(product);
        cart.getProductList().add(product);
    }

    public void deleteProductFromCart(Product product){
        Cart cart = findCartById(1L);
        product.setCart(null);
        productService.addProduct(product);
        cart.getProductList().remove(product);
    }


    public Cart findCartById(Long id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        return cartOptional.orElse(new Cart()); // Повернення кошика або нового об'єкта, якщо кошик не знайдено
    }

    public Boolean findProductInCart(Product product){
        Cart cart = findCartById(1L);
        Optional<Product> prod = cart.getProductList().stream().filter(product1 -> product1 == product).findAny();
        return prod.isPresent();
    }

    public void updateQuantity(Product product){
        Cart cart = findCartById(1L);
        productService.updateProduct(product.getProduct_id(), product);
    }

}
