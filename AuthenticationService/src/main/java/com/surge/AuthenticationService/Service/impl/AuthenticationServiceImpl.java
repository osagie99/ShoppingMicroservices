package com.surge.AuthenticationService.Service.impl;

import com.surge.AuthenticationService.Repository.JWTTokenRepository;
import com.surge.AuthenticationService.Repository.UserRepository;
import com.surge.AuthenticationService.Service.AuthenticationService;
import com.surge.AuthenticationService.config.JwtService;
import com.surge.AuthenticationService.dto.AuthenticationRequest;
import com.surge.AuthenticationService.dto.AuthenticationResponse;
import com.surge.AuthenticationService.dto.BaseResponse;
import com.surge.AuthenticationService.dto.RegisterRequest;
import com.surge.AuthenticationService.entity.JWTToken;
import com.surge.AuthenticationService.entity.User;
import com.surge.AuthenticationService.enums.Role;
import com.surge.AuthenticationService.enums.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenRepository tokenRepository;


    @Override
    public BaseResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        var token = tokenRepository.save(saveUserToken(user, jwtToken));
        
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .expires_at(jwtService.extractExpiration(jwtToken))
                .build();
        return BaseResponse.<AuthenticationResponse>builder()
                .status("AUTHENTICATION_SUCCESS")
                .message("SUCCESSFULLY_AUTHENTICATED")
                .data(authenticationResponse)
                .build();
    }

    @Override
    public BaseResponse<AuthenticationResponse> register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        JWTToken token = saveUserToken(savedUser, jwtToken);

        tokenRepository.save(token);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .expires_at(jwtService.extractExpiration(jwtToken))
                .build();
        return BaseResponse.<AuthenticationResponse>builder()
                .status("AUTHENTICATION_SUCCESS")
                .message("SUCCESSFULLY_AUTHENTICATED")
                .data(authenticationResponse)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if((validUserTokens.isEmpty())) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }

    private static JWTToken saveUserToken(User user, String jwtToken) {
        return JWTToken.builder()
                .token(jwtToken)
                .user(user)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
    }
}
