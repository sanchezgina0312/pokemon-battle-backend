package co.edu.unbosque.pokemon.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.Gson;

import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.RespuestaDTO;

public class PokemonRequestHandler {
	
	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    // 1. Método para traer los 151 nombres (Se usa cuando el usuario va a armar su caja)
    public static RespuestaDTO obtenerLos151() {
        String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
        HttpRequest solicitud = HttpRequest.newBuilder()
                .GET().uri(URI.create(url))
                .setHeader("User-Agent", "Java HttpClient Bot")
                .build();

        try {
            HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            // Mapeamos el JSON limpio a nuestro DTO
            return gson.fromJson(respuesta.body(), RespuestaDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. Método para traer los DATOS DE COMBATE de un solo Pokémon 
    // (Se usa SOLO cuando el usuario selecciona a sus 6 peleadores)
    public static InformacionPokemonDTO obtenerDetallePokemon(String nombreOId) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + nombreOId;
        HttpRequest solicitud = HttpRequest.newBuilder()
                .GET().uri(URI.create(url))
                .setHeader("User-Agent", "Java HttpClient Bot")
                .build();

        try {
            HttpResponse<String> respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            
            // Gson automáticamente ignora la basura del JSON (gritos, habilidades, etc)
            // y solo guarda lo que pusimos en PokemonDetalleDTO (stats, sprites, moves).
            return gson.fromJson(respuesta.body(), InformacionPokemonDTO.class);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // PRUEBA DE ESCRITORIO
        System.out.println("Cargando lista maestra...");
        RespuestaDTO catalogo = obtenerLos151();
        System.out.println("Total de Pokemones listos: " + catalogo.getResultados().size());

        System.out.println("\nExtrayendo estadísticas de combate para Charizard...");
        InformacionPokemonDTO charizard = obtenerDetallePokemon("charizard");
        
        // Aquí puedes ver que solo tienes lo que pidió el profesor
        System.out.println("Peso: " + charizard.getPeso());
        // El stat 0 es HP (Vida)
        System.out.println("Vida Base (HP): " + charizard.getEstadisticas().get(0).getValorBase());
    }

}
