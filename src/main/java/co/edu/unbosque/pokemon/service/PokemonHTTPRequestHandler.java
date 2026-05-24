package co.edu.unbosque.pokemon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import co.edu.unbosque.pokemon.dto.DescripcionDTO;
import co.edu.unbosque.pokemon.dto.EspeciePokemonDTO;
import co.edu.unbosque.pokemon.dto.EstadisticaPokemonDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.ItemDetalleDTO;
import co.edu.unbosque.pokemon.dto.ReferenciaPokemonDTO;
import co.edu.unbosque.pokemon.dto.RespuestaDTO;
import co.edu.unbosque.pokemon.dto.TipoPokemonDTO;
import co.edu.unbosque.pokemon.dto.TraduccionDTO;

public class PokemonHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	private static ArrayList<InformacionPokemonDTO> pokedexDatos = new ArrayList<>();

	public static void cargarPokedex() {
	    System.out.println("--- Iniciando carga de 151 datos de la PokeAPI ---");
	    pokedexDatos.clear(); 
	    RespuestaDTO respuesta = obtenerLos151();
	    
	    if (respuesta != null && respuesta.getListaResultados() != null) {
	        int contador = 0;
	        for (ReferenciaPokemonDTO ref : respuesta.getListaResultados()) {
	            InformacionPokemonDTO detalle = obtenerDetallePokemon(ref.getNombre());
	            
	            if (detalle != null) {
	                pokedexDatos.add(detalle);
	                contador++;
	            } else {
	                System.out.println("DEBUG: Error al cargar detalles de: " + ref.getNombre());
	            }
	           
	            if (contador % 20 == 0) {
	                System.out.println("DEBUG: Llevamos cargados " + contador + " Pokémon...");
	            }
	        }
	    } else {
	        System.out.println("ERROR CRÍTICO: No se pudo conectar con la API para obtener la lista inicial.");
	    }
	    
	    System.out.println("*** Carga Completa: " + pokedexDatos.size() + " Pokémon almacenados exitosamente ***");
	}
	public static RespuestaDTO obtenerLos151() {
		String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url)).setHeader("User-Agent", "Java Bot")
				.build();
		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(respuesta.body(), RespuestaDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public static InformacionPokemonDTO obtenerDetallePokemon(String nombreOId) {
		String url = "https://pokeapi.co/api/v2/pokemon/" + nombreOId.toLowerCase();
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url)).setHeader("User-Agent", "Java Bot")
				.build();
		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(respuesta.body(), InformacionPokemonDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public static EspeciePokemonDTO obtenerEspeciePokemon(int id) {
		String url = "https://pokeapi.co/api/v2/pokemon-species/" + id;
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url)).setHeader("User-Agent", "Java Bot")
				.build();
		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(respuesta.body(), EspeciePokemonDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public static String traducirTexto(String texto, String idiomaDestino) {
		try {
			String textoUrl = URLEncoder.encode(texto, StandardCharsets.UTF_8);

			String url = "https://api.popcat.xyz/translate?to=" + idiomaDestino + "&text=" + textoUrl;

			HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
					.setHeader("User-Agent", "Java Bot").build();

			HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());

			TraduccionDTO resultado = new Gson().fromJson(respuesta.body(), TraduccionDTO.class);
			return resultado.getTextoTraducido();

		} catch (Exception e) {
			return texto;
		}
	}

	public static String extraerTextoPorIdioma(ArrayList<DescripcionDTO> lista, String idiomaBuscado) {
		if (lista == null || lista.isEmpty())
			return "Sin descripción.";
		for (DescripcionDTO desc : lista) {
			if (desc.getIdioma().getNombreIdioma().equals(idiomaBuscado)) {
				return desc.getTextoDescripcion().replace("\n", " ").replace("\f", " ");
			}
		}
		return lista.get(0).getTextoDescripcion();
	}

	public static String obtenerUrlIconoTipo(TipoPokemonDTO tipo) {
		if (tipo == null || tipo.getInformacionTipo() == null)
			return "";
		String nombreTipo = tipo.getInformacionTipo().getNombreTipo().toLowerCase();
		return "https://raw.githubusercontent.com/duiker101/pokemon-type-svg-icons/master/icons/" + nombreTipo + ".svg";
	}

	public static ArrayList<InformacionPokemonDTO> getPokedexDatos() {
		return pokedexDatos;
	}

	public static void setPokedexDatos(ArrayList<InformacionPokemonDTO> pokedexDatos) {
		PokemonHTTPRequestHandler.pokedexDatos = pokedexDatos;
	}
		public static List<String> extraerCuatroPrimerosAtaques(List<co.edu.unbosque.pokemon.dto.AtaquePokemonDTO> listaAtaques) {
			List<String> nombres = new ArrayList<>();
			if (listaAtaques == null) {
				for (int i = 0; i < 4; i++) nombres.add("---");
				return nombres;
			}
			
			for (int i = 0; i < 4; i++) {
				if (i < listaAtaques.size()) {
					
					nombres.add(listaAtaques.get(i).getInformacionAtaque().getNombre().toUpperCase());
				} else {
					nombres.add("---");
				}
			}
			return nombres;
		}
		public static int extraerStat(List<EstadisticaPokemonDTO> stats, String nombreStat) {
		    if (stats == null) return 0;
		    for (EstadisticaPokemonDTO s : stats) {
		        if (s.getStat() != null && s.getStat().getName().equals(nombreStat)) {
		            return s.getValorDeLaEstadistica();
		        }
		    }
		    return 0;
		}
		public static List<ItemDetalleDTO> obtenerTodosLosItems() {
		    String url = "https://pokeapi.co/api/v2/item?limit=50"; 
		    HttpRequest solicitud = HttpRequest.newBuilder()
		            .GET()
		            .uri(URI.create(url))
		            .build();
		    
		    try {
		        HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
		        System.out.println("DEBUG: JSON recibido de API: " + respuesta.body());
		        
		        JsonObject jsonObject = JsonParser.parseString(respuesta.body()).getAsJsonObject();
		        JsonArray results = jsonObject.getAsJsonArray("results");
		        
		        List<ItemDetalleDTO> lista = new ArrayList<>();
		        
		        for (int i = 0; i < results.size(); i++) {
		            JsonObject itemJson = results.get(i).getAsJsonObject();
		            
		            ItemDetalleDTO item = new ItemDetalleDTO();
		            item.setNombreIngles(itemJson.get("name").getAsString());
		            
		            String urlItem = itemJson.get("url").getAsString();
		            String[] partes = urlItem.split("/");
		            int id = Integer.parseInt(partes[partes.length - 1]); 
		            item.setId(id);
		            
		            lista.add(item);
		        }
		        return lista;
		    } catch (Exception e) {
		        System.out.println("Error al obtener catálogo de items: " + e.getMessage());
		        return new ArrayList<>();
		    }
	    }
		public static List<ItemDetalleDTO> obtenerTodosLosAtaques() {
		    // La URL correcta para ataques en PokeAPI es 'move'
		    String url = "https://pokeapi.co/api/v2/move?limit=100"; 
		    HttpRequest solicitud = HttpRequest.newBuilder()
		            .GET()
		            .uri(URI.create(url))
		            .build();
		    
		    try {
		        HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
		        System.out.println("DEBUG: JSON recibido de API (Ataques): " + respuesta.body());
		        
		        JsonObject jsonObject = JsonParser.parseString(respuesta.body()).getAsJsonObject();
		        JsonArray results = jsonObject.getAsJsonArray("results");
		        
		        List<ItemDetalleDTO> lista = new ArrayList<>();
		        
		        for (int i = 0; i < results.size(); i++) {
		            JsonObject ataqueJson = results.get(i).getAsJsonObject();
		            
		            ItemDetalleDTO ataque = new ItemDetalleDTO();
		            ataque.setNombreIngles(ataqueJson.get("name").getAsString());
		            
		            // Extraemos el ID desde la URL igual que con los items
		            String urlAtaque = ataqueJson.get("url").getAsString();
		            String[] partes = urlAtaque.split("/");
		            int id = Integer.parseInt(partes[partes.length - 1]); 
		            ataque.setId(id);
		            // También guardamos la URL por si la necesitas en el mapeo del Service
		            ataque.setUrl(urlAtaque); 
		            
		            lista.add(ataque);
		        }
		        return lista;
		    } catch (Exception e) {
		        System.out.println("Error al obtener catálogo de ataques: " + e.getMessage());
		        return new ArrayList<>();
		    }
		}
	public static void main(String[] args) {
		cargarPokedex();

		ArrayList<InformacionPokemonDTO> info = getPokedexDatos();
		
		System.out.println("--- PRUEBA DE TODO EL PROYECTO POKEMON ---\n");

		System.out.println("--- 1. Revisión almacenamiento de pokemon ---");
		for (int i = 0; i < 10; i++) {
			InformacionPokemonDTO p = info.get(i);
			System.out.println("------------------------------------------");
			System.out.println("Pokemon Numero " + p.getId() + ": " + p.getNombre().toUpperCase());

			System.out.print("Tipos del pokemon: ");
			for (TipoPokemonDTO t : p.getListaTipos()) {
				String nombreTipo = t.getInformacionTipo().getNombreTipo();
				System.out.print("[" + nombreTipo.toUpperCase() + "] ");
			}
			System.out.println();

			for (TipoPokemonDTO t : p.getListaTipos()) {
				System.out
						.println(" -> Link del Icono " + t.getInformacionTipo().getNombreTipo() + ": " + obtenerUrlIconoTipo(t));
			}

			if (p.getListaAtaques() != null && !p.getListaAtaques().isEmpty()) {
				System.out.println(
						"Ataque que mas usa: " + p.getListaAtaques().get(0).getInformacionAtaque().getNombre());
			}
		}

		System.out.println("\n--- 2. Probando si filtra por idioma ---");
		EspeciePokemonDTO pikachuEspecie = obtenerEspeciePokemon(25);
		if (pikachuEspecie != null) {
			ArrayList<DescripcionDTO> descripciones = (ArrayList<DescripcionDTO>) pikachuEspecie
					.getListaDescripciones();
			String historiaES = extraerTextoPorIdioma(descripciones, "es");
			String historiaEN = extraerTextoPorIdioma(descripciones, "en");

			System.out.println("Pikachu dice en Español: " + historiaES);
			System.out.println("Pikachu says in English: " + historiaEN);
		}

		System.out.println("\n--- 3. Probando la API de traduccion (PopCat) ---");
		String mensajeInterfaz = "¡Bienvenida Tatiana al laboratorio del Profesor Oak! Por favor, elige a tu compañero.";

		System.out.println("Texto Original: " + mensajeInterfaz);

		String traducidoPopCat = traducirTexto(mensajeInterfaz, "en");
		System.out.println("Texto Traducido: " + traducidoPopCat);

		System.out.println("*** ¡Finalizado!*** ");
	}
}