package com.ngolajr.ecommerce.service;

import com.ngolajr.ecommerce.model.Role;
import com.ngolajr.ecommerce.model.dto.AuthResponse;
import com.ngolajr.ecommerce.model.dto.LoginDto;
import com.ngolajr.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final JwtEncoder jwtEncoder;

    public AuthResponse login(LoginDto dto){
        var user = repository.findByUsername(dto.username());

        if(user.isEmpty() || !isLoginCorrect(dto)){
            throw new BadCredentialsException("user or password errados");
        }

        var now = Instant.now();
        var expiresIn = 7200L;
        var scopes = user.get().getRoles()
                .stream()
                .map(Role::name)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("Ngola:User-Manager")
                .subject(dto.username())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        String jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponse(jwt, expiresIn);
    }

    private boolean isLoginCorrect(LoginDto dto) {
        if(repository.findByUsername(dto.username()).isPresent()){
            return encoder.matches(dto.password(), repository.findByUsername(dto.username()).get().getPassword());
        }

        return false;
    }
}
