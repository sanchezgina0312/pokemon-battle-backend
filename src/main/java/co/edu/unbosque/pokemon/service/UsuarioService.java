package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;
import co.edu.unbosque.pokemon.util.LanzadorDeException;

/**
 * Servicio encargado de gestionar las operaciones CRUD de la entidad
 * Usuario.
 * <p>
 * Permite crear, consultar, actualizar y eliminar usuarios, así
 * como realizar búsquedas por diferentes atributos como username, correo y rol.
 * </p>
 * * <p>
 * Utiliza UsuarioRepository para la persistencia, ModelMapper para
 * la conversión entre entidades y DTOs, y LanzadorDeException para la
 * validación de datos.
 * </p>
 * */
@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository usuarioRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Constructor vacío.
	 */
	public UsuarioService() {
	}

	/**
	 * Valida las credenciales para el inicio de sesión. 
	 * * @param username Nombre de usuario ingresado.
	 * @param contrasenia Contraseña ingresada.
	 * @return UsuarioDTO si es válido, null en caso contrario.
	 */
	public UsuarioDTO validarLogin(String username, String contrasenia) {

		Optional<List<Usuario>> encontrados = usuarioRep.findByUsername(username);

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			Usuario usuario = encontrados.get().get(0);

			if (usuario.getContrasenia().equals(contrasenia)) {
				return mapper.map(usuario, UsuarioDTO.class);
			}
		}
		return null;
	}

	/**
	 * Crea un nuevo usuario.
	 * * @param data datos del usuario
	 * @return 0 si se creó correctamente, 1 si ya existe
	 */
	@Override
	public int create(UsuarioDTO data) {
		LanzadorDeException.verificarUsername(data.getNombre());
		LanzadorDeException.verificarContrasena(data.getContrasenia());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarRol(data.getRol());
		
		LanzadorDeException.verificarDuplicado(usuarioRep.existsByUsername(data.getNombre()),
				"El nombre de usuario " + data.getNombre() + " ya se encuentra registrado.");

		Usuario entity = mapper.map(data, Usuario.class);

		if (usuarioRep.existsByUsername(data.getNombre())) {
			return 1;
		} else {
			usuarioRep.save(entity);
			return 0;
		}
	}

	/**
	 * Obtiene todos los usuarios.
	 * * @return lista de usuarios
	 */
	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = (List<Usuario>) usuarioRep.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));

		return dtoList;
	}

	/**
	 * Elimina un usuario por ID.
	 * * @param id identificador
	 * @return 0 si se eliminó, 1 si no existe
	 */
	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<Usuario> encontrado = usuarioRep.findById(id);

		if (encontrado.isPresent()) {
			usuarioRep.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Actualiza un usuario existente.
	 * * @param id   identificador
	 * @param data nuevos datos
	 * @return 0 si se actualizó, 1 si hay error
	 */
	@Override
	public int updateById(Long id, UsuarioDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarUsername(data.getNombre());
		LanzadorDeException.verificarContrasena(data.getContrasenia());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
		LanzadorDeException.verificarRol(data.getRol());

		Optional<Usuario> encontrado = usuarioRep.findById(id);

		if (encontrado.isPresent()) {
			Usuario temp = encontrado.get();

			if (!temp.getNombre().equals(data.getNombre())) {
				LanzadorDeException.verificarDuplicado(usuarioRep.existsByUsername(data.getNombre()),
						"No se puede actualizar: el username " + data.getNombre() + " ya pertenece a otro usuario.");

				if (usuarioRep.existsByUsername(data.getNombre())) {
					return 1;
				}
			}

			temp.setNombre(data.getNombre());
			temp.setContrasenia(data.getContrasenia());
			temp.setCorreo(data.getCorreo());
			temp.setRol(data.getRol());
			temp.setIdiomaPreferido(data.getIdiomaPreferido());
			temp.setDinero(data.getDinero());

			usuarioRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Cuenta los usuarios registrados.
	 * * @return cantidad total
	 */
	@Override
	public long count() {
		return usuarioRep.count();
	}

	/**
	 * Verifica si existe un usuario por ID.
	 * * @param id identificador
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return usuarioRep.existsById(id);
	}

	/**
	 * Busca por username.
	 */
	public List<UsuarioDTO> findByUsername(String username) {
		LanzadorDeException.verificarUsername(username);
		Optional<List<Usuario>> encontrados = usuarioRep.findByUsername(username);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));
		}

		return dtoList;
	}

	/**
	 * Busca por correo.
	 */
	public List<UsuarioDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);
		Optional<List<Usuario>> encontrados = usuarioRep.findByCorreo(correo);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));
		}

		return dtoList;
	}

	/**
	 * Busca por rol.
	 */
	public List<UsuarioDTO> findByRol(String rol) {
		LanzadorDeException.verificarRol(rol);
		Optional<List<Usuario>> encontrados = usuarioRep.findByRol(rol);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));
		}

		return dtoList;
	}

	/**
	 * Permite inyectar el repositorio manualmente (pruebas).
	 */
	public void setUsuarioRep(UsuarioRepository repo) {
		this.usuarioRep = repo;
	}

	/**
	 * Permite inyectar el mapper manualmente (pruebas).
	 */
	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	/**
	 * Busca usuarios por su nombre y devuelve una lista de DTOs.
	 * * @param nombre El nombre a buscar.
	 * @return Lista de UsuarioDTO.
	 */
	public List<UsuarioDTO> findByNombre(String nombre) {
	    // 1. Buscamos en la base de datos a través del repositorio
	    List<Usuario> listaEntidades = usuarioRep.findByNombre(nombre);
	    
	    // 2. Creamos la lista donde guardaremos los DTOs
	    List<UsuarioDTO> listaDTO = new ArrayList<>();
	    
	    // 3. Convertimos cada entidad a DTO (usando ModelMapper)
	    for (Usuario u : listaEntidades) {
	        listaDTO.add(mapper.map(u, UsuarioDTO.class));
	    }
	    
	    return listaDTO;
	}
}