package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

	public int curar(long idPokemon) {
		Optional<Pokemon> pOpt = pokeRep.findById(idPokemon);

		if (pOpt.isPresent()) {
			Pokemon p = pOpt.get();
			p.setSaludActual(p.getSaludMaxima());

			pokeRep.save(p);
			centroRep.save(new CentroPokemon(p.getIdUsuarioPropietario(), idPokemon, LocalDateTime.now()));

			return 0;
		}
		return 1;
	}
	

	public int curarEquipo(List<Long> idsPokemon, Long idUsuarioPropietarioAutenticado) {
		boolean algunoCurado = false;
		
		for (Long idPokemon : idsPokemon) {
			Optional<Pokemon> pOpt = pokeRep.findById(idPokemon);
			if (pOpt.isPresent()) {
				Pokemon p = pOpt.get();
                if (p.getIdUsuarioPropietario().equals(idUsuarioPropietarioAutenticado)) {
                    p.setSaludActual(p.getSaludMaxima());
                    p.setEstado("OK");
                    p.setEstadoAlterado(null); 
                    pokeRep.save(p);
                    centroRep.save(new CentroPokemon(idUsuarioPropietarioAutenticado, idPokemon, LocalDateTime.now()));
                    algunoCurado = true;
                } else {
                    System.out.println("⚠️ Intento de curar un Pokémon ajeno. ID Pokémon: " + idPokemon);
                }
			}
		}
		
		return algunoCurado ? 0 : 1;
	}}