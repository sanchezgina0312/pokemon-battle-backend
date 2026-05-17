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
 * Servicio encargado de gestionar las operaciones CRUD de la entidad Usuario.
 * <p>
 * Permite crear, consultar, actualizar y eliminar usuarios, así como realizar
 * búsquedas por diferentes atributos como username, correo y rol.
 * </p>
 *
 * <p>
 * Utiliza UsuarioRepository para la persistencia, ModelMapper para la
 * conversión entre entidades y DTOs, y LanzadorDeException para la validación
 * de datos.
 * </p>
 * * @author Gina Sánchez
 * 
 * @version 1.0
 */
@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	/**
	 * Repositorio inyectado para realizar operaciones de persistencia en la base de
	 * datos.
	 */
	@Autowired
	private UsuarioRepository usuarioRep;

	/**
	 * Convertidor inyectado para mapear objetos de DTO a Entidad y viceversa.
	 */
	@Autowired
	private ModelMapper mapper;

	/**
	 * Codificador inyectado para cifrar de forma segura las contraseñas de los
	 * usuarios.
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Constructor vacío de la clase.
	 */
	public UsuarioService() {

	}

	/**
	 * Cuenta los usuarios registrados. * @return cantidad total
	 */
	@Override
	public long count() {
		return usuarioRep.count();
	}

	/**
	 * Verifica si existe un usuario por ID. * @param id identificador
	 * 
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return usuarioRep.existsById(id);
	}

	/**
	 * Crea un nuevo usuario. * @param data datos del usuario
	 * 
	 * @return 0 si se creó correctamente, 1 si ya existe
	 */
	@Override
	public int create(UsuarioDTO data) {
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarContrasena(data.getContrasenia());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());

		LanzadorDeException.verificarCorreoDuplicado(usuarioRep.existsByNombre(data.getNombre()));

		if (data.getNombre() == null || data.getNombre().isBlank()) {
			return 1;
		}

		if (data.getCorreo() == null || data.getCorreo().isBlank()) {
			return 1;
		}

		if (data.getContrasenia() == null || data.getContrasenia().isBlank()) {
			return 1;
		}

		Usuario entity = mapper.map(data, Usuario.class);
		entity.setContrasenia(passwordEncoder.encode(data.getContrasenia()));

		if (usuarioRep.existsByNombre(data.getNombre())) {
			return 1;
		} else {
			usuarioRep.save(entity);
			return 0;
		}
	}

	/**
	 * Obtiene todos los usuarios. * @return lista de usuarios
	 */
	@Override
	public List<UsuarioDTO> getAll() {
		Iterable<Usuario> entityList = usuarioRep.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {
			UsuarioDTO dto = mapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	/**
	 * Elimina un usuario por ID. * @param id identificador
	 * 
	 * @return 0 si se eliminó, 1 si no existe
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
	 * Actualiza un usuario existente. * @param id identificador
	 * 
	 * @param data nuevos datos
	 * @return 0 si se actualizó, 1 si hay error
	 */
	@Override
	public int updateById(Long id, UsuarioDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarContrasena(data.getContrasenia());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());

		Optional<Usuario> encontrado = usuarioRep.findById(id);

		if (encontrado.isPresent()) {
			Usuario temp = encontrado.get();

			if (!temp.getNombre().equals(data.getNombre())) {

				LanzadorDeException.verificarCorreoDuplicado(usuarioRep.existsByNombre(data.getNombre()));
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

		} else {
			return 1;
		}
	}

	/**
	 * Valida las credenciales de un usuario.
	 *
	 * @param nombre      Nombre de usuario
	 * @param contrasenia Contraseña sin encriptar
	 * @return 0 si las credenciales son válidas, 1 si son inválidas
	 */
	public int validateCredentials(String nombre, String contrasenia) {
		// Buscar usuario por nombre de usuario
		Optional<Usuario> userOpt = usuarioRep.findByNombre(nombre);

		if (userOpt.isPresent()) {
			Usuario usuario = userOpt.get();
			if (passwordEncoder.matches(contrasenia, usuario.getPassword())) {
				return 0;
			}
		}

		return 1;
	}

	/**
	 * Verifica si un nombre de usuario ya está en uso.
	 *
	 * @param nombre Nombre de usuario a verificar
	 * @return true si el nombre de usuario ya está en uso, false en caso contrario
	 */
	public boolean findUsernameAlreadyTaken(String nombre) {
		Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
		return encontrado.isPresent();
	}

	/**
	 * Busca usuarios filtrando por su nombre exacto. * @param nombre el nombre del
	 * usuario a buscar
	 * 
	 * @return una lista que contiene el UsuarioDTO mapeado, o vacía si no hay
	 *         coincidencias
	 */
	public List<UsuarioDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);

		Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

		return dtoList;
	}

	/**
	 * Busca un usuario filtrando por su correo electrónico. * @param correo el
	 * correo electrónico del usuario a buscar
	 * 
	 * @return una lista que contiene el UsuarioDTO mapeado, o vacía si no hay
	 *         coincidencias
	 */
	public List<UsuarioDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);

		Optional<Usuario> encontrado = usuarioRep.findByCorreo(correo);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

		return dtoList;
	}

	/**
	 * Busca usuarios filtrando por su rol específico. * @param rol el nombre del
	 * rol a buscar (ej. "Jugador", "Admin")
	 * 
	 * @return una lista con los UsuarioDTO que correspondan al rol especificado
	 */
	public List<UsuarioDTO> findByRol(String rol) {
		Optional<Usuario> encontrado = usuarioRep.findByRol(rol);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

		return dtoList;
	}

	/**
	 * Obtiene el repositorio de usuarios activo. * @return el objeto
	 * UsuarioRepository
	 */
	public UsuarioRepository getUsuarioRep() {
		return usuarioRep;
	}

	/**
	 * Configura el repositorio de usuarios. * @param usuarioRep el nuevo objeto
	 * UsuarioRepository
	 */
	public void setUsuarioRep(UsuarioRepository usuarioRep) {
		this.usuarioRep = usuarioRep;
	}

	/**
	 * Obtiene el mapeador de modelos activo. * @return el objeto ModelMapper
	 */
	public ModelMapper getMapper() {
		return mapper;
	}

	/**
	 * Configura el mapeador de modelos. * @param mapper el nuevo objeto ModelMapper
	 */
	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * Obtiene el encriptador de contraseñas activo. * @return el objeto
	 * PasswordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * Configura el encriptador de contraseñas. * @param passwordEncoder el nuevo
	 * objeto PasswordEncoder
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}