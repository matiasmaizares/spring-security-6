package com.matias.app_security.controllers;


import com.matias.app_security.entities.JWTRequest;
import com.matias.app_security.entities.JWTResponse;
import com.matias.app_security.services.JWTService;
import com.matias.app_security.services.JWTUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUserDetailsService jwtUserDetailsService;
    private final JWTService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> postToken(@RequestBody JWTRequest request) {
        authenticate(request);
        final var userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final var token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }

    private void authenticate(JWTRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                    )
            );
        }catch (BadCredentialsException | DisabledException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
