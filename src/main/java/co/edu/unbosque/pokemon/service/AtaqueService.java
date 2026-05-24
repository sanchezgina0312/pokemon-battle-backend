package co.edu.unbosque.pokemon.service;
//
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
		    System.out.println("DEBUG: Consultando ataques desde la PokeAPI...");
		    
		    // Asumiendo que tienes un helper similar a PokemonHTTPRequestHandler para ataques
		    List<ItemDetalleDTO> todosLosAtaques = PokemonHTTPRequestHandler.obtenerTodosLosAtaques(); 

		    List<AtaqueDTO> dtoList = new ArrayList<>();

		    if (todosLosAtaques != null && !todosLosAtaques.isEmpty()) {
		        for (ItemDetalleDTO item : todosLosAtaques) {
		            AtaqueDTO dto = new AtaqueDTO();
		            
		            // Lógica de ID desde la URL igualita a la de Inventario
		            if (item.getUrl() != null && !item.getUrl().isEmpty()) {
		                try {
		                    String[] partes = item.getUrl().split("/");
		                    String idStr = partes[partes.length - 1];
		                    dto.setId(Long.parseLong(idStr));
		                } catch (NumberFormatException e) {
		                    System.err.println("DEBUG: Error al convertir ID de ataque: " + item.getUrl());
		                    dto.setId(0L);
		                }
		            }
		            
		            dto.setNombre(item.getNombreIngles());
		          
		            
		            dtoList.add(dto);
		        }
		    } else {
		        System.out.println("DEBUG: La lista de ataques recibida es nula o vacía.");
		    }
		    
		    System.out.println("DEBUG: Se mapearon " + dtoList.size() + " ataques correctamente.");
		    return dtoList;
		}
}