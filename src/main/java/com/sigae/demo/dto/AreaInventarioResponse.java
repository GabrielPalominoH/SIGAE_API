package com.sigae.demo.dto;

public record AreaInventarioResponse(
        Long id,
        String nombre,
        String descripcion,
        Boolean activo
) {}
