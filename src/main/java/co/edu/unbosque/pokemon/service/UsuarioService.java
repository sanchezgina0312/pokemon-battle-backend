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

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRep;

	@Autowired
	private ModelMapper mapper;

	public UsuarioDTO login(String u, String p) {
		Optional<Usuario> encontrado = userRep.findByUsername(u);
		if (encontrado.isPresent()) {
			Usuario ent = encontrado.get();
			if (ent.getContrasenia().equals(p)) {
				return mapper.map(ent, UsuarioDTO.class);
			}
		}
		return null;
	}

	public List<UsuarioDTO> obtenerJugadores() {
		Optional<List<Usuario>> encontrados = userRep.findByRol("JUGADOR");
		List<UsuarioDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent()) {
			encontrados.get().forEach(ent -> dtoList.add(mapper.map(ent, UsuarioDTO.class)));
		}
		return dtoList;
	}
}