package com.nutriem.service;

import com.nutriem.dto.request.LoginRequest;
import com.nutriem.dto.request.RegisterRequest;
import com.nutriem.dto.response.AuthResponse;
import com.nutriem.exception.EmailAlreadyExistsException;
import com.nutriem.model.User;
import com.nutriem.repository.UserRepository;
import com.nutriem.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils              jwtUtils;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.userRepository        = userRepository;
        this.passwordEncoder       = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils              = jwtUtils;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new EmailAlreadyExistsException("Email already registered: " + req.getEmail());

        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .licenseNumber(req.getLicenseNumber())
                .phoneNumber(req.getPhoneNumber())
                .build();

        userRepository.save(user);
        log.info("New user registered: {}", user.getEmail());

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        return buildResponse(user, jwtUtils.generateToken((UserDetails) auth.getPrincipal()));
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        log.info("User logged in: {}", user.getEmail());
        return buildResponse(user, jwtUtils.generateToken((UserDetails) auth.getPrincipal()));
    }

    private AuthResponse buildResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token).type("Bearer")
                .id(user.getId()).email(user.getEmail())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}
