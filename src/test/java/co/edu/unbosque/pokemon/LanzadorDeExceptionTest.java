package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.unbosque.pokemon.exception.ContraseniaInvalidaException;
import co.edu.unbosque.pokemon.exception.CorreoInvalidoException;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.exception.NombreInvalidoException;
import co.edu.unbosque.pokemon.util.LanzadorDeException;

/**
 * Clase de pruebas unitarias para la clase {@link LanzadorDeException}.
 * 
 * Se encarga de validar que los métodos de verificación de datos
 * (nombre, correo, id y contraseña) lancen o no las excepciones
 * correspondientes según las reglas de negocio definidas.
 */
class LanzadorDeExceptionTest {

	/**
	 * Verifica que un nombre válido no lance excepción.
	 */
	@Test
	void testVerificarNombreValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarNombre("Juan Perez"));
	}

	/**
	 * Verifica que un nombre vacío lance {@link NombreInvalidoException}.
	 */
	@Test
	void testVerificarNombreVacio() {
		assertThrows(NombreInvalidoException.class, () -> LanzadorDeException.verificarNombre(""));
	}

	/**
	 * Verifica que un nombre con espacios dobles lance {@link NombreInvalidoException}.
	 */
	@Test
	void testVerificarNombreConEspaciosDobles() {
		assertThrows(NombreInvalidoException.class, () -> LanzadorDeException.verificarNombre("Juan  Perez"));
	}

	/**
	 * Verifica que un nombre con caracteres inválidos lance {@link NombreInvalidoException}.
	 */
	@Test
	void testVerificarNombreConCaracteresInvalidos() {
		assertThrows(NombreInvalidoException.class, () -> LanzadorDeException.verificarNombre("Juan123"));
	}

	/**
	 * Verifica que un correo válido retorne verdadero.
	 */
	@Test
	void testVerificarCorreoValido() {
		assertTrue(LanzadorDeException.verificarCorreoElectronico("usuario@mail.com"));
	}

	/**
	 * Verifica que un correo inválido lance {@link CorreoInvalidoException}.
	 */
	@Test
	void testVerificarCorreoInvalido() {
		assertThrows(CorreoInvalidoException.class,
				() -> LanzadorDeException.verificarCorreoElectronico("correo-malo"));
	}

	/**
	 * Verifica que un ID válido no lance excepción.
	 */
	@Test
	void testVerificarIdValido() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarId(1L));
	}

	/**
	 * Verifica que un ID nulo lance {@link IdInvalidoException}.
	 */
	@Test
	void testVerificarIdNulo() {
		assertThrows(IdInvalidoException.class, () -> LanzadorDeException.verificarId(null));
	}

	/**
	 * Verifica que un ID negativo lance {@link IdInvalidoException}.
	 */
	@Test
	void testVerificarIdNegativo() {
		assertThrows(IdInvalidoException.class, () -> LanzadorDeException.verificarId(-5L));
	}

	/**
	 * Verifica que una contraseña válida no lance excepción.
	 */
	@Test
	void testVerificarContrasenaValida() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarContrasena("Clave123"));
	}

	/**
	 * Verifica que una contraseña vacía lance {@link ContraseniaInvalidaException}.
	 */
	@Test
	void testVerificarContrasenaVacia() {
		assertThrows(ContraseniaInvalidaException.class, () -> LanzadorDeException.verificarContrasena(""));
	}

	/**
	 * Verifica que una contraseña con espacios lance {@link ContraseniaInvalidaException}.
	 */
	@Test
	void testVerificarContrasenaConEspacios() {
		assertThrows(ContraseniaInvalidaException.class, () -> LanzadorDeException.verificarContrasena("Clave 123"));
	}

	/**
	 * Verifica que una contraseña demasiado corta lance {@link ContraseniaInvalidaException}.
	 */
	@Test
	void testVerificarContrasenaCorta() {
		assertThrows(ContraseniaInvalidaException.class, () -> LanzadorDeException.verificarContrasena("Clv123"));
	}

	/**
	 * Verifica que una contraseña sin mayúscula o número lance {@link ContraseniaInvalidaException}.
	 */
	@Test
	void testVerificarContrasenaSinMayusculaONumero() {
		assertThrows(ContraseniaInvalidaException.class,
				() -> LanzadorDeException.verificarContrasena("claveabcd"));
	}

	/**
	 * Verifica el comportamiento cuando el correo ya está duplicado (true).
	 */
	@Test
	void testVerificarCorreoDuplicadoTrue() {
		assertThrows(CorreoInvalidoException.class, () -> LanzadorDeException.verificarCorreoDuplicado(true));
	}

	/**
	 * Verifica el comportamiento cuando el correo no está duplicado (false).
	 */
	@Test
	void testVerificarCorreoDuplicadoFalse() {
		assertDoesNotThrow(() -> LanzadorDeException.verificarCorreoDuplicado(false));
	}
}