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

/**
 * Servicio encargado de la gestión de ataques dentro del sistema Pokémon.
 * <p>
 * Permite actualizar el poder de los ataques, cambiar su estado de baneo
 * y consultar los ataques que han sido baneados.
 * </p>
 *
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

    /**
     * Actualiza el poder modificado de un ataque.
     *
     * @param nombre nombre del ataque.
     * @param nuevoPoder nuevo valor de poder.
     * @return 0 si la actualización fue exitosa, 1 si el ataque no existe.
     */
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

    /**
     * Cambia el estado de baneo de un ataque.
     *
     * @param nombre nombre del ataque.
     * @param estado true si se quiere banear, false si se quiere habilitar.
     */
    public void cambiarEstadoBaneo(String nombre, boolean estado) {
        Optional<Ataque> encontrado = ataqueRep.findById(nombre);

        if (encontrado.isPresent()) {
            Ataque a = encontrado.get();
            a.setEstaBaneado(estado);
            ataqueRep.save(a);
        }
    }

    /**
     * Obtiene todos los ataques que están baneados.
     *
     * @return lista de ataques baneados en formato DTO.
     */
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
}