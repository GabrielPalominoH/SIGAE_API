package com.sigae.demo.service;

import com.sigae.demo.dto.PermisoAreaRequest;
import com.sigae.demo.dto.PermisoAreaResponse;
import com.sigae.demo.entity.AreaInventario;
import com.sigae.demo.entity.PermisoArea;
import com.sigae.demo.entity.Usuario;
import com.sigae.demo.repository.AreaInventarioRepository;
import com.sigae.demo.repository.PermisoAreaRepository;
import com.sigae.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoAreaService {

    private final PermisoAreaRepository permisoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaInventarioRepository areaRepository;

    public PermisoAreaService(PermisoAreaRepository permisoRepository,
                              UsuarioRepository usuarioRepository,
                              AreaInventarioRepository areaRepository) {
        this.permisoRepository = permisoRepository;
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
    }

    public List<PermisoAreaResponse> listarPorUsuario(Long usuarioId) {
        return permisoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    public PermisoAreaResponse asignar(PermisoAreaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        AreaInventario area = areaRepository.findById(request.areaId())
                .orElseThrow(() -> new RuntimeException("Area no encontrada"));

        PermisoArea permiso = permisoRepository
                .findByUsuarioIdAndAreaId(request.usuarioId(), request.areaId())
                .orElse(new PermisoArea());

        permiso.setUsuario(usuario);
        permiso.setArea(area);
        permiso.setCanRead(request.canRead() != null ? request.canRead() : false);
        permiso.setCanWrite(request.canWrite() != null ? request.canWrite() : false);
        permiso.setCanDelete(request.canDelete() != null ? request.canDelete() : false);

        return toResponse(permisoRepository.save(permiso));
    }

    public void revocar(Long id) {
        permisoRepository.deleteById(id);
    }

    public boolean tienePermiso(String username, Long areaId, String tipo) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if ("ADMIN".equals(usuario.getRol().getNombre())) {
            return true;
        }

        return permisoRepository.findByUsuarioIdAndAreaId(usuario.getId(), areaId)
                .map(p -> switch (tipo) {
                    case "READ" -> p.getCanRead();
                    case "WRITE" -> p.getCanWrite();
                    case "DELETE" -> p.getCanDelete();
                    default -> false;
                })
                .orElse(false);
    }

    private PermisoAreaResponse toResponse(PermisoArea p) {
        return new PermisoAreaResponse(
                p.getId(),
                p.getUsuario().getId(),
                p.getUsuario().getNombre(),
                p.getArea().getId(),
                p.getArea().getNombre(),
                p.getCanRead(),
                p.getCanWrite(),
                p.getCanDelete()
        );
    }
}
