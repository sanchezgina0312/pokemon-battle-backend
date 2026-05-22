package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.CentroPokemon;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.CentroPokemonRepository;
import co.edu.unbosque.pokemon.repository.PokemonRepository;

@Service
public class CentroPokemonService {

    @Autowired
    private PokemonRepository pokeRep;

    @Autowired
    private CentroPokemonRepository centroRep;

    @Autowired
    private AuditoriaService auditoriaService;

    public int curar(long idPokemon) {
        Optional<Pokemon> pOpt = pokeRep.findById(idPokemon);
        if (pOpt.isPresent()) {
            Pokemon p = pOpt.get();
            p.setSaludActual(p.getSaludMaxima());
            pokeRep.save(p);
            centroRep.save(new CentroPokemon(p.getIdUsuarioPropietario(), idPokemon, LocalDateTime.now()));
            auditoriaService.registrar("CURAR", "Pokemon ID: " + idPokemon);
            return 0;
        }
        return 1;
    }
}