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

/**
 * Servicio encargado de la gestión de ataques dentro del sistema Pokémon.
 * <p>
 * Permite actualizar el poder de los ataques, cambiar su estado de baneo
 * y consultar los ataques que han sido baneados.
 * </p>
 * <p>
 * Utiliza AtaqueRepository para el acceso a datos y ModelMapper para la
 * conversión entre entidades y DTOs.
 * </p>
 */
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

    public List<AtaqueDTO> obtenerAtaquesBaneados() {
        Optional<List<Ataque>> baneados = ataqueRep.findByEstaBaneadoTrue();
        List<AtaqueDTO> dtoList = new ArrayList<>();
        if (baneados.isPresent()) {
            baneados.get().forEach(ent ->
                dtoList.add(mapper.map(ent, AtaqueDTO.class))
            );
        }
        return dtoList;
    }

    public List<AtaqueDTO> obtenerCatalogoAtaques() {
        System.out.println("DEBUG: Consultando ataques desde la PokeAPI...");

        List<ItemDetalleDTO> todosLosAtaques = PokemonHTTPRequestHandler.obtenerTodosLosAtaques();
        List<AtaqueDTO> dtoList = new ArrayList<>();

        if (todosLosAtaques != null && !todosLosAtaques.isEmpty()) {
            for (ItemDetalleDTO item : todosLosAtaques) {
                AtaqueDTO dto = new AtaqueDTO();

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