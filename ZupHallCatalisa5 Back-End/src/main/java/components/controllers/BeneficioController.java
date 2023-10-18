package components.controllers;

import components.dtos.BeneficioRequestDTO;
import components.models.Beneficio;
import components.services.BeneficioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Optional;

@RestController
@RequestMapping("/beneficios")
@Tag(name = "BeneficioController", description = "Endpoints relacionados a benefícios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;

    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    @Operation(description = "Lista todos os benefícios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de benefícios")
    })
    public ResponseEntity<List<Beneficio>> listarBeneficios() {
        List<Beneficio> beneficios = beneficioService.listarTodosBeneficios();
        return new ResponseEntity<>(beneficios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    @Operation(description = "Obtém um benefício por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benefício encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Benefício não encontrado")
    })
    public ResponseEntity<Beneficio> listarBeneficioPorId(
            @Parameter(name = "ID do benefício", required = true) @PathVariable Long id) {
        Optional<Beneficio> beneficio = beneficioService.listarBeneficioPorId(id);
        if (beneficio.isPresent()) {
            return new ResponseEntity<>(beneficio.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(description = "Cria um novo benefício")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Benefício criado com sucesso")
    })
    public ResponseEntity<Beneficio> criarBeneficio(@RequestBody Beneficio beneficio) {
        Beneficio novoBeneficio = beneficioService.criarBeneficio(beneficio);
        return new ResponseEntity<>(novoBeneficio, HttpStatus.CREATED);
    }

    @PostMapping("/resgatar")
    @PreAuthorize("hasRole('COLABORADOR')")
    @Operation(description = "Resgata um benefício para um colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Benefício resgatado com sucesso")
    })
    public ResponseEntity<Beneficio> resgatarBeneficio(@Valid @RequestBody BeneficioRequestDTO beneficioRequestDTO) {
        Beneficio novoBeneficio = beneficioService.adicionarBeneficioColaborador(beneficioRequestDTO);
        return new ResponseEntity<>(novoBeneficio, HttpStatus.CREATED);
    }
}