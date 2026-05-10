package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.PokemonRepository;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;

@Service
public class PokemonService {

	@Autowired
	private PokemonRepository pokemonRep;

	public Pokemon crearPokemon(int apiId, long idDuenio, boolean esInicial) {
		InformacionPokemonDTO info = null;
		for (InformacionPokemonDTO p : PokemonHTTPRequestHandler.getPokedexDatos()) {
			if (p.getId() == apiId) {
				info = p;
				break;
			}
		}

		Pokemon nuevo = new Pokemon();
		nuevo.setPokeApiId(apiId);
		nuevo.setApodo(info.getNombre().toUpperCase());
		nuevo.setNivel(esInicial ? 5 : 2);
		nuevo.setExperienciaAcumulada(0);
		nuevo.setIdUsuarioPropietario(idDuenio);

		nuevo.setNombreAtaque1(info.getListaAtaques().get(0).getInformacionAtaque().getNombre());

		return pokemonRep.save(nuevo);
	}
}