package com.sigae.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permisos_area",
       uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "area_id"}))
public class PermisoArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private AreaInventario area;

    @Column(nullable = false)
    private Boolean canRead = false;

    @Column(nullable = false)
    private Boolean canWrite = false;

    @Column(nullable = false)
    private Boolean canDelete = false;

    public PermisoArea() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public AreaInventario getArea() { return area; }
    public void setArea(AreaInventario area) { this.area = area; }

    public Boolean getCanRead() { return canRead; }
    public void setCanRead(Boolean canRead) { this.canRead = canRead; }

    public Boolean getCanWrite() { return canWrite; }
    public void setCanWrite(Boolean canWrite) { this.canWrite = canWrite; }

    public Boolean getCanDelete() { return canDelete; }
    public void setCanDelete(Boolean canDelete) { this.canDelete = canDelete; }
}
