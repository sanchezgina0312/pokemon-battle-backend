package co.edu.unbosque.pokemon.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.CapturaDTO;
import co.edu.unbosque.pokemon.entity.Captura;
import co.edu.unbosque.pokemon.repository.CapturaRepository;

/**
 * Servicio encargado de la gestión de capturas de Pokémon.
 * 
 * Permite registrar nuevas capturas y consultar las capturas realizadas
 * por un usuario específico, manejando el mapeo entre entidades y DTOs.
 */
@Service
public class CapturaService {
	
	@Autowired
	private CapturaRepository capturaRep;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Registra una nueva captura de Pokémon en el sistema.
	 * 
	 * Asigna automáticamente la fecha de captura al momento de guardarla.
	 *
	 * @param data DTO con la información de la captura
	 * @return CapturaDTO con la entidad guardada en base de datos
	 */
	public CapturaDTO registrar(CapturaDTO data) {
		Captura ent = mapper.map(data, Captura.class);
		ent.setFechaCaptura(LocalDateTime.now());
		return mapper.map(capturaRep.save(ent), CapturaDTO.class);
	}

	/**
	 * Obtiene todas las capturas realizadas por un usuario específico.
	 *
	 * @param idUsuario identificador del usuario
	 * @return lista de capturas del usuario en formato DTO
	 */
	public List<CapturaDTO> obtenerPorUsuario(long idUsuario) {
		Optional<List<Captura>> lista = capturaRep.findByIdUsuario(idUsuario);

		List<CapturaDTO> dtoList = new ArrayList<>();

		if (lista.isPresent()) {
			lista.get().forEach(e -> dtoList.add(mapper.map(e, CapturaDTO.class)));
		}

		return dtoList;
	}
}