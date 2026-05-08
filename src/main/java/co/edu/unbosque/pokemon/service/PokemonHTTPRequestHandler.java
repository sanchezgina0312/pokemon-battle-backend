package co.edu.unbosque.pokemon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import com.google.gson.Gson;

import co.edu.unbosque.pokemon.dto.DescripcionDTO;
import co.edu.unbosque.pokemon.dto.EspeciePokemonDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.ItemDetalleDTO;
import co.edu.unbosque.pokemon.dto.RespuestaDTO;

public class PokemonHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	// Método para traer los 151 nombres de los pokemon
	// Se usa cuando el usuario va a armar su caja
	public static RespuestaDTO obtenerLos151() {
		String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java HttpClient Bot").build();

		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();
			// Mapeamos el JSON limpio a el DTO
			return gson.fromJson(respuesta.body(), RespuestaDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para traer los DATOS DE COMBATE de un solo Pokémon
	// Se usa SOLO cuando el usuario selecciona a sus 6 peleadores
	public static InformacionPokemonDTO obtenerDetallePokemon(String nombreOId) {
		String url = "https://pokeapi.co/api/v2/pokemon/" + nombreOId.toLowerCase();
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java HttpClient Bot").build();

		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();

			// Guarda lo que se puso en PokemonDetalleDTO (stats, sprites, moves).
			return gson.fromJson(respuesta.body(), InformacionPokemonDTO.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Trae la información de la especie (como la descripción de la Pokédex). Se
	 * necesita porque el endpoint de /pokemon/ no trae textos biográficos de cada
	 * pokemon.
	 */
	public static EspeciePokemonDTO obtenerEspeciePokemon(int id) {
		String url = "https://pokeapi.co/api/v2/pokemon-species/" + id;
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java HttpClient Bot").build();
		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(respuesta.body(), EspeciePokemonDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Trae el detalle de un objeto (Poción, Revivir, etc.) incluyendo su precio.
	 */
	public static ItemDetalleDTO obtenerDetalleItem(String nombreItem) {
		// Reemplazamos espacios por guiones para la URL (ej: "full restore" ->
		// "full-restore")
		String url = "https://pokeapi.co/api/v2/item/" + nombreItem.toLowerCase().replace(" ", "-");
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java HttpClient Bot").build();
		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(respuesta.body(), ItemDetalleDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lógica de filtrado de idioma con Fallback. Si no encuentra el idioma
	 * solicitado (ej: "es"), devuelve el primero disponible.
	 */
	public static String extraerTextoPorIdioma(List<DescripcionDTO> lista, String idiomaBuscado) {
		if (lista == null || lista.isEmpty()) {
			return "Sin descripción disponible.";
		}

		// 1. Intentamos buscar el idioma que quiere el usuario
		for (DescripcionDTO desc : lista) {
			// Agregamos comprobación de que el texto no sea null
			if (desc.getIdioma().getNombreIdioma().equals(idiomaBuscado) && desc.getTextoDescripcion() != null) {
				return desc.getTextoDescripcion().replace("\n", " ").replace("\f", " ");
			}
		}

		// 2. Lógica de Fallback: Si no estaba en su idioma,
		// devolvemos el primero que haya
		if (lista.get(0).getTextoDescripcion() != null) {
			return lista.get(0).getTextoDescripcion().replace("\n", " ").replace("\f", " ");
		}

		return "Descripción no encontrada.";
	}

	public static void main(String[] args) {
		System.out.println("Prueba de trar toda la pokedex rojofuego");

		// 1. Obtener la lista de los 151
		RespuestaDTO catalogo = obtenerLos151();

		if (catalogo == null || catalogo.getListaResultados() == null) {
			System.err.println("Error: No se pudo conectar con la API.");
			return;
		}

		// 2. Recorrer la lista completa
		for (int i = 0; i < catalogo.getListaResultados().size(); i++) {
			String nombre = catalogo.getListaResultados().get(i).getNombre();

			// Traemos el detalle para obtener el ID y los Stats
			InformacionPokemonDTO info = obtenerDetallePokemon(nombre);

			if (info != null) {
				// Traemos la especie para la descripción completa
				EspeciePokemonDTO especie = obtenerEspeciePokemon(info.getId());

				System.out.println("--------------------------------------------------");
				System.out.println("POKÉMON #" + info.getId() + ": " + nombre.toUpperCase());

				// Imprimimos Stats de combate (importante para tu proyecto)
				if (info.getListaEstadisticas() != null) {
					System.out.print("STATS: ");
					info.getListaEstadisticas()
							.forEach(s -> System.out.print("[" + s.getValorDeLaEstadistica() + "] "));
					System.out.println();
				}

				// Imprimimos la descripción completa sin recortes
				if (especie != null) {
					String descCompleta = extraerTextoPorIdioma(especie.getListaDescripciones(), "es");
					System.out.println("DESCRIPCIÓN: " + descCompleta);
				}
			} else {
				System.err.println("Error al cargar datos de: " + nombre);
			}

			// Nota: Si la API da 429 (Too Many Requests),
			// se puede agregar un pequeño delay aquí: Thread.sleep(100);
		}

		System.out.println("--------------------------------------------------");
		System.out.println("***FINALIZADO: 151 POKÉMON PROCESADOS***");
	}
}