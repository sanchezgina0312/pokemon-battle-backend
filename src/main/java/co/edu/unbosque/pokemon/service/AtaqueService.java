package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.edu.unbosque.pokemon.dto.AtaqueDTO;
import co.edu.unbosque.pokemon.dto.ItemDetalleDTO;
import co.edu.unbosque.pokemon.entity.Ataque;
import co.edu.unbosque.pokemon.repository.AtaqueRepository;

@Service
public class AtaqueService {

	@Autowired
	private AtaqueRepository ataqueRep;

	@Autowired
	private ModelMapper mapper;

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

	public void cambiarEstadoBaneo(String nombre, boolean estado) {
		Optional<Ataque> encontrado = ataqueRep.findById(nombre);

		if (encontrado.isPresent()) {
			Ataque a = encontrado.get();
			a.setEstaBaneado(estado);
			ataqueRep.save(a);
		}
	}
	public List<AtaqueDTO> obtenerCatalogoAtaques() {
	    Iterable<Ataque> existentes = ataqueRep.findAll();
	    
	    List<Ataque> listaExistentes = new ArrayList<>();
	    existentes.forEach(listaExistentes::add);

	    if (!listaExistentes.isEmpty()) {
	        System.out.println("DEBUG: Cargando ataques desde la Base de Datos local.");
	        return listaExistentes.stream()
	            .map(a -> mapper.map(a, AtaqueDTO.class))
	            .collect(Collectors.toList());
	    }

	    System.out.println("DEBUG: Consultando ataques desde la PokeAPI por primera vez...");
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