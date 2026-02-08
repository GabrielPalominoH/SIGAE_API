package com.sigae.demo.repository;

import com.sigae.demo.entity.PermisoArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermisoAreaRepository extends JpaRepository<PermisoArea, Long> {
    List<PermisoArea> findByUsuarioId(Long usuarioId);
    Optional<PermisoArea> findByUsuarioIdAndAreaId(Long usuarioId, Long areaId);
}
