package co.edu.unbosque.pokemon.exception;

/**
 * Excepción personalizada que se lanza cuando se detecta una cédula con formato
 * o valor inválido dentro del sistema de mensajería.
 *
 * <p>Esta excepción extiende {@link RuntimeException}, por lo que es una excepción
 * no verificada (<i>unchecked exception</i>) y no requiere ser declarada
 * explícitamente en la firma de los métodos que la lancen.</p>
 *
 * @version 1.0
 * @since 1.0
 * @see RuntimeException
 */
public class CedulaInvalidaException extends RuntimeException {

    /**
     * Construye una nueva instancia de {@code CedulaInvalidaException} con el
     * mensaje de detalle especificado.
     *
     * <p>El mensaje puede ser recuperado posteriormente mediante el método
     * {@link Throwable#getMessage()}.</p>
     *
     * @param mensaje descripción detallada del motivo por el cual la cédula
     *                se considera inválida; no debe ser {@code null}.
     */
    public CedulaInvalidaException(String mensaje) {
        super(mensaje);
    }
}