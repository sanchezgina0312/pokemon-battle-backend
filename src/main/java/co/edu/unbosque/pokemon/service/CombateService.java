package co.edu.unbosque.pokemon.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.CombateDTO;
import co.edu.unbosque.pokemon.entity.Combate;
import co.edu.unbosque.pokemon.repository.CombateRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class CombateService {

	@Autowired
	private CombateRepository combateRep;
	@Autowired
	private ModelMapper mapper;

	private static final Map<String, Map<String, Double>> TABLA_EFECTIVIDAD = new HashMap<>();

	static {
		agregarEfectividad("normal", Map.of("roca", 0.5, "acero", 0.5, "fantasma", 0.0));
		agregarEfectividad("fuego", Map.of("fuego", 0.5, "agua", 0.5, "planta", 2.0, "hielo", 2.0, "bicho", 2.0, "roca",
				0.5, "dragon", 0.5, "acero", 2.0));
		agregarEfectividad("agua",
				Map.of("fuego", 2.0, "agua", 0.5, "planta", 0.5, "tierra", 2.0, "roca", 2.0, "dragon", 0.5));
		agregarEfectividad("planta", Map.of("fuego", 0.5, "agua", 2.0, "planta", 0.5, "veneno", 0.5, "tierra", 2.0,
				"volador", 0.5, "bicho", 0.5, "roca", 2.0, "acero", 0.5));
		agregarEfectividad("electrico",
				Map.of("agua", 2.0, "electrico", 0.5, "planta", 0.5, "tierra", 0.0, "volador", 2.0, "dragon", 0.5));
		agregarEfectividad("hielo", Map.of("fuego", 0.5, "agua", 0.5, "planta", 2.0, "hielo", 0.5, "tierra", 2.0,
				"volador", 2.0, "dragon", 2.0, "acero", 0.5));
		agregarEfectividad("lucha", Map.of("normal", 2.0, "hielo", 2.0, "veneno", 0.5, "volador", 0.5, "psiquico", 0.5,
				"bicho", 0.5, "roca", 2.0, "fantasma", 0.0, "acero", 2.0, "siniestro", 2.0));
		agregarEfectividad("veneno",
				Map.of("planta", 2.0, "veneno", 0.5, "tierra", 0.5, "roca", 0.5, "fantasma", 0.5, "acero", 0.0));
		agregarEfectividad("tierra", Map.of("fuego", 2.0, "electrico", 2.0, "planta", 0.5, "veneno", 2.0, "volador",
				0.0, "bicho", 0.5, "roca", 2.0, "acero", 2.0));
		agregarEfectividad("volador",
				Map.of("electrico", 0.5, "planta", 2.0, "lucha", 2.0, "bicho", 2.0, "roca", 0.5, "acero", 0.5));
		agregarEfectividad("psiquico",
				Map.of("lucha", 2.0, "veneno", 2.0, "psiquico", 0.5, "acero", 0.5, "siniestro", 0.0));
		agregarEfectividad("bicho", Map.of("fuego", 0.5, "planta", 2.0, "lucha", 0.5, "veneno", 0.5, "volador", 0.5,
				"psiquico", 2.0, "fantasma", 0.5, "acero", 0.5, "siniestro", 2.0));
		agregarEfectividad("roca", Map.of("fuego", 2.0, "hielo", 2.0, "lucha", 0.5, "tierra", 0.5, "volador", 2.0,
				"bicho", 2.0, "acero", 0.5));
		agregarEfectividad("fantasma", Map.of("normal", 0.0, "psiquico", 2.0, "fantasma", 2.0, "siniestro", 0.5));
		agregarEfectividad("dragon", Map.of("dragon", 2.0, "acero", 0.5));
		agregarEfectividad("acero",
				Map.of("fuego", 0.5, "agua", 0.5, "electrico", 0.5, "hielo", 2.0, "roca", 2.0, "acero", 0.5));
		agregarEfectividad("siniestro", Map.of("lucha", 0.5, "psiquico", 2.0, "fantasma", 2.0, "siniestro", 0.5));
	}

	private static void agregarEfectividad(String tipo, Map<String, Double> debilidades) {
		TABLA_EFECTIVIDAD.put(tipo, debilidades);
	}

	public int calcularDanio(int nivel, int atk, int def, String tipoAtk, String tipoDef) {

		double multiplicador = 1.0;

		if (tipoAtk.equalsIgnoreCase("FIRE") && tipoDef.equalsIgnoreCase("GRASS")) {
			multiplicador = 2.0;
		}

		else if (tipoAtk.equalsIgnoreCase("WATER") && tipoDef.equalsIgnoreCase("FIRE")) {
			multiplicador = 2.0;
		}

		else if (tipoAtk.equalsIgnoreCase("GRASS") && tipoDef.equalsIgnoreCase("WATER")) {
			multiplicador = 2.0;
		}

		double base = (((2.0 * nivel) / 5.0) + 2.0);

		double daño = ((base * 40 * ((double) atk / def)) / 50.0) + 2.0;

		daño *= multiplicador;

		return Math.max(1, (int) daño);
	}

	public CombateDTO crearRegistro(CombateDTO data) {
		Combate entidad = mapper.map(data, Combate.class);
		return mapper.map(combateRep.save(entidad), CombateDTO.class);
	}

	public List<CombateDTO> historialPeleas(Long idUser) {
		Optional<List<Combate>> encontrados = combateRep.findByIdUsuarioJugador(idUser);
		List<CombateDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent()) {
			encontrados.get().forEach(ent -> dtoList.add(mapper.map(ent, CombateDTO.class)));
		}
		return dtoList;
	}

	public Map<String, Object> calcularRecompensas(int nivelRival) {
		int expGanada = (nivelRival * 10) + new Random().nextInt(20);
		int dineroGanado = nivelRival * 5;

		Map<String, Object> recompensas = new HashMap<>();
		recompensas.put("exp", expGanada);
		recompensas.put("dinero", dineroGanado);
		return recompensas;
	}

	public int calcularNuevoNivel(int nivelActual, int expActual, int expGanada) {
		int nuevaExp = expActual + expGanada;
		return Math.min(100, (nuevaExp / 100) + 1);
	}
}