package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.unbosque.pokemon.dto.AtaquePokemonDTO;
import co.edu.unbosque.pokemon.dto.DescripcionDTO;
import co.edu.unbosque.pokemon.dto.IdiomaDTO;
import co.edu.unbosque.pokemon.dto.InformacionAtaqueDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;

/**
 * Clase de pruebas unitarias para {@link PokemonHTTPRequestHandler}.
 * 
 * Valida el correcto funcionamiento de los métodos utilitarios relacionados
 * con la extracción de información de Pokémon, como descripciones,
 * ataques, estadísticas y manejo de datos de la Pokédex.
 */
class PokemonHTTPRequestHandlerTest {

	/**
	 * Verifica que se extraiga correctamente el texto de la descripción según el idioma.
	 */
	@Test
	void testExtraerTextoPorIdioma() {

		DescripcionDTO desc = new DescripcionDTO();

		IdiomaDTO idioma = new IdiomaDTO();
		idioma.setNombreIdioma("es");

		desc.setIdioma(idioma);
		desc.setTextoDescripcion("Pokemon eléctrico");

		ArrayList<DescripcionDTO> lista = new ArrayList<>();
		lista.add(desc);

		String resultado =
				PokemonHTTPRequestHandler.extraerTextoPorIdioma(lista, "es");

		assertEquals("Pokemon eléctrico", resultado);
	}

	/**
	 * Verifica que cuando la lista de descripciones está vacía se retorne el mensaje por defecto.
	 */
	@Test
	void testExtraerTextoPorIdiomaEmpty() {

		String resultado =
				PokemonHTTPRequestHandler.extraerTextoPorIdioma(new ArrayList<>(), "es");

		assertEquals("Sin descripción.", resultado);
	}

	/**
	 * Verifica que se retorne correctamente el icono del tipo cuando es nulo.
	 */
	@Test
	void testObtenerUrlIconoTipo() {

		String resultado =
				PokemonHTTPRequestHandler.obtenerUrlIconoTipo(null);

		assertEquals("", resultado);
	}

	/**
	 * Verifica la extracción de los cuatro primeros ataques del Pokémon.
	 * Si hay menos de cuatro ataques, los espacios restantes deben llenarse con "---".
	 */
	@Test
	void testExtraerCuatroPrimerosAtaques() {

		AtaquePokemonDTO atk = mock(AtaquePokemonDTO.class);
		InformacionAtaqueDTO info = mock(InformacionAtaqueDTO.class);

		when(atk.getInformacionAtaque()).thenReturn(info);
		when(info.getNombre()).thenReturn("impactrueno");

		List<AtaquePokemonDTO> lista = List.of(atk);

		List<String> resultado =
				PokemonHTTPRequestHandler.extraerCuatroPrimerosAtaques(lista);

		assertEquals(4, resultado.size());
		assertEquals("IMPACTRUENO", resultado.get(0));
		assertEquals("---", resultado.get(1));
		assertEquals("---", resultado.get(2));
		assertEquals("---", resultado.get(3));
	}

	/**
	 * Verifica el comportamiento cuando la lista de ataques es nula.
	 * Debe retornar una lista de cuatro elementos "---".
	 */
	@Test
	void testExtraerCuatroPrimerosAtaquesNull() {

		List<String> resultado =
				PokemonHTTPRequestHandler.extraerCuatroPrimerosAtaques(null);

		assertEquals(4, resultado.size());

		for (String s : resultado) {
			assertEquals("---", s);
		}
	}

	/**
	 * Verifica que cuando una estadística no existe, se retorne 0.
	 */
	@Test
	void testExtraerStatNotFound() {

		int resultado =
				PokemonHTTPRequestHandler.extraerStat(new ArrayList<>(), "speed");

		assertEquals(0, resultado);
	}

	/**
	 * Verifica el correcto funcionamiento del getter y setter de los datos de la Pokédex.
	 */
	@Test
	void testGetSetPokedexDatos() {

		ArrayList<InformacionPokemonDTO> lista =
				new ArrayList<>();

		InformacionPokemonDTO pokemon =
				new InformacionPokemonDTO();

		lista.add(pokemon);

		PokemonHTTPRequestHandler.setPokedexDatos(lista);

		assertEquals(1,
				PokemonHTTPRequestHandler.getPokedexDatos().size());
	}
}