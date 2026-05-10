package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Ataque;

public interface AtaqueRepository extends CrudRepository<Ataque,String> {
	
	public Optional<List<Ataque>> findByEstaBaneadoTrue();
}
