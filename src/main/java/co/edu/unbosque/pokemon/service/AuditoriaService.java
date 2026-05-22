package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.Auditoria;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.AuditoriaRepository;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public void registrar(String accion, String entidad) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String correo = "anonimo";
        String rol = "desconocido";
        Long idUsuario = null;

        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {

            correo = auth.getName();

            rol = auth.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse("desconocido");

            if (auth.getPrincipal() instanceof Usuario usuario) {
                idUsuario = usuario.getId();
            }
        }

        Auditoria auditoria = new Auditoria(idUsuario, correo, rol, accion, entidad, LocalDateTime.now());
        auditoriaRepository.save(auditoria);
    }

    public List<Auditoria> getAll() {
        return (List<Auditoria>) auditoriaRepository.findAll();
    }
    
    public void registrarLogin(String correo, String rol, Long idUsuario) {
        Auditoria auditoria = new Auditoria(
            idUsuario,
            correo,
            rol,
            "LOGIN",
            "Sesión",
            LocalDateTime.now()
        );
        auditoriaRepository.save(auditoria);
    }
    
    public void registrarAccionPublica(String correo, String accion, String entidad) {
        auditoriaRepository.save(new Auditoria(null, correo, "ANONIMO", accion, entidad, LocalDateTime.now()));
    }
}