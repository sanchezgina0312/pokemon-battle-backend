package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.entity.*;
import co.edu.unbosque.pokemon.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRep;

	@Autowired
	private PokemonRepository pokeRep;

	public Usuario login(String username, String password) {
		Optional<Usuario> usuarioEncontrado = userRep.findByUsername(username);

		if (usuarioEncontrado.isPresent()) {
			Usuario u = usuarioEncontrado.get();

			if (u.getContrasenia().equals(password)) {
				return u;
			}
		}

		return null;
	}

	public List<Usuario> obtenerTodosLosJugadores() {
		Optional<List<Usuario>> encontrados = userRep.findByRol("JUGADOR");

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			return encontrados.get();
		} else {
			return new ArrayList<Usuario>();
		}
	}

	public List<Pokemon> obtenerTodosLosPokemonesDelMundo() {
		return pokeRep.findAll();
	}
}