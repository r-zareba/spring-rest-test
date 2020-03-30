package com.example.restfulwebservices.security;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String jwt;
}
