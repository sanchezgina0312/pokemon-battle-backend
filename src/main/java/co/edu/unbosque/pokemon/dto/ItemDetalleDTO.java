package co.edu.unbosque.pokemon.dto;

import java.util.ArrayList; 
import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa las especificaciones detalladas de un ítem u objeto.
 * <p>
 * Esta clase funciona como el modelo raíz para la deserialización de los datos JSON de ítems 
 * (como pociones, Pokéballs o bayas) obtenidos desde la PokeAPI, consolidando su identificador, 
 * costo comercial, recursos gráficos y descripciones contextuales.
 * </p>
 * 
 * @version 1.0
 */
public class ItemDetalleDTO {

	/**
	 * El identificador numérico único del ítem en el sistema de la API externa.
	 * <p>
	 * La anotación {@code @SerializedName("id")} vincula este campo con la propiedad 
	 * "id" del JSON procesado por Gson.
	 * </p>
	 */
	@SerializedName("id")
	private int id;

	/**
	 * El nombre oficial en inglés que identifica al ítem en la base de datos externa.
	 * <p>
	 * La anotación {@code @SerializedName("name")} vincula este campo con la propiedad 
	 * "name" del JSON procesado por Gson.
	 * </p>
	 */
	@SerializedName("name")
	private String nombreIngles;

	/**
	 * El costo o precio base del ítem expresado en la moneda del juego (Pokédolares).
	 * <p>
	 * La anotación {@code @SerializedName("cost")} vincula este campo con la propiedad 
	 * "cost" del JSON procesado por Gson.
	 * </p>
	 */
	@SerializedName("cost")
	private int costo;

	/**
	 * Contenedor de las URLs que apuntan a las representaciones gráficas e íconos del objeto.
	 * <p>
	 * La anotación {@code @SerializedName("sprites")} mapea este atributo con el objeto 
	 * anidado "sprites" del JSON externo, representándolo mediante {@link SpriteItemDTO}.
	 * </p>
	 */
	@SerializedName("sprites")
	private SpriteItemDTO imagenes;

	/**
	 * Listado dinámico de las descripciones narrativas e históricas asociadas al uso del ítem.
	 * <p>
	 * La anotación {@code @SerializedName("flavor_text_entries")} mapea este atributo con el arreglo 
	 * "flavor_text_entries" del JSON, estructurando los datos en objetos {@link DescripcionDTO}.
	 * </p>
	 */
	@SerializedName("flavor_text_entries")
	private ArrayList<DescripcionDTO> listaDescripciones;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code ItemDetalleDTO}.
	 */
	public ItemDetalleDTO() {

	}

	/**
	 * Constructor parametrizado con todos los atributos de la clase.
	 * Inicializa una nueva instancia de {@code ItemDetalleDTO} con los detalles completos del ítem.
	 *
	 * @param id                 El ID numérico único del ítem.
	 * @param nombreIngles       El nombre oficial en inglés del objeto.
	 * @param costo              El precio o valor comercial del ítem.
	 * @param imagenes           Objeto {@link SpriteItemDTO} con la información de las imágenes.
	 * @param listaDescripciones Lista de objetos {@link DescripcionDTO} con las entradas de texto informativas.
	 */
	public ItemDetalleDTO(int id, String nombreIngles, int costo, SpriteItemDTO imagenes,
			ArrayList<DescripcionDTO> listaDescripciones) {
		super();
		this.id = id;
		this.nombreIngles = nombreIngles;
		this.costo = costo;
		this.imagenes = imagenes;
		this.listaDescripciones = listaDescripciones;
	}

	/**
	 * Obtiene el identificador numérico único del ítem.
	 *
	 * @return Un entero con el ID de la PokeAPI.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador numérico único del ítem.
	 *
	 * @param id El nuevo ID de PokeAPI a asignar.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre en inglés del ítem.
	 *
	 * @return Una cadena de caracteres con el nombre oficial del objeto.
	 */
	public String getNombreIngles() {
		return nombreIngles;
	}

	/**
	 * Establece el nombre en inglés del ítem.
	 *
	 * @param nombreIngles El nuevo nombre del objeto a asignar.
	 */
	public void setNombreIngles(String nombreIngles) {
		this.nombreIngles = nombreIngles;
	}

	/**
	 * Obtiene el costo o valor base del ítem.
	 *
	 * @return Un entero con el precio del objeto.
	 */
	public int getCosto() {
		return costo;
	}

	/**
	 * Establece el costo o valor base del ítem.
	 *
	 * @param costo El nuevo costo comercial a asignar.
	 */
	public void setCosto(int costo) {
		this.costo = costo;
	}

	/**
	 * Obtiene el objeto que contiene las imágenes o sprites del ítem.
	 *
	 * @return Una instancia de {@link SpriteItemDTO} con los recursos gráficos.
	 */
	public SpriteItemDTO getImagenes() {
		return imagenes;
	}

	/**
	 * Establece el objeto que contiene las imágenes o sprites del ítem.
	 *
	 * @param imagenes El nuevo objeto {@link SpriteItemDTO} a asignar.
	 */
	public void setImagenes(SpriteItemDTO imagenes) {
		this.imagenes = imagenes;
	}

	/**
	 * Obtiene la lista dinámica de descripciones asociadas al ítem.
	 *
	 * @return Un {@link ArrayList} conteniendo instancias de {@link DescripcionDTO}.
	 */
	public ArrayList<DescripcionDTO> getListaDescripciones() {
		return listaDescripciones;
	}

	/**
	 * Establece la lista dinámica de descripciones asociadas al ítem.
	 *
	 * @param listaDescripciones El nuevo {@link ArrayList} de {@link DescripcionDTO} a asignar.
	 */
	public void setListaDescripciones(ArrayList<DescripcionDTO> listaDescripciones) {
		this.listaDescripciones = listaDescripciones;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "ItemDetalleDTO [id=" + id + ", nombreIngles=" + nombreIngles + ", costo=" + costo + ", imagenes="
				+ imagenes + ", listaDescripciones=" + listaDescripciones + "]";
	}

}