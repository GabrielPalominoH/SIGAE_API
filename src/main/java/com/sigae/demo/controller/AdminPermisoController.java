package com.sigae.demo.controller;

import com.sigae.demo.dto.PermisoAreaRequest;
import com.sigae.demo.dto.PermisoAreaResponse;
import com.sigae.demo.service.PermisoAreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/permisos")
public class AdminPermisoController {

    private final PermisoAreaService permisoService;

    public AdminPermisoController(PermisoAreaService permisoService) {
        this.permisoService = permisoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PermisoAreaResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(permisoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<PermisoAreaResponse> asignar(@RequestBody PermisoAreaRequest request) {
        return ResponseEntity.ok(permisoService.asignar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revocar(@PathVariable Long id) {
        permisoService.revocar(id);
        return ResponseEntity.noContent().build();
    }
}
