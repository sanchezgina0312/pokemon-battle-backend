package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.TiendaService;

/**
 * Controlador REST que gestiona las transacciones comerciales dentro de la tienda Pokémon.
 * <p>
 * Proporciona los endpoints necesarios para que los usuarios adquieran ítems y objetos
 * consumibles o de equipamiento utilizando los recursos económicos (monedas/dinero) de su cuenta.
 * Permite peticiones CORS desde los orígenes locales en los puertos 8080 y 8081.
 *
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/tienda")
public class TiendaController {

	/**
	 * Servicio que contiene la lógica de negocio para procesar las transacciones de la tienda.
	 */
	@Autowired
	private TiendaService tiendaSer;

	/**
	 * Procesa la compra de un ítem por parte de un usuario específico.
	 * <p>
	 * Este método evalúa el código numérico de respuesta devuelto por el servicio para determinar el resultado:
	 * <ul>
	 * <li>Si retorna {@code 0}: La transacción fue exitosa (Estado HTTP 202 - Accepted).</li>
	 * <li>Si retorna {@code 2}: El usuario no cuenta con fondos suficientes (Estado HTTP 402 - Payment Required).</li>
	 * <li>Cualquier otro código: Se interpreta como un error en la solicitud o datos inválidos (Estado HTTP 400 - Bad Request).</li>
	 * </ul>
	 *
	 * @param idUsuario El identificador único del usuario o entrenador que realiza la compra.
	 * @param idItem    El identificador único del ítem u objeto que se desea adquirir.
	 * @return Un {@link ResponseEntity} con un mensaje descriptivo del resultado de la operación y su 
	 * respectivo código de estado HTTP ({@link HttpStatus#ACCEPTED}, {@link HttpStatus#PAYMENT_REQUIRED} 
	 * o {@link HttpStatus#BAD_REQUEST}).
	 */
	@PostMapping("/comprar")
	public ResponseEntity<String> comprar(@RequestParam long idUsuario, @RequestParam long idItem) {
		int res = tiendaSer.realizarCompra(idUsuario, idItem);
		if (res == 0)
			return new ResponseEntity<>("¡Compra exitosa!", HttpStatus.ACCEPTED);
		if (res == 2)
			return new ResponseEntity<>("No tienes suficiente dinero", HttpStatus.PAYMENT_REQUIRED);
		return new ResponseEntity<>("Error al procesar", HttpStatus.BAD_REQUEST);
	}
}