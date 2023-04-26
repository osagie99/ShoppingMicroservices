package com.surge.AuthenticationService.controller;

import com.surge.AuthenticationService.Service.AuthenticationService;
import com.surge.AuthenticationService.dto.AuthenticationRequest;
import com.surge.AuthenticationService.dto.AuthenticationResponse;
import com.surge.AuthenticationService.dto.BaseResponse;
import com.surge.AuthenticationService.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class authController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public BaseResponse<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public BaseResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
}
