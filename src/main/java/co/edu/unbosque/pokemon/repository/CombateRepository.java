package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Combate;

public interface CombateRepository extends CrudRepository<Combate, Long> {

	public Optional<List<Combate>> findByIdUsuarioJugador(long idUsuarioJugador);
}
