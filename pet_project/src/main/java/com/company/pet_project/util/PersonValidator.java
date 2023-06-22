package com.company.pet_project.util;

import com.company.pet_project.dto.RegisterDto;
import com.company.pet_project.model.Person;
import com.company.pet_project.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {

    private final PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegisterDto person = (RegisterDto) target;

        if(personRepository.findPersonByEmail(person.getEmail()).isPresent()){
            errors.rejectValue("email","", "This email is already taken");
        }

        if(personRepository.findPersonByUsername(person.getUsername()).isPresent()){
            errors.rejectValue("username", "", "This username is already taken");
        }
    }
}
