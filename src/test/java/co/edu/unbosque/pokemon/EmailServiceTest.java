package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import co.edu.unbosque.pokemon.service.EmailService;

/**
 * Clase de pruebas unitarias para {@link EmailService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio de correo utilizando Mockito para simular
 * el envío de emails.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de generación de códigos
 * y envío de correos electrónicos.
 * </p>
 */
class EmailServiceTest {

	/**
	 * Mock del servicio de envío de correos.
	 */
	@Mock
	private JavaMailSender mailSender;

	/**
	 * Servicio que será probado.
	 */
	private EmailService emailService;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		emailService = new EmailService();

		try {

			Field mailField = EmailService.class
					.getDeclaredField("mailSender");

			mailField.setAccessible(true);

			mailField.set(emailService, mailSender);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba la generación de un código de verificación.
	 */
	@Test
	void testGenerarCodigoVerificacion() {

		String codigo = emailService.generarCodigoVerificacion();

		assertNotNull(codigo);

		assertEquals(4, codigo.length());

		assertTrue(codigo.matches("\\d+"));
	}

	/**
	 * Prueba el envío de un correo con código de verificación.
	 */
	@Test
	void testEnviarCorreoCodigo() {

		doNothing().when(mailSender)
				.send(any(SimpleMailMessage.class));

		assertDoesNotThrow(() -> {

			emailService.enviarCorreoCodigo(
					"usuario@mail.com",
					"1234",
					"Ash");
		});

		verify(mailSender)
				.send(any(SimpleMailMessage.class));
	}
}