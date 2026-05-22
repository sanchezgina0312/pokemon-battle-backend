package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.AtaqueDTO;
import co.edu.unbosque.pokemon.entity.Ataque;
import co.edu.unbosque.pokemon.repository.AtaqueRepository;

@Service
public class AtaqueService {

    @Autowired
    private AtaqueRepository ataqueRep;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuditoriaService auditoriaService;

    public int actualizarPoder(String nombre, Integer nuevoPoder) {
        Optional<Ataque> encontrado = ataqueRep.findById(nombre);
        if (encontrado.isPresent()) {
            Ataque a = encontrado.get();
            a.setPoderModificado(nuevoPoder);
            ataqueRep.save(a);
            auditoriaService.registrar("ACTUALIZAR_PODER", "Ataque: " + nombre + " | Nuevo poder: " + nuevoPoder);
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
            String accion = estado ? "BANEAR_ATAQUE" : "DESBANEAR_ATAQUE";
            auditoriaService.registrar(accion, "Ataque: " + nombre);
        }
    }

    public List<AtaqueDTO> obtenerAtaquesBaneados() {
        Optional<List<Ataque>> baneados = ataqueRep.findByEstaBaneadoTrue();
        List<AtaqueDTO> dtoList = new ArrayList<>();
        if (baneados.isPresent()) {
            baneados.get().forEach(ent -> dtoList.add(mapper.map(ent, AtaqueDTO.class)));
        }
        return dtoList;
    }
}