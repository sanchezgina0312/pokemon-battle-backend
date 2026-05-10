package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Captura;

public interface CapturaRepository extends CrudRepository<Captura, Long> {
	
	public Optional<List<Captura>> findByIdUsuario(long idUsuario);
}
