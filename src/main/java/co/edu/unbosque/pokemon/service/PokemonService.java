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
        Optional<Pokemon> configBD = pokemonRep.findFirstByPokeApiId(pokeApiId);
        InformacionPokemonDTO info = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokeApiId));

        if (info != null) {
            PokemonDTO dto = new PokemonDTO();
            dto.setPokeApiId(pokeApiId);
            if (configBD.isPresent()) {
                dto.setApodo(configBD.get().getApodo());
                dto.setNivel(configBD.get().getNivel());
                dto.setEstado(configBD.get().getEstado());
            } else {
                dto.setApodo(info.getNombre().toUpperCase());
                dto.setNivel(1);
                dto.setEstado("OK");
            }

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


    /**
     * Guarda o actualiza la configuración personalizada (apodo, nivel, estado)
     * de una especie en la BD. Preservado de Nata.
     */
    public void actualizarConfiguracionEspecie(PokemonDTO data) {
        Pokemon p = pokemonRep.findFirstByPokeApiId(data.getPokeApiId())
                              .orElse(new Pokemon());
        System.out.println("Antes de guardar: " + p.getApodo());
        p.setPokeApiId(data.getPokeApiId());
        p.setApodo(data.getApodo());
        p.setNivel(data.getNivel());
        p.setEstado(data.getEstado());
        pokemonRep.save(p);
        System.out.println("Después de guardar: " + p.getApodo() + p.getNivel() + p.getEstado());
    }

    /**
     * Devuelve solo los campos de configuración (apodo, nivel, estado) de una
     * especie guardada en BD, sin enriquecer con la PokeAPI. Preservado de Nata.
     */
    public PokemonDTO buscarConfiguracionEnBD(Integer pokeApiId) {
        Optional<Pokemon> opt = pokemonRep.findFirstByPokeApiId(pokeApiId);
        if (opt.isPresent()) {
            Pokemon entity = opt.get();
            PokemonDTO dto = new PokemonDTO();
            dto.setPokeApiId(entity.getPokeApiId());
            dto.setApodo(entity.getApodo());
            dto.setNivel(entity.getNivel());
            dto.setEstado(entity.getEstado());
            return dto;
        }
        return null;
    }


    /**
     * Incrementa la experiencia de un Pokémon específico en la base de datos.
     * Al llegar a 100 xp sube de nivel y mejora ataque y defensa. Preservado de main_copy.
     * @param id  Identificador del Pokémon.
     * @param exp Cantidad de puntos de experiencia a sumar.
     */
    public void sumarExperiencia(Long id, int exp) {
        Optional<Pokemon> encontrado = pokemonRep.findById(id);
        if (encontrado.isPresent()) {
            Pokemon p = encontrado.get();
            int nuevaExp = p.getExperienciaAcumulada() + exp;

            if (nuevaExp >= 100) {
                p.setNivel(p.getNivel() + 1);
                p.setExperienciaAcumulada(nuevaExp - 100);
                p.setAtaque(p.getAtaque() + 2);
                p.setDefensa(p.getDefensa() + 2);
            } else {
                p.setExperienciaAcumulada(nuevaExp);
            }
            pokemonRep.save(p);
        }
    }


    private void enriquecerConTipos(PokemonDTO dto) {
        if (dto.getPokeApiId() != null && dto.getPokeApiId() > 0) {
            List<String> listaNombresTipos = new ArrayList<>();
            InformacionPokemonDTO detalleApi = PokemonHTTPRequestHandler
                    .obtenerDetallePokemon(String.valueOf(dto.getPokeApiId()));

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

    public void setPokemonRep(PokemonRepository repo) { this.pokemonRep = repo; }
    public void setMapper(ModelMapper mapper) { this.mapper = mapper; }
}