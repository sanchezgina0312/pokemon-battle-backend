package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.TipoPokemonDTO;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.PokemonRepository;
import co.edu.unbosque.pokemon.util.LanzadorDeException;

@Service
public class PokemonService implements CRUDOperation<PokemonDTO> {

    @Autowired
    private PokemonRepository pokemonRep;

    @Autowired
    private ModelMapper mapper;

    public PokemonService() {
    }

    @Override
    public int create(PokemonDTO data) {
        LanzadorDeException.verificarNombre(data.getApodo());
        LanzadorDeException.verificarId(data.getIdUsuarioPropietario()); 
        Pokemon entity = mapper.map(data, Pokemon.class);
        if (entity.getEstado() == null) entity.setEstado("OK");
        if (entity.getNivel() == 0) entity.setNivel(1);
        pokemonRep.save(entity);
        return 0;
    }

    @Override
    public List<PokemonDTO> getAll() {
        List<Pokemon> entityList = (List<Pokemon>) pokemonRep.findAll();
        List<PokemonDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> {
            PokemonDTO dto = mapper.map(entity, PokemonDTO.class);
            enriquecerConTipos(dto);
            dtoList.add(dto);
        });
        return dtoList;
    }

    @Override
    public int deleteById(Long id) {
        LanzadorDeException.verificarId(id);
        Optional<Pokemon> encontrado = pokemonRep.findById(id);
        if (encontrado.isPresent()) {
            pokemonRep.delete(encontrado.get());
            return 0;
        }
        return 1;
    }

    @Override
    public int updateById(Long id, PokemonDTO data) {
        LanzadorDeException.verificarId(id);
        LanzadorDeException.verificarNombre(data.getApodo());
        Optional<Pokemon> encontrado = pokemonRep.findById(id);
        if (encontrado.isPresent()) {
            Pokemon temp = encontrado.get();
            temp.setApodo(data.getApodo());
            temp.setNivel(data.getNivel());
            temp.setExperienciaAcumulada(data.getExperienciaAcumulada());
            temp.setSaludActual(data.getSaludActual());
            temp.setSaludMaxima(data.getSaludMaxima());
            temp.setEstado(data.getEstado());
            temp.setNombreAtaque1(data.getNombreAtaque1());
            temp.setNombreAtaque2(data.getNombreAtaque2());
            temp.setNombreAtaque3(data.getNombreAtaque3());
            temp.setNombreAtaque4(data.getNombreAtaque4());
            pokemonRep.save(temp);
            return 0;
        }
        return 1;
    }

    // --- MÉTODOS DE BÚSQUEDA ---

    public List<PokemonDTO> findByApodo(String apodo) {
        LanzadorDeException.verificarNombre(apodo);
        Optional<List<Pokemon>> encontrados = pokemonRep.findByApodo(apodo);
        List<PokemonDTO> dtoList = new ArrayList<>();
        if (encontrados.isPresent()) {
            encontrados.get().forEach(e -> {
                PokemonDTO dto = mapper.map(e, PokemonDTO.class);
                enriquecerConTipos(dto);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    public List<PokemonDTO> findByPropietario(Long idUsuario) {
        LanzadorDeException.verificarId(idUsuario);
        Optional<List<Pokemon>> encontrados = pokemonRep.findByIdUsuarioPropietario(idUsuario);
        List<PokemonDTO> dtoList = new ArrayList<>();
        if (encontrados.isPresent()) {
            encontrados.get().forEach(e -> {
                PokemonDTO dto = mapper.map(e, PokemonDTO.class);
                enriquecerConTipos(dto);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    /**
     * Mantiene compatibilidad con el sistema de usuarios (Pokémon ya capturados).
     */
    public PokemonDTO obtenerEspeciePorPokeApiId(Integer pokeApiId) {
        Optional<Pokemon> encontrado = pokemonRep.findFirstByPokeApiId(pokeApiId);
        if (encontrado.isPresent()) {
            PokemonDTO dto = mapper.map(encontrado.get(), PokemonDTO.class);
            enriquecerConTipos(dto);
            return dto;
        }
        return null;
    }

 
    public PokemonDTO obtenerEspecieParaAdmin(Integer pokeApiId) {
        InformacionPokemonDTO info = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokeApiId));
        if (info != null) {
            PokemonDTO dto = new PokemonDTO();
            dto.setPokeApiId(pokeApiId);
            dto.setApodo(info.getNombre().toUpperCase());
            
   
            List<String> ataques = PokemonHTTPRequestHandler.extraerCuatroPrimerosAtaques(info.getListaAtaques());
            dto.setNombreAtaque1(ataques.get(0));
            dto.setNombreAtaque2(ataques.get(1));
            dto.setNombreAtaque3(ataques.get(2));
            dto.setNombreAtaque4(ataques.get(3));
            
  
            List<String> tipos = new ArrayList<>();
            if (info.getListaTipos() != null) {
                for (TipoPokemonDTO t : info.getListaTipos()) {
                    if (t.getInformacionTipo() != null) {
                        tipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
                    }
                }
            }
            dto.setTipos(tipos);

            dto.setSaludMaxima(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "hp"));
            dto.setAtaque(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "attack"));
            dto.setDefensa(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "defense"));
            dto.setVelocidad(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "speed"));
      
            
            return dto;
        }
        return null;
    }

    private void enriquecerConTipos(PokemonDTO dto) {
        if (dto.getPokeApiId() != null && dto.getPokeApiId() > 0) {
            List<String> listaNombresTipos = new ArrayList<>();
            InformacionPokemonDTO detalleApi = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(dto.getPokeApiId()));
            
            if (detalleApi != null && detalleApi.getListaTipos() != null) {
                for (TipoPokemonDTO t : detalleApi.getListaTipos()) {
                    if (t.getInformacionTipo() != null && t.getInformacionTipo().getNombreTipo() != null) {
                        listaNombresTipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
                    }
                }
            }
            dto.setTipos(listaNombresTipos);
        }
    }

    @Override
    public long count() { return pokemonRep.count(); }

    @Override
    public boolean exist(Long id) {
        LanzadorDeException.verificarId(id);
        return pokemonRep.existsById(id);
    }
}