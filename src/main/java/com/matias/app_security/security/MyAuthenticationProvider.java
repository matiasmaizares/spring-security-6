package com.matias.app_security.security;

import com.matias.app_security.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var username = authentication.getName();
        final var pwd = authentication.getCredentials().toString();

        final var customerFromDB = customerRepository.findByEmail(username);
        final var customer = customerFromDB.orElseThrow(()->new BadCredentialsException("Invalid credentials"));
        final var customerPWD = customer.getPassword();

        if(passwordEncoder.matches(pwd, customerPWD)) {
            final var authorities = Collections.singletonList(new SimpleGrantedAuthority(customer.getRole()));
            return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
        }else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
