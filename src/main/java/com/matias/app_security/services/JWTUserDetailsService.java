package com.matias.app_security.services;

import com.matias.app_security.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByEmail(username).map(customer ->{
            final var authorities = customer
                    .getRoles()
                    .stream()
                    .map(role->
                            new SimpleGrantedAuthority(role.getName()))
                    .toList();
            return new User(customer.getEmail(), customer.getPassword(), authorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
