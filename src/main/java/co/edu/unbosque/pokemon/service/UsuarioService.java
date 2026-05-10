package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.entity.*;
import co.edu.unbosque.pokemon.repository.*;
import java.util.List;

@Service
public class UsuarioService {
	@Autowired private UsuarioRepository userRep;
	@Autowired private PokemonRepository pokeRep;

	public List<Usuario> listarTodosLosUsuarios() {
		return (List<Usuario>) userRep.findAll();
	}

	public List<Pokemon> listarTodosLosPokemones() {
		return (List<Pokemon>) pokeRep.findAll();
	}
}