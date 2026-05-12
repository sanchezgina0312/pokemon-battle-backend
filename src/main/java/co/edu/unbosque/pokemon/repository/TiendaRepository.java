package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.pokemon.entity.Tienda;

public interface TiendaRepository extends CrudRepository<Tienda, Long> {

	public Optional<List<Tienda>> findByIdUsuario(long idUsuario);
}
