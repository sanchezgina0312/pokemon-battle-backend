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

/**
 * Servicio encargado de la gestión de auditoría del sistema.
 * 
 * Registra acciones realizadas por usuarios autenticados o anónimos,
 * incluyendo login, acciones generales y eventos públicos.
 */
@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    /**
     * Registra una acción general realizada por el usuario autenticado.
     * 
     * Si no hay usuario autenticado, registra la acción como anónima.
     *
     * @param accion acción realizada (CREATE, UPDATE, DELETE, etc.)
     * @param entidad entidad sobre la que se realizó la acción
     */
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

        Auditoria auditoria = new Auditoria(
                idUsuario,
                correo,
                rol,
                accion,
                entidad,
                LocalDateTime.now()
        );

        auditoriaRepository.save(auditoria);
    }

    /**
     * Obtiene todos los registros de auditoría del sistema.
     *
     * @return lista de auditorías registradas
     */
    public List<Auditoria> getAll() {
        return (List<Auditoria>) auditoriaRepository.findAll();
    }

    /**
     * Registra específicamente un evento de login.
     *
     * @param correo correo del usuario
     * @param rol rol del usuario
     * @param idUsuario identificador del usuario
     */
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

    /**
     * Registra una acción realizada por un usuario no autenticado.
     *
     * @param correo correo asociado (si existe)
     * @param accion acción realizada
     * @param entidad entidad afectada
     */
    public void registrarAccionPublica(String correo, String accion, String entidad) {
        auditoriaRepository.save(
                new Auditoria(null, correo, "ANONIMO", accion, entidad, LocalDateTime.now())
        );
    }
}