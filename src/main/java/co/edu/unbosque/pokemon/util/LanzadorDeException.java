package co.edu.unbosque.pokemon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.pokemon.exception.CedulaInvalidaException;
import co.edu.unbosque.pokemon.exception.CiudadInvalidaException;
import co.edu.unbosque.pokemon.exception.ContraseniaInvalidaException;
import co.edu.unbosque.pokemon.exception.CorreoInvalidoException;
import co.edu.unbosque.pokemon.exception.DireccionInvalidaException;
import co.edu.unbosque.pokemon.exception.EstadoPedidoInvalidoException;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.exception.MetodoDePagoInvalidoException;
import co.edu.unbosque.pokemon.exception.NombreInvalidoException;
import co.edu.unbosque.pokemon.exception.PlacaInvalidaException;
import co.edu.unbosque.pokemon.exception.RolInvalidoException;
import co.edu.unbosque.pokemon.exception.TamanioInvalidoException;
import co.edu.unbosque.pokemon.exception.TelefonoInvalidoException;
import co.edu.unbosque.pokemon.exception.TipoDeAlimentoInvalidoException;
import co.edu.unbosque.pokemon.exception.TipoDeCartaInvalidaException;
import co.edu.unbosque.pokemon.exception.TipoManipuladorInvalidoException;
import co.edu.unbosque.pokemon.exception.TurnoInvalidoException;
import co.edu.unbosque.pokemon.exception.UsuarioInvalidoException;

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
	 * Verifica que la cédula cumpla con el formato numérico y longitud. * @param
	 * cedula el número de cédula a validar.
	 * 
	 * @throws CedulaInvalidaException si la cédula no es válida.
	 */
	public static void verificarCedula(String cedula) {
		if (cedula == null || cedula.isEmpty()) {
			throw new CedulaInvalidaException("La cédula no puede estar vacía");
		}
		if (cedula.contains(" ")) {
			throw new CedulaInvalidaException("La cédula no debe contener espacios");
		}
		if (!cedula.matches("^[0-9]+$")) {
			throw new CedulaInvalidaException("La cédula solo debe contener números");
		}
		if (cedula.length() < 6 || cedula.length() > 10) {
			throw new CedulaInvalidaException("La cédula debe tener entre 6 y 10 dígitos");
		}
	}

	/**
	 * Verifica que la dirección cumpla con el formato estándar colombiano. * @param
	 * direccion la dirección a validar.
	 * 
	 * @throws DireccionInvalidaException si el formato es incorrecto.
	 */
	public static void verificarDireccion(String direccion) {
		if (direccion == null || direccion.isEmpty()) {
			throw new DireccionInvalidaException("La dirección no puede estar vacía");
		}
		if (direccion.contains("  ")) {
			throw new DireccionInvalidaException("La dirección no puede contener espacios dobles");
		}
		if (!direccion.matches("^[A-Za-z0-9#\\-\\.\\s]+$")) {
			throw new DireccionInvalidaException("La dirección contiene caracteres inválidos");
		}
		if (!direccion.matches(
				"^(Calle|Carrera|Transversal|Diagonal|Avenida|Av|Cl|Cra|Tv|Dg)\\s+[0-9A-Za-z]+\\s+#\\s*[0-9A-Za-z]+-\\s*[0-9A-Za-z]+.*$")) {
			throw new DireccionInvalidaException("La dirección no cumple con un formato válido en Colombia");
		}
		if (direccion.length() < 5 || direccion.length() > 100) {
			throw new DireccionInvalidaException("La dirección debe tener entre 5 y 100 caracteres");
		}
	}

	/**
	 * Valida que el método de pago sea uno de los predefinidos.
	 * 
	 * @param metodoPago método a validar.
	 */
	public static void verificarMetodoPago(String metodoPago) {
		if (metodoPago == null || metodoPago.isEmpty()) {
			throw new MetodoDePagoInvalidoException("El método de pago no puede estar vacío");
		}
		metodoPago = metodoPago.toUpperCase().trim();
		if (!metodoPago.matches("TARJETA DE CRÉDITO|TARJETA DEBITO|PSE")) {
			throw new MetodoDePagoInvalidoException("Método de pago no válido");
		}
	}

	/**
	 * Valida el formato de placa colombiana (tres letras y tres números). * @param
	 * placa placa a validar.
	 */
	public static void verificarPlaca(String placa) {
		if (placa == null || placa.isEmpty()) {
			throw new PlacaInvalidaException("La placa no puede estar vacía");
		}
		if (!placa.toUpperCase().matches("^[A-Z]{3}[0-9]{3}$")) {
			throw new PlacaInvalidaException("La placa no cumple con el formato colombiano (ABC123)");
		}
	}

	/**
	 * Valida el tamaño del paquete. * @param tamano tamaño a validar.
	 */
	public static void verificarTamanoPaquete(String tamano) {
		if (tamano == null || tamano.isEmpty()) {
			throw new TamanioInvalidoException("El tamaño del paquete no puede estar vacío");
		}
		tamano = tamano.toUpperCase().trim();
		if (!tamano.matches("PEQUEÑO|MEDIANO|GRANDE")) {
			throw new TamanioInvalidoException("Tamaño de paquete no válido");
		}
	}

	/**
	 * Valida número telefónico celular colombiano (inicia con 3, tiene 10 dígitos).
	 * * @param telefono teléfono a validar.
	 */
	public static void verificarTelefono(String telefono) {
		if (telefono == null || telefono.isEmpty()) {
			throw new TelefonoInvalidoException("El número de teléfono no puede estar vacío");
		}
		if (!telefono.matches("^3[0-9]{9}$")) {
			throw new TelefonoInvalidoException("El número debe iniciar con 3 y tener 10 dígitos");
		}
	}

	/**
	 * Valida tipos de alimentos permitidos.
	 */
	public static void verificarTipoAlimento(String tipo) {
		if (tipo == null || tipo.isEmpty()) {
			throw new TipoDeAlimentoInvalidoException("El tipo de alimento no puede estar vacío");
		}
		tipo = tipo.toUpperCase().trim();
		if (!tipo.matches("FRUTA|VERDURA|CARNE|LACTEO|CEREAL|BEBIDA")) {
			throw new TipoDeAlimentoInvalidoException("Tipo de alimento no válido");
		}
	}

	/**
	 * Valida tipos de carta permitidos.
	 */
	public static void verificarTipoCarta(String tipo) {
		if (tipo == null || tipo.isEmpty()) {
			throw new TipoDeCartaInvalidaException("El tipo de carta no puede estar vacío");
		}
		tipo = tipo.toUpperCase().trim();
		if (!tipo.matches("ESTANDAR|CERTIFICADA|JURIDICA|PUBLICITARIA")) {
			throw new TipoDeCartaInvalidaException("Tipo de carta no válido");
		}
	}

	/**
	 * Valida el rol del manipulador de carga.
	 */
	public static void verificarTipoManipulador(String tipo) {
		if (tipo == null || tipo.isEmpty()) {
			throw new TipoManipuladorInvalidoException("El tipo de manipulador no puede estar vacío");
		}
		tipo = tipo.toUpperCase().trim();
		if (!tipo.matches("PAQUETES_ALIMENTICIOS|PAQUETES_NO_ALIMENTICIOS|CARTAS")) {
			throw new TipoManipuladorInvalidoException("Tipo de manipulador no válido");
		}
	}

	/**
	 * Valida turnos laborales (Día, Noche, Mixto).
	 */
	public static void verificarTurno(char turno) {
		turno = Character.toUpperCase(turno);
		if (turno != 'D' && turno != 'N' && turno != 'M') {
			throw new TurnoInvalidoException("Turno no válido. Use D, N o M");
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
	 * Valida que la ciudad solo contenga caracteres alfabéticos.
	 */
	public static void verificarCiudad(String ciudadDestino) {
		if (ciudadDestino == null || ciudadDestino.trim().isEmpty()) {
			throw new CiudadInvalidaException("La ciudad destino no puede estar vacía");
		}
		if (!ciudadDestino.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
			throw new CiudadInvalidaException("La ciudad destino solo debe contener letras");
		}
	}

	/**
	 * Valida estados de flujo de pedido.
	 */
	public static void verificarEstadoPedido(String estadoPedido) {
		if (estadoPedido == null || estadoPedido.trim().isEmpty()) {
			throw new EstadoPedidoInvalidoException("El estado del pedido no puede estar vacío");
		}
		if (!estadoPedido.matches("EN_PROCESO|ENTREGADO")) {
			throw new EstadoPedidoInvalidoException("Estado inválido. Valores permitidos: EN_PROCESO, ENTREGADO");
		}
	}

	/**
	 * Valida duplicidad en el sistema.
	 */
	public static void verificarDuplicado(boolean existe, String mensaje) {
		if (existe) {
			throw new CedulaInvalidaException(mensaje);
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
	
	/**
	 * Verifica que el nombre de usuario (username) no esté vacío y tenga una longitud válida.
	 * @param username El nombre de usuario a validar.
	 */
	public static void verificarUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new UsuarioInvalidoException("El username no puede estar vacío o ser nulo.");
		}
		if (username.length() < 3 || username.length() > 20) {
			throw new UsuarioInvalidoException("El username debe tener entre 3 y 20 caracteres.");
		}
	}

	/**
	 * Verifica que el rol del usuario sea válido.
	 * @param rol El rol a validar.
	 */
	public static void verificarRol(String rol) {
		if (rol == null || rol.trim().isEmpty()) {
			throw new RolInvalidoException("El rol no puede estar vacío o ser nulo.");
		}
		// Opcional: Si en tu juego solo hay ciertos roles, puedes descomentar esto:
		/*
		if (!rol.equalsIgnoreCase("Administrador") && !rol.equalsIgnoreCase("Jugador")) {
			throw new IllegalArgumentException("El rol debe ser 'Administrador' o 'Jugador'.");
		}
		*/
	}
}