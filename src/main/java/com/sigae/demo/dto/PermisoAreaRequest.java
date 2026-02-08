package com.sigae.demo.dto;

public record PermisoAreaRequest(
        Long usuarioId,
        Long areaId,
        Boolean canRead,
        Boolean canWrite,
        Boolean canDelete
) {}
