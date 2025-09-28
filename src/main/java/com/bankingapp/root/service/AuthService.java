package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.AuthRequestDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.entity.CustomerAddressEntity;
import com.bankingapp.root.entity.CustomerEntity;
import com.bankingapp.root.entity.EmployeeEntity;
import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.exception.*;
import com.bankingapp.root.mapper.CustomerAddressEntityDTOMapper;
import com.bankingapp.root.mapper.UserDTOEntityMapper;
import com.bankingapp.root.repository.AddressRepository;
import com.bankingapp.root.repository.CustomerRepository;
import com.bankingapp.root.repository.EmployeeRepository;
import com.bankingapp.root.repository.UserRepository;
import com.bankingapp.root.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder,
                       EmployeeRepository employeeRepository,
                       CustomerRepository customerRepository,
                       AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
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
        if (user.getUserRole().equals(Constants.UserRoles.CUSTOMER)) {
            final CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setCustomer(userEntity);
            customerEntity.setDateOfBirth(user.getBirthDate());
            customerEntity.setContactNumber(user.getPhone());
            customerRepository.save(customerEntity);
            final List<CustomerAddressEntity> addresses = user.getAddresses()
                    .stream()
                    .map(address -> {
                        final CustomerAddressEntity customerAddressEntity = CustomerAddressEntityDTOMapper.map(address);
                        customerAddressEntity.setCustomer(customerEntity);
                        return customerAddressEntity;
                    })
                    .toList();
            addressRepository.saveAll(addresses);
        } else {
            final EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setEmployee(userEntity);
            employeeEntity.setContactNumber(user.getPhone());
            employeeRepository.save(employeeEntity);
        }
        return UserDTOEntityMapper.map(savedUserEntity);
    }

    public String loginUser(final AuthRequestDTO authRequest, final HttpServletResponse response) {
        if (Objects.isNull(authRequest))
            throw new IllegalArgumentException("Auth data must not be null.");
        final UserEntity userEntity = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with username " + authRequest.getUsername() + " not found."));
        if (Objects.equals(userEntity.getUsername(), authRequest.getUsername())
                && passwordEncoder.matches(authRequest.getPassword(), userEntity.getPassword())) {
            final Constants.UserRoles userRole = userEntity.getUserRole();
            if (Objects.isNull(userRole))
                throw new UserNotFoundException("Role not found.");
            final String accessToken = jwtUtil.generateToken(authRequest.getUsername(), userRole);
            final String refreshToken = jwtUtil.generateRefreshToken(authRequest.getUsername());
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/api/v1/auth/refresh");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
            return accessToken;
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    public Map<String, String> refreshToken(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies))
            throw new InvalidCredentialsException("Invalid username or password.");
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                String refreshToken = cookie.getValue();
                if (jwtUtil.isTokenExpired(refreshToken))
                    throw new InvalidTokenException("Token is expired.");
                String username = jwtUtil.extractUsername(refreshToken);
                String newAccessToken = jwtUtil.generateToken(username, jwtUtil.extractRole(refreshToken));

                Map<String, String> response = new HashMap<>();
                response.put("accessToken", newAccessToken);
                return response;
            }else {
                throw new InvalidTokenException("Invalid refresh token.");
            }
        }
        return null;
    }
}
