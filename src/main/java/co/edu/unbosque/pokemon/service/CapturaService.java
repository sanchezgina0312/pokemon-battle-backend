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

@Service
public class CapturaService {
	
	@Autowired
	private CapturaRepository capturaRep;
	@Autowired
	private ModelMapper mapper;

	public CapturaDTO registrar(CapturaDTO data) {
		Captura ent = mapper.map(data, Captura.class);
		ent.setFechaCaptura(LocalDateTime.now());
		return mapper.map(capturaRep.save(ent), CapturaDTO.class);
	}

	public List<CapturaDTO> obtenerPorUsuario(long idUsuario) {
		Optional<List<Captura>> lista = capturaRep.findByIdUsuario(idUsuario);
		List<CapturaDTO> dtoList = new ArrayList<>();
		if (lista.isPresent()) {
			lista.get().forEach(e -> dtoList.add(mapper.map(e, CapturaDTO.class)));
		}
		return dtoList;
	}
}