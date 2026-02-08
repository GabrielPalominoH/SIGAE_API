package com.sigae.demo.controller;

import com.sigae.demo.dto.AreaInventarioRequest;
import com.sigae.demo.dto.AreaInventarioResponse;
import com.sigae.demo.service.AreaInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/areas")
public class AdminAreaController {

    private final AreaInventarioService areaService;

    public AdminAreaController(AreaInventarioService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public ResponseEntity<List<AreaInventarioResponse>> listar() {
        return ResponseEntity.ok(areaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaInventarioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AreaInventarioResponse> crear(@RequestBody AreaInventarioRequest request) {
        return ResponseEntity.ok(areaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaInventarioResponse> actualizar(@PathVariable Long id,
                                                              @RequestBody AreaInventarioRequest request) {
        return ResponseEntity.ok(areaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        areaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
