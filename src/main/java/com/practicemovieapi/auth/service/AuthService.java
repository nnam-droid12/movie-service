package com.practicemovieapi.auth.service;

import com.practicemovieapi.auth.entities.RefreshToken;
import com.practicemovieapi.auth.entities.User;
import com.practicemovieapi.auth.entities.UserRole;
import com.practicemovieapi.auth.repository.UserRepository;
import com.practicemovieapi.auth.utils.AuthResponse;
import com.practicemovieapi.auth.utils.LoginRequest;
import com.practicemovieapi.auth.utils.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    public AuthService(PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService,
                       UserRepository userRepository,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;



    }

    @Value("${admin.email}")
    private String defaultAdminEmail;

    @Value("${admin.password}")
    private String defaultAdminPassword;

    public AuthResponse register(RegisterRequest registerRequest){
        var user = User.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(user);

        var accessToken = jwtService.generateToken(savedUser, "USER");

        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        var accessToken = jwtService.generateToken(user, "USER");

        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }



    public AuthResponse adminLogin(LoginRequest loginRequest) {
        if (!defaultAdminEmail.equals(loginRequest.getEmail())) {
            throw new RuntimeException("Invalid email");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), defaultAdminPassword)) {
            throw new RuntimeException("Invalid password");
        }
        var admin = User.builder()
                .email(defaultAdminEmail)
                .password(defaultAdminPassword)
                .role(UserRole.ADMIN)
                .build();

        var accessToken = jwtService.generateToken(admin, "ADMIN");
        var refreshToken = refreshTokenService.createRefreshToken(defaultAdminEmail);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

}
