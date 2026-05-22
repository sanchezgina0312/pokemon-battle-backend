package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.InventarioDTO;
import co.edu.unbosque.pokemon.entity.Inventario;
import co.edu.unbosque.pokemon.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository invRep;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuditoriaService auditoriaService;

    public List<InventarioDTO> verMochila(Long idUser) {
        Optional<List<Inventario>> mochila = invRep.findByIdUsuario(idUser);
        List<InventarioDTO> dtoList = new ArrayList<>();
        if (mochila.isPresent()) {
            mochila.get().forEach(ent -> dtoList.add(mapper.map(ent, InventarioDTO.class)));
        }
        auditoriaService.registrar("CONSULTAR_MOCHILA", "Usuario ID: " + idUser);
        return dtoList;
    }
}