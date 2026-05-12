package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.CentroPokemon;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.CentroPokemonRepository;
import co.edu.unbosque.pokemon.repository.PokemonRepository;

@Service
public class CentroPokemonService {

	@Autowired
	private PokemonRepository pokeRep;

	@Autowired
	private CentroPokemonRepository centroRep;

	public int curarPokemon(long idPokemon) {
		if (!pokeRep.existsById(idPokemon)) {
			return 1;
		}

		Pokemon p = pokeRep.findById(idPokemon).get();

		p.setSaludActual(100);
		pokeRep.save(p);

		CentroPokemon visita = new CentroPokemon(p.getIdUsuarioPropietario(), idPokemon, LocalDateTime.now());
		centroRep.save(visita);
		return 0;
	}
}