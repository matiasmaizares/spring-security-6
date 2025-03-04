package com.matias.app_security.entities;

import lombok.Data;

@Data
public class JWTRequest {

    private String username;
    private String password;

}
