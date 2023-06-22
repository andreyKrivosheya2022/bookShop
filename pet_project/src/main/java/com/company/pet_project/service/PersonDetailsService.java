package com.company.pet_project.service;

import com.company.pet_project.model.Person;
import com.company.pet_project.repository.PersonRepository;
import com.company.pet_project.security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personInfo = Optional.ofNullable(personRepository.findPersonByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username)));
    return new PersonDetails(personInfo.get());
    }

}
