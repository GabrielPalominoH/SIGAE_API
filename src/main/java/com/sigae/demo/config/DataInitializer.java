package com.sigae.demo.config;

import com.sigae.demo.entity.Rol;
import com.sigae.demo.entity.Usuario;
import com.sigae.demo.repository.RolRepository;
import com.sigae.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Rol adminRol = rolRepository.findByNombre("ADMIN")
                .orElseGet(() -> {
                    Rol r = new Rol();
                    r.setNombre("ADMIN");
                    r.setDescripcion("Administrador del sistema");
                    return rolRepository.save(r);
                });

        rolRepository.findByNombre("MANAGER")
                .orElseGet(() -> {
                    Rol r = new Rol();
                    r.setNombre("MANAGER");
                    r.setDescripcion("Encargado de area de inventario");
                    return rolRepository.save(r);
                });

        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Administrador");
            admin.setEmail("admin@sigae.com");
            admin.setRol(adminRol);
            admin.setActivo(true);
            usuarioRepository.save(admin);
        }
    }
}
