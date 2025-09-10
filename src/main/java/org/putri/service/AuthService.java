package org.putri.service;

import java.time.Duration;

import org.putri.entity.User;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {
    public String genereateToken(User user) {
        // Generate JWT token
        return io.smallrye.jwt.build.Jwt.issuer("putri-app")
                .upn(user.email) // Subject
                .claim("id", user.id) // Custom claim: id
                .claim("username", user.username) // Custom claim: username
                .claim("email", user.email) // Custom claim: email
                .expiresIn(Duration.ofHours(2)) // Token valid 2 jam
                .sign(); // Sign dengan private key
    }
}
