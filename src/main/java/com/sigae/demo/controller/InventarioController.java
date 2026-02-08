package com.sigae.demo.controller;

import com.sigae.demo.dto.AreaInventarioResponse;
import com.sigae.demo.service.AreaInventarioService;
import com.sigae.demo.service.PermisoAreaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final AreaInventarioService areaService;
    private final PermisoAreaService permisoService;

    public InventarioController(AreaInventarioService areaService,
                                PermisoAreaService permisoService) {
        this.areaService = areaService;
        this.permisoService = permisoService;
    }

    @GetMapping("/areas")
    public ResponseEntity<List<AreaInventarioResponse>> listarAreas() {
        return ResponseEntity.ok(areaService.listarTodas());
    }

    @GetMapping("/areas/{areaId}")
    public ResponseEntity<AreaInventarioResponse> verArea(@PathVariable Long areaId,
                                                          Authentication auth) {
        verificarPermiso(auth.getName(), areaId, "READ");
        return ResponseEntity.ok(areaService.obtenerPorId(areaId));
    }

    @PostMapping("/areas/{areaId}/movimientos")
    public ResponseEntity<String> registrarMovimiento(@PathVariable Long areaId,
                                                       @RequestBody String detalle,
                                                       Authentication auth) {
        verificarPermiso(auth.getName(), areaId, "WRITE");
        return ResponseEntity.ok("Movimiento registrado en area " + areaId + ": " + detalle);
    }

    @DeleteMapping("/areas/{areaId}/items/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long areaId,
                                              @PathVariable Long itemId,
                                              Authentication auth) {
        verificarPermiso(auth.getName(), areaId, "DELETE");
        return ResponseEntity.noContent().build();
    }

    private void verificarPermiso(String username, Long areaId, String tipo) {
        if (!permisoService.tienePermiso(username, areaId, tipo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permiso de " + tipo + " en esta area");
        }
    }
}
