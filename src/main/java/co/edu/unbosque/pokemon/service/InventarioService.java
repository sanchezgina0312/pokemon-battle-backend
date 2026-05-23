package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.InventarioDTO;
import co.edu.unbosque.pokemon.dto.ItemDetalleDTO;
import co.edu.unbosque.pokemon.entity.Inventario;
import co.edu.unbosque.pokemon.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository invRep;

    @Autowired
    private ModelMapper mapper;

    public List<InventarioDTO> verMochila(Long idUser) {
        Optional<List<Inventario>> mochila = invRep.findByIdUsuario(idUser);
        List<InventarioDTO> dtoList = new ArrayList<>();
        if (mochila.isPresent()) {
            mochila.get().forEach(ent -> dtoList.add(mapper.map(ent, InventarioDTO.class)));
        }
        return dtoList;
    }

    /**
     * Obtiene el catálogo completo consultando la API externa y mapeando los datos
     * al DTO de inventario, extrayendo el ID real desde la URL del recurso.
     * * @return Lista de InventarioDTO con los ítems procesados.
     */
    public List<InventarioDTO> obtenerCatalogoCompleto() {
        System.out.println("DEBUG: Consultando catálogo desde la API externa...");
        
        List<ItemDetalleDTO> todosLosItems = PokemonHTTPRequestHandler.obtenerTodosLosItems(); 

        List<InventarioDTO> dtoList = new ArrayList<>();

        if (todosLosItems != null && !todosLosItems.isEmpty()) {
            for (ItemDetalleDTO item : todosLosItems) {
                InventarioDTO dto = new InventarioDTO();
               
                if (item.getUrl() != null && !item.getUrl().isEmpty()) {
                    try {
                        String[] partes = item.getUrl().split("/");
                    
                        String idStr = partes[partes.length - 1];
                        dto.setIdItem(Long.parseLong(idStr));
                    } catch (NumberFormatException e) {
                        System.err.println("DEBUG: Error al convertir ID desde URL: " + item.getUrl());
                        dto.setIdItem(0L);
                    }
                } else {
                   
                    dto.setIdItem(item.getId() > 0 ? (long) item.getId() : 0L);
                }
                
                dto.setNombre(item.getNombreIngles());
                dto.setCantidad(0); 
               
                dtoList.add(dto);
            }
        } else {
            System.out.println("DEBUG: La lista de items recibida es nula o vacía.");
        }
        
        System.out.println("DEBUG: Se mapearon " + dtoList.size() + " objetos correctamente.");
        return dtoList;
    }}