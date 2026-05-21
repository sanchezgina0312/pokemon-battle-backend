package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.PokemonRepository;
import co.edu.unbosque.pokemon.util.LanzadorDeException;

/**
 * Servicio encargado de gestionar las operaciones CRUD de la entidad Pokemon.
 * <p>
 * Permite crear (capturar), consultar, actualizar y eliminar (liberar) pokemones,
 * gestionando atributos de combate como salud, nivel y dueño, e integrando los
 * tipos oficiales consumidos de forma interna en el Backend desde la PokéAPI.
 * </p>
 */
@Service
public class PokemonService implements CRUDOperation<PokemonDTO> {

	@Autowired
	private PokemonRepository pokemonRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Constructor vacío.
	 */
	public PokemonService() {
	}

	/**
	 * Crea un nuevo registro de Pokemon en la base de datos.
	 * @param data datos del pokemon (apodo, apiId, propietario, etc.)
	 * @return 0 si se creó correctamente, 1 si hay error.
	 */
	@Override
	public int create(PokemonDTO data) {
		LanzadorDeException.verificarNombre(data.getApodo());
		LanzadorDeException.verificarId(data.getIdUsuarioPropietario()); 

		Pokemon entity = mapper.map(data, Pokemon.class);
		
		// Valores por defecto al "nacer" o ser capturado si no vienen en el DTO
		if (entity.getEstado() == null) entity.setEstado("OK");
		if (entity.getNivel() == 0) entity.setNivel(1);

		pokemonRep.save(entity);
		return 0;
	}

	/**
	 * Obtiene todos los pokemones registrados en el sistema enriquecidos con sus tipos de la PokéAPI.
	 * @return lista de PokemonDTO
	 */
	@Override
	public List<PokemonDTO> getAll() {
		List<Pokemon> entityList = (List<Pokemon>) pokemonRep.findAll();
		List<PokemonDTO> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			PokemonDTO dto = mapper.map(entity, PokemonDTO.class);
			enriquecerConTipos(dto);
			dtoList.add(dto);
		});

		return dtoList;
	}

	/**
	 * Elimina un pokemon por ID (Liberar al pokemon).
	 * @param id identificador único del pokemon
	 * @return 0 si se eliminó, 1 si no se encontró
	 */
	@Override
	public int deleteById(Long id) {
		LanzadorDeException.verificarId(id);
		Optional<Pokemon> encontrado = pokemonRep.findById(id);

		if (encontrado.isPresent()) {
			pokemonRep.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Actualiza los datos de un pokemon (subir de nivel, cambiar apodo, curar salud).
	 * @param id identificador
	 * @param data nuevos datos
	 * @return 0 si se actualizó, 1 si no existe
	 */
	@Override
	public int updateById(Long id, PokemonDTO data) {
		LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getApodo());

		Optional<Pokemon> encontrado = pokemonRep.findById(id);

		if (encontrado.isPresent()) {
			Pokemon temp = encontrado.get();

			// Actualización de atributos basados en tu estructura
			temp.setApodo(data.getApodo());
			temp.setNivel(data.getNivel());
			temp.setExperienciaAcumulada(data.getExperienciaAcumulada());
			temp.setSaludActual(data.getSaludActual());
			temp.setSaludMaxima(data.getSaludMaxima());
			temp.setEstado(data.getEstado());
			
			// Actualización de ataques
			temp.setNombreAtaque1(data.getNombreAtaque1());
			temp.setNombreAtaque2(data.getNombreAtaque2());
			temp.setNombreAtaque3(data.getNombreAtaque3());
			temp.setNombreAtaque4(data.getNombreAtaque4());

			pokemonRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Busca pokemones por su apodo enriquecidos con sus tipos correspondientes.
	 */
	public List<PokemonDTO> findByApodo(String apodo) {
		LanzadorDeException.verificarNombre(apodo);
		Optional<List<Pokemon>> encontrados = pokemonRep.findByApodo(apodo);
		List<PokemonDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> {
				PokemonDTO dto = mapper.map(entity, PokemonDTO.class);
				enriquecerConTipos(dto);
				dtoList.add(dto);
			});
		}

		return dtoList;
	}

	/**
	 * Busca todos los pokemones que pertenecen a un usuario enriquecidos con sus tipos correspondientes.
	 * @param idUsuario ID del entrenador propietario
	 */
	public List<PokemonDTO> findByPropietario(Long idUsuario) {
		LanzadorDeException.verificarId(idUsuario);
		Optional<List<Pokemon>> encontrados = pokemonRep.findByIdUsuarioPropietario(idUsuario);
		List<PokemonDTO> dtoList = new ArrayList<>();

		if (encontrados.isPresent()) {
			encontrados.get().forEach(entity -> {
				PokemonDTO dto = mapper.map(entity, PokemonDTO.class);
				enriquecerConTipos(dto);
				dtoList.add(dto);
			});
		}

		return dtoList;
	}

	// NUEVO: Método para obtener la especie utilizando el ID de la Pokédex (pokeApiId)
	/**
	 * Busca la primera especie registrada que coincida con el ID de la Pokédex.
	 * @param pokeApiId ID de la PokéAPI.
	 * @return El DTO del Pokémon o null si no se encuentra.
	 */
	public PokemonDTO obtenerEspeciePorPokeApiId(Integer pokeApiId) {
		Optional<Pokemon> encontrado = pokemonRep.findFirstByPokeApiId(pokeApiId);
		if (encontrado.isPresent()) {
			PokemonDTO dto = mapper.map(encontrado.get(), PokemonDTO.class);
			enriquecerConTipos(dto);
			return dto;
		}
		return null;
	}

	/**
	 * Método auxiliar interno privado para orquestar la llamada centralizada a la PokeAPI
	 * a través de tu manejador HTTP sin repetir código.
	 * @param dto Instancia del DTO local a enriquecer con los tipos externos.
	 */
	private void enriquecerConTipos(PokemonDTO dto) {
		if (dto.getPokeApiId() != null && dto.getPokeApiId() > 0) {
			List<String> listaNombresTipos = new ArrayList<>();
			
			co.edu.unbosque.pokemon.dto.InformacionPokemonDTO detalleApi = 
					PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(dto.getPokeApiId()));
			
			if (detalleApi != null && detalleApi.getListaTipos() != null) {
				for (co.edu.unbosque.pokemon.dto.TipoPokemonDTO t : detalleApi.getListaTipos()) {
					if (t.getInformacionTipo() != null && t.getInformacionTipo().getNombreTipo() != null) {
						listaNombresTipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
					}
				}
			}
			dto.setTipos(listaNombresTipos);
		}
	}

	@Override
	public long count() {
		return pokemonRep.count();
	}

	@Override
	public boolean exist(Long id) {
		LanzadorDeException.verificarId(id);
		return pokemonRep.existsById(id);
	}

	// Setters para inyección manual en pruebas
	public void setPokemonRep(PokemonRepository repo) {
		this.pokemonRep = repo;
	}

	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}
}