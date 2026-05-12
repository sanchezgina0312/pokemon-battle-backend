package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.dto.CombateDTO;
import co.edu.unbosque.pokemon.entity.Combate;
import co.edu.unbosque.pokemon.repository.CombateRepository;

@Service
public class CombateService {

	@Autowired
	private CombateRepository combateRep;

	@Autowired
	private ModelMapper mapper;

	public CombateDTO crearRegistro(CombateDTO data) {
		Combate entidad = mapper.map(data, Combate.class);
		return mapper.map(combateRep.save(entidad), CombateDTO.class);
	}

	public List<CombateDTO> historialPeleas(Long idUser) {
		Optional<List<Combate>> encontrados = combateRep.findByIdUsuarioJugador(idUser);
		List<CombateDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent()) {
			encontrados.get().forEach(ent -> dtoList.add(mapper.map(ent, CombateDTO.class)));
		}
		return dtoList;
	}
}