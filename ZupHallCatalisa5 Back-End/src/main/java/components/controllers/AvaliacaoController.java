package components.controllers;

import components.dtos.AvaliacaoResponseDTO;
import components.dtos.CriarAvaliacaoDTO;
import components.services.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "AvaliacaoController", description = "Endpoints relacionados a avaliações")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    @Operation(description = "Lista as avaliações recebidas por um colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações recebidas")
    })
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoesRecebidas() {
        return ResponseEntity.ok(avaliacaoService.listarAvaliacaoRecebidas());
    }

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    @Operation(description = "Cria uma nova avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AvaliacaoResponseDTO> criar(@Valid @RequestBody CriarAvaliacaoDTO criarAvaliacaoDTO) {
        AvaliacaoResponseDTO avaliacao = avaliacaoService.criarAvaliacao(criarAvaliacaoDTO);
        return new ResponseEntity<>(avaliacao, HttpStatus.CREATED);
    }
}