package components.controllers;

import components.dtos.UsuarioResponseDto;
import components.mapper.UsuarioMapper;
import components.models.Colaborador;
import components.models.Usuario;
import components.services.ColaboradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colaboradores")
@Tag(name = "ColaboradorController", description = "Endpoints relacionados a colaboradores")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    @Operation(description = "Obtém as informações de um colaborador com base no ID especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador encontrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrado")
    })
    public ResponseEntity<Colaborador> getById(
            @Parameter(name = "ID do colaborador", required = true) @PathVariable Long id) {
        Colaborador user = colaboradorService.listarColaboradorId(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('GESTOR') OR hasRole('COLABORADOR')")
    @Operation(description = "Obtém a lista de todos os colaboradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de colaboradores recuperada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Nenhum colaborador encontrado")
    })
    public ResponseEntity<List<Colaborador>> getAll() {
        List<Colaborador> colaboradores = colaboradorService.listar();
        if (!colaboradores.isEmpty()) {
            return ResponseEntity.ok(colaboradores);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}