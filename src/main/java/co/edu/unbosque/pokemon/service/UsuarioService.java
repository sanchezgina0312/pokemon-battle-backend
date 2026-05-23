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
import jakarta.transaction.Transactional;

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

	@Autowired
	private UsuarioRepository usuarioRep;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioService() {
	}

	@Override
	public long count() {
		return usuarioRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return usuarioRep.existsById(id);
	}

	@Override
	public int create(UsuarioDTO data) {
		LanzadorDeException.verificarNombre(data.getNombre());
		LanzadorDeException.verificarContrasena(data.getContrasenia());
		LanzadorDeException.verificarCorreoElectronico(data.getCorreo());

		LanzadorDeException.verificarCorreoDuplicado(usuarioRep.existsByCorreo(data.getCorreo()));

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

	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);

		if (usuarioRep.existsById(id)) {
			usuarioRep.deleteById(id);
			return 0;
		}

		return 1;
	}

	@Override
	public int updateById(Long id, UsuarioDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getNombre());
		// La contraseña es opcional en edición: solo se valida si viene con valor
		if (data.getContrasenia() != null && !data.getContrasenia().isBlank()) {
			LanzadorDeException.verificarContrasena(data.getContrasenia());
		}
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
	 * Incrementa el saldo de dinero de un usuario tras ganar un combate.
	 * @param idUsuario Identificador del usuario.
	 * @param cantidad Cantidad de dinero a sumar.
	 */
	public void sumarDinero(Long idUsuario, int cantidad) {
	    Optional<Usuario> encontrado = usuarioRep.findById(idUsuario);
	    if (encontrado.isPresent()) {
	        Usuario u = encontrado.get();
	        u.setDinero(u.getDinero() + cantidad);
	        usuarioRep.save(u);
	    }
	}

	public boolean findUsernameAlreadyTaken(String nombre) {
		Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
		return encontrado.isPresent();
	}

	public List<UsuarioDTO> findByNombre(String nombre) {
		LanzadorDeException.verificarNombre(nombre);

		Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

		return dtoList;
	}

	public List<UsuarioDTO> findByCorreo(String correo) {
		LanzadorDeException.verificarCorreoElectronico(correo);

		Optional<Usuario> encontrado = usuarioRep.findByCorreo(correo);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

		return dtoList;
	}

	public List<UsuarioDTO> findByRol(String rol) {
		Optional<Usuario> encontrado = usuarioRep.findByRol(rol);
		List<UsuarioDTO> dtoList = new ArrayList<>();

		encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));

		return dtoList;
	}

	/**
	 * Valida las credenciales de un usuario por correo electrónico.
	 * Usa PasswordEncoder para comparar la contraseña ingresada con el hash.
	 *
	 * @param correo      Correo electrónico del usuario
	 * @param contrasenia Contraseña sin encriptar
	 * @return 0 si las credenciales son válidas,
	 *         1 si la contraseña es incorrecta,
	 *         2 si el usuario no existe
	 */
	public int login(String correo, String contrasenia) {
		LanzadorDeException.verificarCorreoElectronico(correo);

		Optional<Usuario> encontrado = usuarioRep.findByCorreo(correo);

		if (encontrado.isEmpty()) {
			return 2;
		}

		Usuario usuario = encontrado.get();

		if (passwordEncoder.matches(contrasenia, usuario.getContrasenia())) {
			return 0;
		}

		return 1;
	}

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