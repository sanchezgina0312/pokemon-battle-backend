package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;
import co.edu.unbosque.pokemon.util.LanzadorDeException;

/**
 * Servicio encargado de la gestión de usuarios del sistema Pokémon.
 * <p>
 * Implementa operaciones CRUD, autenticación, búsquedas por diferentes campos
 * y manejo de datos sensibles como contraseñas encriptadas.
 * <p>
 * Utiliza:
 * <ul>
 * <li>UsuarioRepository para acceso a datos</li>
 * <li>ModelMapper para conversión entidad-DTO</li>
 * <li>PasswordEncoder para encriptación de contraseñas</li>
 * <li>LanzadorDeException para validaciones de negocio</li>
 * </ul>
 */
@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

    @Autowired
    private UsuarioRepository usuarioRep;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService() {
    }

    /**
     * Cuenta el total de usuarios registrados.
     *
     * @return número total de usuarios.
     */
    @Override
    public long count() {
        return usuarioRep.count();
    }

    /**
     * Verifica si un usuario existe por ID.
     *
     * @param id identificador del usuario.
     * @return true si existe, false si no.
     */
    @Override
    public boolean exist(Long id) {
        LanzadorDeException.verificarId(id);
        return usuarioRep.existsById(id);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param data información del usuario.
     * @return 0 si se crea correctamente, 1 si hay error o duplicado.
     */
    @Override
    public int create(UsuarioDTO data) {
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarContrasena(data.getContrasenia());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());

        LanzadorDeException.verificarCorreoDuplicado(
                usuarioRep.existsByCorreo(data.getCorreo()));

        if (data.getNombre() == null || data.getNombre().isBlank()) return 1;
        if (data.getCorreo() == null || data.getCorreo().isBlank()) return 1;
        if (data.getContrasenia() == null || data.getContrasenia().isBlank()) return 1;

        Usuario entity = mapper.map(data, Usuario.class);
        entity.setContrasenia(passwordEncoder.encode(data.getContrasenia()));

        if (usuarioRep.existsByNombre(data.getNombre())) {
            return 1;
        }

        usuarioRep.save(entity);
        return 0;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios en formato DTO.
     */
    @Override
    public List<UsuarioDTO> getAll() {
        Iterable<Usuario> entityList = usuarioRep.findAll();
        List<UsuarioDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> {
            UsuarioDTO dto = mapper.map(entity, UsuarioDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
    }

    /**
     * Elimina un usuario por ID.
     *
     * @param id identificador del usuario.
     * @return 0 si se elimina, 1 si no existe.
     */
    @Override
    public int deleteById(Long id) {
        LanzadorDeException.verificarId(id);

        if (usuarioRep.existsById(id)) {
            usuarioRep.deleteById(id);
            return 0;
        }

        return 1;
    }

    /**
     * Actualiza la información de un usuario.
     *
     * @param id   identificador del usuario.
     * @param data nuevos datos del usuario.
     * @return 0 si se actualiza correctamente, 1 si falla.
     */
    @Override
    public int updateById(Long id, UsuarioDTO data) {
        LanzadorDeException.verificarId(id);
        LanzadorDeException.verificarNombre(data.getNombre());

        if (data.getContrasenia() != null && !data.getContrasenia().isBlank()) {
            LanzadorDeException.verificarContrasena(data.getContrasenia());
        }

        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());

        Optional<Usuario> encontrado = usuarioRep.findById(id);

        if (encontrado.isPresent()) {
            Usuario temp = encontrado.get();

            if (!temp.getNombre().equals(data.getNombre())) {
                LanzadorDeException.verificarCorreoDuplicado(
                        usuarioRep.existsByNombre(data.getNombre()));

                if (usuarioRep.existsByNombre(data.getNombre())) {
                    return 1;
                }
            }

            temp.setNombre(data.getNombre());
            temp.setCorreo(data.getCorreo());
            temp.setIdiomaPreferido(data.getIdiomaPreferido());
            temp.setDinero(data.getDinero());

            if (data.getContrasenia() != null && !data.getContrasenia().isBlank()) {
                temp.setContrasenia(passwordEncoder.encode(data.getContrasenia()));
            }

            usuarioRep.save(temp);
            return 0;
        }

        return 1;
    }

    /**
     * Incrementa el dinero de un usuario (recompensa por combate).
     *
     * @param idUsuario identificador del usuario.
     * @param cantidad  dinero a sumar.
     */
    public void sumarDinero(Long idUsuario, int cantidad) {
        Optional<Usuario> encontrado = usuarioRep.findById(idUsuario);

        if (encontrado.isPresent()) {
            Usuario u = encontrado.get();
            u.setDinero(u.getDinero() + cantidad);
            usuarioRep.save(u);
        }
    }

    /**
     * Verifica si un nombre de usuario ya está en uso.
     *
     * @param nombre nombre a validar.
     * @return true si ya existe, false si no.
     */
    public boolean findUsernameAlreadyTaken(String nombre) {
        Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
        return encontrado.isPresent();
    }

    /**
     * Busca usuarios por nombre.
     *
     * @param nombre nombre del usuario.
     * @return lista de coincidencias.
     */
    public List<UsuarioDTO> findByNombre(String nombre) {
        LanzadorDeException.verificarNombre(nombre);

        Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
        List<UsuarioDTO> dtoList = new ArrayList<>();

        encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

        return dtoList;
    }

    /**
     * Busca usuarios por correo electrónico.
     *
     * @param correo correo del usuario.
     * @return lista de coincidencias.
     */
    public List<UsuarioDTO> findByCorreo(String correo) {
        LanzadorDeException.verificarCorreoElectronico(correo);

        Optional<Usuario> encontrado = usuarioRep.findByCorreo(correo);
        List<UsuarioDTO> dtoList = new ArrayList<>();

        encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

        return dtoList;
    }

    /**
     * Busca usuarios por rol.
     *
     * @param rol rol del usuario.
     * @return lista de usuarios con ese rol.
     */
    public List<UsuarioDTO> findByRol(String rol) {
        Optional<Usuario> encontrado = usuarioRep.findByRol(rol);
        List<UsuarioDTO> dtoList = new ArrayList<>();

        encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

        return dtoList;
    }

    /**
     * Autenticación de usuario por correo y contraseña.
     *
     * @param correo      correo del usuario.
     * @param contrasenia contraseña en texto plano.
     * @return 0 si login correcto, 1 si contraseña incorrecta, 2 si no existe usuario.
     */
    public int login(String correo, String contrasenia) {
        LanzadorDeException.verificarCorreoElectronico(correo);

        Optional<Usuario> encontrado = usuarioRep.findByCorreo(correo);

        if (encontrado.isEmpty()) return 2;

        Usuario usuario = encontrado.get();

        if (passwordEncoder.matches(contrasenia, usuario.getContrasenia())) {
            return 0;
        }

        return 1;
    }

    /**
     * Actualiza el género de un usuario.
     *
     * @param id     identificador del usuario.
     * @param genero nuevo género.
     * @return 0 si se actualiza, 1 si no existe.
     */
    public int actualizarGenero(Long id, String genero) {
        Optional<Usuario> encontrado = usuarioRep.findById(id);

        if (encontrado.isPresent()) {
            Usuario temp = encontrado.get();
            temp.setGenero(genero);
            usuarioRep.save(temp);
            return 0;
        }

        return 1;
    }

    public UsuarioRepository getUsuarioRep() {
        return usuarioRep;
    }

    public void setUsuarioRep(UsuarioRepository usuarioRep) {
        this.usuarioRep = usuarioRep;
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}