package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.CentroPokemon;
import co.edu.unbosque.pokemon.entity.Tienda;

public interface CentroPokemonRepository extends CrudRepository<CentroPokemon, Long> {

	public Optional<List<CentroPokemon>> findByIdUsuario(long idUsuario);
}
