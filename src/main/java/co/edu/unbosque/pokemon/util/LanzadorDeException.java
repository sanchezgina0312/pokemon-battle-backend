package co.edu.unbosque.pokemon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.pokemon.exception.ContraseniaInvalidaException;
import co.edu.unbosque.pokemon.exception.CorreoInvalidoException;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.exception.NombreInvalidoException;

/**
 * Clase utilitaria que centraliza la validación de datos del dominio del
 * sistema de mensajería.
 * <p>
 * Cada método verifica las reglas de negocio correspondientes a un campo
 * específico y lanza una excepción personalizada en caso de que el valor no
 * cumpla con los requisitos. Todos los métodos son estáticos, por lo que no es
 * necesario instanciar la clase.
 * </p>
 *
 * @author Angie Villarreal
 * @version 1.0
 */
public class LanzadorDeException {

	/**
	 * Verifica que el nombre proporcionado sea válido según las reglas del sistema.
	 * * @param nombre el nombre completo a validar.
	 * 
	 * @throws NombreInvalidoException si el nombre contiene espacios dobles,
	 *                                 caracteres no permitidos o tiene menos de dos
	 *                                 palabras.
	 */
	public static void verificarNombre(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new NombreInvalidoException("El nombre no puede estar vacío");
		}
		if (nombre.contains("  ")) {
			throw new NombreInvalidoException("El nombre no puede contener espacios dobles");
		}
		if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
			throw new NombreInvalidoException("El nombre solo debe contener letras y espacios");
		}
		String[] palabras = nombre.trim().split("\\s+");
		if (palabras.length < 2) {
			throw new NombreInvalidoException("El nombre debe tener al menos dos palabras");
		}
	}

	/**
	 * Verifica que el correo electrónico proporcionado tenga un formato válido.
	 * * @param correo la dirección de correo electrónico a validar.
	 * 
	 * @return true si el correo tiene un formato válido.
	 * @throws CorreoInvalidoException si el correo no cumple con el formato
	 *                                 esperado.
	 */
	public static boolean verificarCorreoElectronico(String correo) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
		Matcher matcher = pattern.matcher(correo);

		if (matcher.find()) {
			return true;
		} else {
			throw new CorreoInvalidoException(
					"El correo electrónico ingresado no es válido. Verifique el formato (ejemplo: usuario@dominio.com).");
		}
	}

	/**
	 * Valida que el ID sea mayor a cero.
	 */
	public static void verificarId(Long id) {
		if (id == null) {
			throw new IdInvalidoException("El ID no puede ser nulo");
		}
		if (id <= 0) {
			throw new IdInvalidoException("El ID debe ser un número positivo");
		}
	}

	/**
	 * Verifica que la contraseña cumpla con criterios de seguridad: Mínimo 8
	 * caracteres, una mayúscula y un número. * @param contrasena clave a validar.
	 * 
	 * @throws ContraseniaInvalidaException si la clave es débil o inválida.
	 */
	public static void verificarContrasena(String contrasena) {
		if (contrasena == null || contrasena.isEmpty()) {
			throw new ContraseniaInvalidaException("La contraseña no puede estar vacía");
		}
		if (contrasena.contains(" ")) {
			throw new ContraseniaInvalidaException("La contraseña no debe contener espacios");
		}
		if (contrasena.length() < 8) {
			throw new ContraseniaInvalidaException("La contraseña debe tener al menos 8 caracteres");
		}
		if (!contrasena.matches("^(?=.*[A-Z])(?=.*[0-9]).+$")) {
			throw new ContraseniaInvalidaException("La contraseña debe contener al menos una mayúscula y un número");
		}
	}
	
	
}