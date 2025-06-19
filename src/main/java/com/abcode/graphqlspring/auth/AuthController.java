package com.abcode.graphqlspring.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @MutationMapping
    public Boolean registerUser(@Argument @Valid RegisterRequest input) {
        authService.registerUser(input);
        return true;
    }

    @MutationMapping
    public TokenPair login(@Argument LoginRequest input) {
        return authService.login(input);
    }

    @MutationMapping
    public TokenPair refreshToken(@Argument @Valid RefreshTokenRequest input) {
        return authService.refreshToken(input);
    }
}
