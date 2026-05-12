package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.pokemon.entity.Pokemon;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {

	public List<Pokemon> findAll();
	public Optional<List<Pokemon>> findByIdUsuarioPropietario(Long idUsuarioPropietario);
}
