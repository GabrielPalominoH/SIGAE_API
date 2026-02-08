package com.sigae.demo.dto;

public record UsuarioResponse(
        Long id,
        String username,
        String nombre,
        String email,
        String rol,
        Boolean activo
) {}
