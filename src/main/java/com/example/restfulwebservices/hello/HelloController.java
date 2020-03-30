package com.example.restfulwebservices.hello;

import com.example.restfulwebservices.security.AuthenticationRequest;
import com.example.restfulwebservices.security.AuthenticationResponse;
import com.example.restfulwebservices.security.JwtUtil;
import com.example.restfulwebservices.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping(path = "/test")
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "/test-object")
    public Hello helloObject() {
        return new Hello("Hello");
    }

    @GetMapping(path = "/test/{name}")
    public Hello helloObjectPath(@PathVariable String name) {
        return new Hello(name);
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
