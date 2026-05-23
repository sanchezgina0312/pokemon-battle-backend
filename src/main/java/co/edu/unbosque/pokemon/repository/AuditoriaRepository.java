package co.edu.unbosque.pokemon.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Auditoria;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link Auditoria}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la tabla de auditoría.
 * </p>
 *
 * @version 1.0
 */
public interface AuditoriaRepository extends CrudRepository<Auditoria, Long> {

}