package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import co.edu.unbosque.pokemon.entity.Inventario;

@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Long> {

	public Optional<List<Inventario>> findByIdUsuario(long idUsuario);
}