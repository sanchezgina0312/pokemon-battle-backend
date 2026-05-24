package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.entity.Auditoria;
import co.edu.unbosque.pokemon.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auditoria")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "http://localhost:4200" })
@Tag(name = "Auditoria")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @Operation(summary = "Obtener todos los registros de auditoría", description = "Retorna la lista completa de acciones registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Lista de auditoría retornada exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay registros de auditoría") })
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<Auditoria>> mostrarTodo() {
        List<Auditoria> lista = auditoriaService.getAll();
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }
}