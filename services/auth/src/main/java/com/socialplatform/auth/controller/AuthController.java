package com.socialplatform.auth.controller;

import com.socialplatform.auth.dto.LoginRequest;
import com.socialplatform.auth.dto.SignUpRequest;
import com.socialplatform.auth.security.JwtTokenProvider;
import com.socialplatform.auth.service.UserService;
import com.socialplatform.common.api.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        return Result.success(jwt);
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return Result.fail("用户名已存在");
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return Result.fail("邮箱已被注册");
        }

        userService.createUser(signUpRequest);
        return Result.success();
    }

    @GetMapping("/me")
    public Result<?> getCurrentUser() {
        return Result.success(userService.getCurrentUser());
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        SecurityContextHolder.clearContext();
        return Result.success();
    }
}
