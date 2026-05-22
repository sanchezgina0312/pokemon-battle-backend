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

    @Autowired
    private AuditoriaService auditoriaService;

    public PokemonService() {}

    @Override
    public int create(PokemonDTO data) {
        LanzadorDeException.verificarNombre(data.getApodo());
        LanzadorDeException.verificarId(data.getIdUsuarioPropietario());

        Pokemon entity = mapper.map(data, Pokemon.class);

        if (entity.getEstado() == null) entity.setEstado("OK");
        if (entity.getNivel() == 0) entity.setNivel(1);

        pokemonRep.save(entity);
        auditoriaService.registrar("CREAR", "Pokemon: " + data.getApodo());
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

        auditoriaService.registrar("CONSULTAR_TODO", "Pokemon");
        return dtoList;
    }

    @Override
    public int deleteById(Long id) {
        LanzadorDeException.verificarId(id);
        Optional<Pokemon> encontrado = pokemonRep.findById(id);

        if (encontrado.isPresent()) {
            pokemonRep.delete(encontrado.get());
            auditoriaService.registrar("ELIMINAR", "Pokemon ID: " + id);
            return 0;
        } else {
            return 1;
        }
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
            auditoriaService.registrar("ACTUALIZAR", "Pokemon ID: " + id);
            return 0;
        } else {
            return 1;
        }
    }

    public List<PokemonDTO> findByApodo(String apodo) {
        LanzadorDeException.verificarNombre(apodo);
        Optional<List<Pokemon>> encontrados = pokemonRep.findByApodo(apodo);
        List<PokemonDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity -> {
                PokemonDTO dto = mapper.map(entity, PokemonDTO.class);
                enriquecerConTipos(dto);
                dtoList.add(dto);
            });
        }

        auditoriaService.registrar("CONSULTAR_POR_APODO", "Pokemon: " + apodo);
        return dtoList;
    }

    public List<PokemonDTO> findByPropietario(Long idUsuario) {
        LanzadorDeException.verificarId(idUsuario);
        Optional<List<Pokemon>> encontrados = pokemonRep.findByIdUsuarioPropietario(idUsuario);
        List<PokemonDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent()) {
            encontrados.get().forEach(entity -> {
                PokemonDTO dto = mapper.map(entity, PokemonDTO.class);
                enriquecerConTipos(dto);
                dtoList.add(dto);
            });
        }

        auditoriaService.registrar("CONSULTAR_POR_PROPIETARIO", "Usuario ID: " + idUsuario);
        return dtoList;
    }

    public PokemonDTO obtenerEspeciePorPokeApiId(Integer pokeApiId) {
        Optional<Pokemon> encontrado = pokemonRep.findFirstByPokeApiId(pokeApiId);
        auditoriaService.registrar("CONSULTAR_POR_POKEAPI_ID", "PokeApiId: " + pokeApiId);
        if (encontrado.isPresent()) {
            PokemonDTO dto = mapper.map(encontrado.get(), PokemonDTO.class);
            enriquecerConTipos(dto);
            return dto;
        }
        return null;
    }

    private void enriquecerConTipos(PokemonDTO dto) {
        if (dto.getPokeApiId() != null && dto.getPokeApiId() > 0) {
            List<String> listaNombresTipos = new ArrayList<>();

            co.edu.unbosque.pokemon.dto.InformacionPokemonDTO detalleApi =
                    PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(dto.getPokeApiId()));

            if (detalleApi != null && detalleApi.getListaTipos() != null) {
                for (co.edu.unbosque.pokemon.dto.TipoPokemonDTO t : detalleApi.getListaTipos()) {
                    if (t.getInformacionTipo() != null && t.getInformacionTipo().getNombreTipo() != null) {
                        listaNombresTipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
                    }
                }
            }
            dto.setTipos(listaNombresTipos);
        }
    }

    @Override
    public long count() {
        return pokemonRep.count();
    }

    @Override
    public boolean exist(Long id) {
        LanzadorDeException.verificarId(id);
        auditoriaService.registrar("CONSULTAR_EXISTENCIA", "Pokemon ID: " + id);
        return pokemonRep.existsById(id);
    }

    public void setPokemonRep(PokemonRepository repo) { this.pokemonRep = repo; }
    public void setMapper(ModelMapper mapper) { this.mapper = mapper; }
}