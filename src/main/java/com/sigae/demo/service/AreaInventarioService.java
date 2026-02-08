package com.sigae.demo.service;

import com.sigae.demo.dto.AreaInventarioRequest;
import com.sigae.demo.dto.AreaInventarioResponse;
import com.sigae.demo.entity.AreaInventario;
import com.sigae.demo.repository.AreaInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaInventarioService {

    private final AreaInventarioRepository areaRepository;

    public AreaInventarioService(AreaInventarioRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaInventarioResponse> listarTodas() {
        return areaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AreaInventarioResponse obtenerPorId(Long id) {
        return toResponse(areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area no encontrada")));
    }

    public AreaInventarioResponse crear(AreaInventarioRequest request) {
        AreaInventario area = new AreaInventario();
        area.setNombre(request.nombre());
        area.setDescripcion(request.descripcion());
        area.setActivo(true);
        return toResponse(areaRepository.save(area));
    }

    public AreaInventarioResponse actualizar(Long id, AreaInventarioRequest request) {
        AreaInventario area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area no encontrada"));

        if (request.nombre() != null) area.setNombre(request.nombre());
        if (request.descripcion() != null) area.setDescripcion(request.descripcion());

        return toResponse(areaRepository.save(area));
    }

    public void desactivar(Long id) {
        AreaInventario area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area no encontrada"));
        area.setActivo(false);
        areaRepository.save(area);
    }

    private AreaInventarioResponse toResponse(AreaInventario a) {
        return new AreaInventarioResponse(
                a.getId(),
                a.getNombre(),
                a.getDescripcion(),
                a.getActivo()
        );
    }
}
