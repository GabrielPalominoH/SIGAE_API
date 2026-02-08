package com.sigae.demo.dto;

public record PermisoAreaResponse(
        Long id,
        Long usuarioId,
        String usuarioNombre,
        Long areaId,
        String areaNombre,
        Boolean canRead,
        Boolean canWrite,
        Boolean canDelete
) {}
