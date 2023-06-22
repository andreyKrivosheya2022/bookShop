package com.company.pet_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="person")
public class Person {

    @Id
    @Column(name="person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long person_id;

    @Column(name="name")
    @NotNull(message = "Name mustn`t be null")
    @Size(min = 2, max = 50, message = "Name can consist of symbols which are between 2 and 50")
    private String username;

    @NotEmpty(message = "Password can`t be empty")
    @Column(name = "password")
    private String password;

    @Email(message = "Email should be valid")
    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 16)
    private Role role;

}
