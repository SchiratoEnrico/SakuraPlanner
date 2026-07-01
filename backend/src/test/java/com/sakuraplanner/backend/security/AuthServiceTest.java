package com.sakuraplanner.backend.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sakuraplanner.backend.entity.User;
import com.sakuraplanner.backend.entity.VerificationToken;
import com.sakuraplanner.backend.exception.BusinessException;
import com.sakuraplanner.backend.repositories.UserRepository;
import com.sakuraplanner.backend.repositories.VerificationTokenRepository;
import com.sakuraplanner.backend.security.dto.RegisterRequest;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("testuser", "test@test.com", "password123");
    }

    @Test
    void register_ShouldCreateUserAndToken_WhenEmailIsUnique() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPass");
        
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        authService.register(registerRequest);

        // Verify user saved correctly
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("test@test.com", savedUser.getEmail());
        assertEquals("encodedPass", savedUser.getPassword());
        assertTrue(!savedUser.isEnabled());

        // Verify token saved correctly
        ArgumentCaptor<VerificationToken> tokenCaptor = ArgumentCaptor.forClass(VerificationToken.class);
        verify(tokenRepository).save(tokenCaptor.capture());
        VerificationToken savedToken = tokenCaptor.getValue();
        assertEquals(savedUser, savedToken.getUser());

        // Verify email sent
        verify(emailService).sendVerificationEmail(eq("test@test.com"), eq(savedToken.getToken()));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(new User()));

        BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(registerRequest));
        assertEquals("ERR_AUTH_002", exception.getErrorCode());
    }

    @Test
    void verifyAccount_ShouldEnableUser_WhenTokenIsValid() {
        User user = new User();
        user.setEnabled(false);
        VerificationToken token = new VerificationToken("valid-token", user, 100);

        when(tokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));

        authService.verifyAccount("valid-token");

        assertTrue(user.isEnabled());
        verify(userRepository).save(user);
        verify(tokenRepository).delete(token);
    }

    @Test
    void verifyAccount_ShouldThrowException_WhenTokenIsExpired() {
        User user = new User();
        // Token scaduto
        VerificationToken token = new VerificationToken("expired-token", user, -10);

        when(tokenRepository.findByToken("expired-token")).thenReturn(Optional.of(token));

        BusinessException exception = assertThrows(BusinessException.class, () -> authService.verifyAccount("expired-token"));
        assertEquals("ERR_AUTH_006", exception.getErrorCode());
    }
}
