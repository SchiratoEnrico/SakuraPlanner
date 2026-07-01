package com.sakuraplanner.backend.security;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakuraplanner.backend.entity.User;
import com.sakuraplanner.backend.entity.VerificationToken;
import com.sakuraplanner.backend.exception.BusinessException;
import com.sakuraplanner.backend.repositories.UserRepository;
import com.sakuraplanner.backend.repositories.VerificationTokenRepository;
import com.sakuraplanner.backend.security.dto.AuthResponse;
import com.sakuraplanner.backend.security.dto.LoginRequest;
import com.sakuraplanner.backend.security.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("ERR_AUTH_002");
        }
        
        // Creazione utente disabilitato
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        
        user = userRepository.save(user);
        
        // Generazione Token (scadenza in 24 ore = 1440 minuti)
        String tokenString = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(tokenString, user, 1440);
        tokenRepository.save(verificationToken);
        
        // Invio Email
        emailService.sendVerificationEmail(user.getEmail(), tokenString);
    }

    @Transactional
    public void verifyAccount(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException("ERR_AUTH_005"));
                
        if (verificationToken.isExpired()) {
            throw new BusinessException("ERR_AUTH_006");
        }
        
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        
        // Cancella il token usato
        tokenRepository.delete(verificationToken);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Spring Security implicitly checks isEnabled() via the UserDetails interface.
        // If it was false, a DisabledException would have been thrown during authentication.
        
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
