package co.edu.unbosque.pokemon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import com.google.gson.Gson;

import co.edu.unbosque.pokemon.dto.DescripcionDTO;
import co.edu.unbosque.pokemon.dto.DetalleMovimientoDTO;
import co.edu.unbosque.pokemon.dto.EspeciePokemonDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.ItemDetalleDTO;
import co.edu.unbosque.pokemon.dto.RespuestaDTO;

public class PokemonHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public static RespuestaDTO obtenerLos151() {
		String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java HttpClient Bot").build();

		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();
			return gson.fromJson(respuesta.body(), RespuestaDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static InformacionPokemonDTO obtenerDetallePokemon(String nombreOId) {
		String url = "https://pokeapi.co/api/v2/pokemon/" + nombreOId.toLowerCase();
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java HttpClient Bot").build();

		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();
			return gson.fromJson(respuesta.body(), InformacionPokemonDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String obtenerGritoRojoFuego(String nombreOId) {
		InformacionPokemonDTO info = obtenerDetallePokemon(nombreOId);
		if (info != null && info.getSonidos() != null) {
			return info.getSonidos().getGritoPokemon();
		}
		return null;
	}

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

	public static ItemDetalleDTO obtenerDetalleItem(String nombreItem) {
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

	public static String extraerTextoPorIdioma(List<DescripcionDTO> lista, String idiomaBuscado) {
		if (lista == null || lista.isEmpty()) {
			return "Sin descripción disponible.";
		}
		for (DescripcionDTO desc : lista) {
			if (desc.getIdioma().getNombreIdioma().equals(idiomaBuscado) && desc.getTextoDescripcion() != null) {
				return desc.getTextoDescripcion().replace("\n", " ").replace("\f", " ");
			}
		}
		return "Descripción no encontrada.";
	}
	
	public static DetalleMovimientoDTO obtenerDetalleMovimiento(String urlMovimiento) {
	    HttpRequest solicitud = HttpRequest.newBuilder()
	            .GET()
	            .uri(URI.create(urlMovimiento))
	            .setHeader("User-Agent", "Java HttpClient Bot")
	            .build();
	    try {
	        HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
	        return new Gson().fromJson(respuesta.body(), DetalleMovimientoDTO.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public static void main(String[] args) {
		System.out.println("Procesando Pokedex (Datos de combate y descripción)...");

		RespuestaDTO catalogo = obtenerLos151();
		if (catalogo == null || catalogo.getListaResultados() == null) return;

		for (int i = 0; i < catalogo.getListaResultados().size(); i++) {
			String nombre = catalogo.getListaResultados().get(i).getNombre();
			InformacionPokemonDTO info = obtenerDetallePokemon(nombre);

			if (info != null) {
				EspeciePokemonDTO especie = obtenerEspeciePokemon(info.getId());

				System.out.println("--------------------------------------------------");
				System.out.println("POKÉMON #" + info.getId() + ": " + nombre.toUpperCase());

				if (info.getListaEstadisticas() != null) {
					System.out.print("STATS: ");
					info.getListaEstadisticas()
							.forEach(s -> System.out.print("[" + s.getValorDeLaEstadistica() + "] "));
					System.out.println();
				}

				if (especie != null) {
					String desc = extraerTextoPorIdioma(especie.getListaDescripciones(), "es");
					System.out.println("DESCRIPCIÓN: " + desc);
				}
			}
		}
		System.out.println("--------------------------------------------------");
		System.out.println("*** PROCESO FINALIZADO ***");
	}
}