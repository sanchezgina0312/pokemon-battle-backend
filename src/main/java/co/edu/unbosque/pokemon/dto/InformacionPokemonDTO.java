package co.edu.unbosque.pokemon.dto;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) principal que condensa toda la información detallada de un Pokémon.
 * <p>
 * Esta clase actúa como el modelo espejo raíz para la deserialización del flujo JSON obtenido desde la PokeAPI. 
 * Consolida las características físicas, colecciones de estadísticas, listados de movimientos, tipos elementales, 
 * recursos multimedia de imágenes (sprites) y archivos de audio (gritos).
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class InformacionPokemonDTO {

	/**
	 * El identificador numérico único de la especie del Pokémon en la base de datos externa.
	 * <p>
	 * La anotación {@code @SerializedName("id")} vincula este campo con la propiedad 
	 * "id" del JSON externo.
	 * </p>
	 */
	@SerializedName("id")
	private int id;

	/**
	 * El nombre oficial de la especie del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("name")} vincula este campo con la propiedad 
	 * "name" del JSON externo.
	 * </p>
	 */
	@SerializedName("name")
	private String nombre;

	/**
	 * El peso base de la criatura, expresado en hectogramos según el estándar de la PokeAPI.
	 * <p>
	 * La anotación {@code @SerializedName("weight")} vincula este campo con la propiedad 
	 * "weight" del JSON externo.
	 * </p>
	 */
	@SerializedName("weight")
	private int peso;

	/**
	 * Listado dinámico que contiene los valores de las estadísticas base del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("stats")} mapea este atributo con el arreglo 
	 * "stats" del JSON, estructurando los datos en objetos {@link EstadisticaPokemonDTO}.
	 * </p>
	 */
	@SerializedName("stats")
	private ArrayList<EstadisticaPokemonDTO> listaEstadisticas;

	/**
	 * Listado dinámico que almacena el repertorio de ataques y movimientos que el Pokémon puede aprender.
	 * <p>
	 * La anotación {@code @SerializedName("moves")} mapea este atributo con el arreglo 
	 * "moves" del JSON, estructurando los datos en objetos {@link AtaquePokemonDTO}.
	 * </p>
	 */
	@SerializedName("moves")
	private ArrayList<AtaquePokemonDTO> listaAtaques;

	/**
	 * Contenedor de URLs correspondientes a las representaciones gráficas y sprites del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("sprites")} vincula este atributo con el objeto 
	 * anidado "sprites" del JSON externo.
	 * </p>
	 */
	@SerializedName("sprites")
	private SpriteDTO imagenes;

	/**
	 * Listado dinámico que contiene la clasificación de tipos elementales asignados a la criatura.
	 * <p>
	 * La anotación {@code @SerializedName("types")} mapea este atributo con el arreglo 
	 * "types" del JSON, estructurando los datos en objetos {@link TipoPokemonDTO}.
	 * </p>
	 */
	@SerializedName("types")
	private ArrayList<TipoPokemonDTO> listaTipos;

	/**
	 * Contenedor de las rutas o URLs de los archivos de audio que reproducen los gritos del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("cries")} vincula este atributo con el objeto 
	 * anidado "cries" del JSON externo.
	 * </p>
	 */
	@SerializedName("cries")
	private GritoPokemonDTO sonidos;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code InformacionPokemonDTO}.
	 */
	public InformacionPokemonDTO() {

	}

	/**
	 * Constructor parametrizado con todos los atributos de la clase.
	 * Permite instanciar un objeto {@code InformacionPokemonDTO} completamente inicializado.
	 *
	 * @param id                El ID numérico único del Pokémon.
	 * @param nombre            El nombre oficial de la especie.
	 * @param peso              El peso base de la criatura.
	 * @param listaEstadisticas Lista de objetos {@link EstadisticaPokemonDTO} con sus estadísticas.
	 * @param listaAtaques      Lista de objetos {@link AtaquePokemonDTO} con sus movimientos disponibles.
	 * @param imagenes          Objeto {@link SpriteDTO} con las URLs de las ilustraciones.
	 * @param listaTipos        Lista de objetos {@link TipoPokemonDTO} con sus afinidades elementales.
	 * @param sonidos           Objeto {@link GritoPokemonDTO} con los archivos de sonido correspondientes.
	 */
	public InformacionPokemonDTO(int id, String nombre, int peso, ArrayList<EstadisticaPokemonDTO> listaEstadisticas,
			ArrayList<AtaquePokemonDTO> listaAtaques, SpriteDTO imagenes, ArrayList<TipoPokemonDTO> listaTipos,
			GritoPokemonDTO sonidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.peso = peso;
		this.listaEstadisticas = listaEstadisticas;
		this.listaAtaques = listaAtaques;
		this.imagenes = imagenes;
		this.listaTipos = listaTipos;
		this.sonidos = sonidos;
	}

	/**
	 * Obtiene el identificador numérico de la especie del Pokémon.
	 *
	 * @return Un entero con el ID de la PokeAPI.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador numérico de la especie del Pokémon.
	 *
	 * @param id El nuevo ID de PokeAPI a asignar.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre oficial del Pokémon.
	 *
	 * @return Una cadena de caracteres con el nombre de la especie.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre oficial del Pokémon.
	 *
	 * @param nombre El nuevo nombre de la especie a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el peso base de la criatura (en hectogramos).
	 *
	 * @return Un entero con el valor del peso.
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * Establece el peso base de la criatura (en hectogramos).
	 *
	 * @param peso El nuevo valor de peso a asignar.
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}

	/**
	 * Obtiene la lista dinámica con las estadísticas base del Pokémon.
	 *
	 * @return Un {@link ArrayList} conteniendo instancias de {@link EstadisticaPokemonDTO}.
	 */
	public ArrayList<EstadisticaPokemonDTO> getListaEstadisticas() {
		return listaEstadisticas;
	}

	/**
	 * Establece la lista dinámica con las estadísticas base del Pokémon.
	 *
	 * @param listaEstadisticas El nuevo {@link ArrayList} de {@link EstadisticaPokemonDTO} a asignar.
	 */
	public void setListaEstadisticas(ArrayList<EstadisticaPokemonDTO> listaEstadisticas) {
		this.listaEstadisticas = listaEstadisticas;
	}

	/**
	 * Obtiene la lista dinámica de los ataques que puede aprender el Pokémon.
	 *
	 * @return Un {@link ArrayList} conteniendo instancias de {@link AtaquePokemonDTO}.
	 */
	public ArrayList<AtaquePokemonDTO> getListaAtaques() {
		return listaAtaques;
	}

	/**
	 * Establece la lista dinámica de los ataques que puede aprender el Pokémon.
	 *
	 * @param listaAtaques El nuevo {@link ArrayList} de {@link AtaquePokemonDTO} a asignar.
	 */
	public void setListaAtaques(ArrayList<AtaquePokemonDTO> listaAtaques) {
		this.listaAtaques = listaAtaques;
	}

	/**
	 * Obtiene el objeto contenedor de las URLs de los sprites e imágenes.
	 *
	 * @return Una instancia de {@link SpriteDTO} con los recursos gráficos.
	 */
	public SpriteDTO getImagenes() {
		return imagenes;
	}

	/**
	 * Establece el objeto contenedor de las URLs de los sprites e imágenes.
	 *
	 * @param imagenes El nuevo objeto {@link SpriteDTO} a asignar.
	 */
	public void setImagenes(SpriteDTO imagenes) {
		this.imagenes = imagenes;
	}

	/**
	 * Obtiene la lista dinámica con las afinidades elementales (tipos) del Pokémon.
	 *
	 * @return Un {@link ArrayList} conteniendo instancias de {@link TipoPokemonDTO}.
	 */
	public ArrayList<TipoPokemonDTO> getListaTipos() {
		return listaTipos;
	}

	/**
	 * Establece la lista dinámica con las afinidades elementales (tipos) del Pokémon.
	 *
	 * @param listaTipos El nuevo {@link ArrayList} de {@link TipoPokemonDTO} a asignar.
	 */
	public void setListaTipos(ArrayList<TipoPokemonDTO> listaTipos) {
		this.listaTipos = listaTipos;
	}

	/**
	 * Obtiene el objeto contenedor de los recursos y archivos de audio de los gritos.
	 *
	 * @return Una instancia de {@link GritoPokemonDTO} con los recursos multimedia de voz.
	 */
	public GritoPokemonDTO getSonidos() {
		return sonidos;
	}

	/**
	 * Establece el objeto contenedor de los recursos y archivos de audio de los gritos.
	 *
	 * @param sonidos El nuevo objeto {@link GritoPokemonDTO} a asignar.
	 */
	public void setSonidos(GritoPokemonDTO sonidos) {
		this.sonidos = sonidos;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "InformacionPokemonDTO [id=" + id + ", nombre=" + nombre + ", peso=" + peso + ", listaEstadisticas="
				+ listaEstadisticas + ", listaAtaques=" + listaAtaques + ", imagenes=" + imagenes + ", listaTipos="
				+ listaTipos + ", sonidos=" + sonidos + "]";
	}

}