package com.bankingapp.root.service;

import com.bankingapp.root.dto.AuthRequestDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.exception.*;
import com.bankingapp.root.mapper.UserDTOEntityMapper;
import com.bankingapp.root.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO registerUser(final UserDTO user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User data must not be null.");
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Email already exists.");
        final String password = user.getPassword();
        if (password.length() < 8)
            throw new InvalidUserInputException("Password must be at least 8 characters long.");
        if (!password.matches(".*[A-Z].*"))
            throw new InvalidUserInputException("Password must contain at least one uppercase letter.");
        if (!password.matches(".*[a-z].*"))
            throw new InvalidUserInputException("Password must contain at least one lowercase letter.");
        if (!password.matches(".*\\d.*"))
            throw new InvalidUserInputException("Password must contain at least one number.");
        if (!password.matches(".*[^a-zA-Z0-9].*"))
            throw new InvalidUserInputException("Password must contain at least one special character.");
        final String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        final UserEntity userEntity = UserDTOEntityMapper.map(user);
        final UserEntity savedUserEntity = userRepository.save(userEntity);
        return UserDTOEntityMapper.map(savedUserEntity);
    }

    public void login(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        if(authRequestDTO == null)
            throw new IllegalArgumentException("AuthRequestDTO cannot be null");
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        Authentication authentication = authManager.authenticate(authInputToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );
    }
}
