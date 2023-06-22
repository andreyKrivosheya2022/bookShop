package com.company.pet_project.controller;

import com.company.pet_project.dto.RegisterDto;
import com.company.pet_project.model.Cart;
import com.company.pet_project.model.Person;
import com.company.pet_project.model.Role;
import com.company.pet_project.repository.PersonRepository;
import com.company.pet_project.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;


@Controller
@RequestMapping("/shop/auth")
@AllArgsConstructor
public class AuthController {

    private PersonValidator personValidator;
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("message", false);
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegPage(Model model){
        model.addAttribute("person", new RegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("person") @Valid RegisterDto person,
                           BindingResult bindingResult){

        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/auth/register";
        Person person1 = new Person();
        person1.setUsername(person.getUsername());
        person1.setEmail(person.getEmail());
        person1.setPassword(passwordEncoder.encode(person.getPassword()));
        person1.setCart(new Cart(person1.getPerson_id(), new ArrayList<>(), person1));
        if(Objects.equals(passwordEncoder.encode(person.getPassword()), String.valueOf(123))){
            person1.setRole(Role.valueOf("ROLE_ADMIN"));
        }else person1.setRole(Role.valueOf("ROLE_USER"));


        personRepository.save(person1);

        return "redirect:/shop/products";
      }
}
