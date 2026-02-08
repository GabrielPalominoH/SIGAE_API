package com.sigae.demo.service;

import com.sigae.demo.dto.LoginRequest;
import com.sigae.demo.dto.LoginResponse;
import com.sigae.demo.entity.Usuario;
import com.sigae.demo.repository.UsuarioRepository;
import com.sigae.demo.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow();

        String token = jwtTokenProvider.generateToken(
                usuario.getUsername(),
                usuario.getRol().getNombre());

        return new LoginResponse(token);
    }
}
