package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa las estadísticas detalladas de un movimiento o ataque.
 * <p>
 * Esta clase se utiliza en el proceso de mapeo y deserialización de datos JSON provenientes
 * de servicios web (como PokeAPI), abstrayendo las propiedades de combate esenciales del ataque 
 * como su daño, precisión, puntos de poder (PP) y clasificaciones elementales.
 * </p>
 * 
 * @version 1.0
 */
public class DetalleMovimientoDTO {
	
	/**
	 * El valor numérico del poder base que posee el movimiento.
	 * <p>
	 * La anotación {@code @SerializedName("power")} vincula este campo con la propiedad 
	 * "power" del JSON externo.
	 * </p>
	 */
	@SerializedName("power")
    private int poder;

	/**
	 * El porcentaje o tasa de acierto y precisión que tiene el ataque.
	 * <p>
	 * La anotación {@code @SerializedName("accuracy")} vincula este campo con la propiedad 
	 * "accuracy" del JSON externo.
	 * </p>
	 */
    @SerializedName("accuracy")
    private int precision;

    /**
     * Los Puntos de Poder (PP) base, que dictan la cantidad de veces que se puede ejecutar el movimiento.
     * <p>
     * La anotación {@code @SerializedName("pp")} vincula este campo con la propiedad 
     * "pp" del JSON externo.
     * </p>
     */
    @SerializedName("pp")
    private int pp;

    /**
     * Objeto que especifica la categoría o clase de daño del ataque (ej. Físico, Especial o Estado).
     * <p>
     * La anotación {@code @SerializedName("damage_class")} vincula este atributo con el nodo 
     * "damage_class" del JSON externo.
     * </p>
     */
    @SerializedName("damage_class")
    private InfoTipoDTO claseDanio; 
    
    /**
     * Objeto que determina el tipo elemental del ataque (ej. Fuego, Agua, Planta, etc.).
     * <p>
     * La anotación {@code @SerializedName("type")} vincula este atributo con el nodo 
     * "type" del JSON externo.
     * </p>
     */
    @SerializedName("type")
    private InfoTipoDTO tipoAtaque; 

    /**
     * Constructor por defecto de la clase.
     * Crea una nueva instancia vacía de {@code DetalleMovimientoDTO}.
     */
    public DetalleMovimientoDTO() {}

	/**
	 * Obtiene el poder base del movimiento.
	 *
	 * @return Un entero con el valor del poder del ataque.
	 */
	public int getPoder() {
		return poder;
	}

	/**
	 * Establece el poder base del movimiento.
	 *
	 * @param poder El nuevo valor de poder para asignar al ataque.
	 */
	public void setPoder(int poder) {
		this.poder = poder;
	}

	/**
	 * Obtiene el porcentaje de precisión del movimiento.
	 *
	 * @return Un entero que representa la tasa de precisión.
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * Establece el porcentaje de precisión del movimiento.
	 *
	 * @param precision El nuevo valor de precisión para asignar al ataque.
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * Obtiene los Puntos de Poder (PP) iniciales del ataque.
	 *
	 * @return Un entero con la cantidad de PP disponibles.
	 */
	public int getPp() {
		return pp;
	}

	/**
	 * Establece los Puntos de Poder (PP) iniciales del ataque.
	 *
	 * @param pp La nueva cantidad de PP a asignar.
	 */
	public void setPp(int pp) {
		this.pp = pp;
	}

	/**
	 * Obtiene el objeto con los detalles de la categoría de daño (Físico/Especial/Estado).
	 *
	 * @return Una instancia de {@link InfoTipoDTO} asociada a la clase de daño.
	 */
	public InfoTipoDTO getClaseDanio() {
		return claseDanio;
	}

	/**
	 * Establece el objeto con los detalles de la categoría de daño.
	 *
	 * @param claseDanio El nuevo objeto {@link InfoTipoDTO} que define la clase de daño.
	 */
	public void setClaseDanio(InfoTipoDTO claseDanio) {
		this.claseDanio = claseDanio;
	}

	/**
	 * Obtiene el objeto con la información del tipo elemental del ataque (Fuego/Agua, etc.).
	 *
	 * @return Una instancia de {@link InfoTipoDTO} asociada al tipo elemental.
	 */
	public InfoTipoDTO getTipoAtaque() {
		return tipoAtaque;
	}

	/**
	 * Establece el objeto con la información del tipo elemental del ataque.
	 *
	 * @param tipoAtaque El nuevo objeto {@link InfoTipoDTO} que define el tipo elemental.
	 */
	public void setTipoAtaque(InfoTipoDTO tipoAtaque) {
		this.tipoAtaque = tipoAtaque;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva con los valores numéricos y estructurales de la instancia.
	 */
	@Override
	public String toString() {
		return "DetalleMovimientoDTO [poder=" + poder + ", precision=" + precision + ", pp=" + pp + ", claseDanio="
				+ claseDanio + ", tipoAtaque=" + tipoAtaque + "]";
	}
    
}