package com.pm.authservice.Service;

import com.pm.authservice.DTO.LoginRequesDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }
    public  Optional<String> authenticate(LoginRequesDTO loginRequesDTO) {
        Optional<String> token = userService.findByEmail(loginRequesDTO.getEmail())
                .filter(u ->passwordEncoder.matches(loginRequesDTO.getPassword(),
                        u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(),u.getPassword()));
        return token;

    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;

        }
        catch (JwtException e)
        {
            return false;
        }
    }
}
