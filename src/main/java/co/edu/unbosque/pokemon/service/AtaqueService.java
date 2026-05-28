package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.dto.AtaqueDTO;
import co.edu.unbosque.pokemon.dto.ItemDetalleDTO;
import co.edu.unbosque.pokemon.entity.Ataque;
import co.edu.unbosque.pokemon.repository.AtaqueRepository;

/**
 * Servicio encargado de la gestión de ataques, permitiendo la consulta,
 * actualización de estadísticas y control de estados de baneo de los ataques
 * Pokémon.
 */
@Service
public class AtaqueService {

	/** Repositorio para la persistencia de los ataques. */
	@Autowired
	private AtaqueRepository ataqueRep;
	
	/** Mapeador para transformar entidades de base de datos a objetos DTO. */
	@Autowired
	private ModelMapper mapper;

	/**
     * Actualiza el poder base de un ataque específico en la base de datos.
     *
     * @param nombre     Nombre del ataque a modificar.
     * @param nuevoPoder El nuevo valor de poder a asignar.
     * @return 0 si la actualización fue exitosa, 1 si el ataque no fue encontrado.
     */
	public int actualizarPoder(String nombre, Integer nuevoPoder) {
		Optional<Ataque> encontrado = ataqueRep.findById(nombre);

		if (encontrado.isPresent()) {
			Ataque a = encontrado.get();
			a.setPoderModificado(nuevoPoder);
			ataqueRep.save(a);
			return 0;
		}
		return 1;
	}

	/**
     * Cambia el estado de baneo de un ataque (activo o inhabilitado).
     *
     * @param nombre Nombre del ataque.
     * @param estado {@code true} para banear el ataque, {@code false} para habilitarlo.
     */
	public void cambiarEstadoBaneo(String nombre, boolean estado) {
		Optional<Ataque> encontrado = ataqueRep.findById(nombre);

		if (encontrado.isPresent()) {
			Ataque a = encontrado.get();
			a.setEstaBaneado(estado);
			ataqueRep.save(a);
		}
	}

	/**
	 * Obtiene el catálogo completo de ataques disponibles.
	 * <p>
	 * Este método implementa una lógica de carga bajo demanda: primero consulta los
	 * ataques almacenados en la base de datos local. Si la base de datos está
	 * vacía, realiza una petición a la PokeAPI para obtener todos los ataques, los
	 * registra en el sistema local y retorna la lista mapeada a {@link AtaqueDTO}.
	 * </p>
	 *
	 * @return Una lista de {@link AtaqueDTO} con todos los ataques disponibles en
	 *         el sistema.
	 */
	public List<AtaqueDTO> obtenerCatalogoAtaques() {
		Iterable<Ataque> existentes = ataqueRep.findAll();

		List<Ataque> listaExistentes = new ArrayList<>();
		existentes.forEach(ataque -> listaExistentes.add(ataque));

		if (!listaExistentes.isEmpty()) {
			return listaExistentes.stream().map(a -> mapper.map(a, AtaqueDTO.class)).collect(Collectors.toList());
		}

		List<ItemDetalleDTO> todosLosAtaques = PokemonHTTPRequestHandler.obtenerTodosLosAtaques();
		List<AtaqueDTO> dtoList = new ArrayList<>();

		if (todosLosAtaques != null) {
			for (ItemDetalleDTO item : todosLosAtaques) {
				try {
					String[] partes = item.getUrl().split("/");
					long id = Long.parseLong(partes[partes.length - 1]);

					Ataque nuevaEntidad = new Ataque();
					nuevaEntidad.setId(id);
					nuevaEntidad.setNombre(item.getNombreIngles());
					nuevaEntidad.setPoderModificado(0);
					nuevaEntidad.setEstaBaneado(false);

					ataqueRep.save(nuevaEntidad);
					dtoList.add(mapper.map(nuevaEntidad, AtaqueDTO.class));
				} catch (Exception e) {
					System.err.println("Error procesando el ataque: " + item.getNombreIngles());
				}
			}
		}

		return dtoList;
	}
}