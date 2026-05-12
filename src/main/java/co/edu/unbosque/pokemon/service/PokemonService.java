package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.PokemonRepository;

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

		int nivelAsignado = esInicial ? 5 : 2;
		nuevo.setNivel(nivelAsignado);
		nuevo.setExperienciaAcumulada(0);
		nuevo.setIdUsuarioPropietario(idDuenio);

		int vidaCalculada = 50 + (nivelAsignado * 10);

		nuevo.setSaludMaxima(vidaCalculada);
		nuevo.setSaludActual(vidaCalculada);

		if (info.getListaAtaques() != null && !info.getListaAtaques().isEmpty()) {
			nuevo.setNombreAtaque1(info.getListaAtaques().get(0).getInformacionAtaque().getNombre());
		}

		return pokemonRep.save(nuevo);
	}
}