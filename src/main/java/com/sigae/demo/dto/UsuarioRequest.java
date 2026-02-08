package com.sigae.demo.dto;

public record UsuarioRequest(
        String username,
        String password,
        String nombre,
        String email,
        Long rolId
) {}
