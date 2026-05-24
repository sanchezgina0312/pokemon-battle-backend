package co.edu.unbosque.pokemon.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.pokemon.dto.ItemDTO;
import co.edu.unbosque.pokemon.repository.ItemRepository;

/**
 * Servicio encargado de la gestión de ítems del sistema Pokémon.
 * 
 * Permite obtener el listado completo de ítems registrados en la base de datos
 * y mapearlos a objetos DTO para su uso en la capa de presentación.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRep;

    @Autowired
    private ModelMapper mapper;

    /**
     * Obtiene todos los ítems registrados en el sistema.
     *
     * @return lista de ítems en formato DTO
     */
    public List<ItemDTO> listarTodos() {
        List<ItemDTO> dtos = new ArrayList<>();
        itemRep.findAll().forEach(e -> dtos.add(mapper.map(e, ItemDTO.class)));
        return dtos;
    }
}