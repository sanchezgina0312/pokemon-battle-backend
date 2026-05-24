package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public String generarCodigoVerificacion() {
		Random rand = new Random();
		int numero = rand.nextInt(9999 - 1000 + 1) + 1000;
		return String.valueOf(numero);
	}

	public void enviarCorreoCodigo(String correoDestinatario, String codigo, String nombreUsuario) {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setFrom("gssanchez@unbosque.edu.co");
		mensaje.setTo(correoDestinatario);
		mensaje.setSubject("Código de Verificación - Registro de Entrenador Pokémon");
		mensaje.setText("¡Hola, " + nombreUsuario + "!\n\n"
				+ "Tu código de verificación para completar el registro en el sistema es: 👉 " + codigo + " 👈\n\n"
				+ "Ingresa este código en la pantalla de la aplicación para activar tu cuenta de entrenador.\n\n"
				+ "Atentamente,\nLaboratorio del Profesor Oak");

		mailSender.send(mensaje);
	}
}