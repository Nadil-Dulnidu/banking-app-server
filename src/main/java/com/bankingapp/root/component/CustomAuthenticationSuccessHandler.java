package com.bankingapp.root.component;

import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public  CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("role", user.getUserRole().name());

        switch (user.getUserRole()) {
            case ADMIN:
                response.sendRedirect("/admin/dashboard");
                break;
            case CUSTOMER:
                response.sendRedirect("/customer/dashboard");
                break;
            default:
                response.sendRedirect("/auth/login?error");
        }
    }
}

