package components.controllers;

import components.dtos.CertificadoDTO;
import components.models.Certificado;
import components.services.CertificadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/certificados")
@Tag(name = "CertificadoController", description = "Endpoints relacionados a certificados")
public class CertificadoController {

    @Autowired
    private CertificadoService certificadoService;

    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    @Operation(description = "Lista todos os certificados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de certificados")
    })
    public ResponseEntity<List<Certificado>> listar() {
        List<Certificado> certificados = certificadoService.listar();
        return ResponseEntity.ok(certificados);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    @Operation(description = "Obtém um certificado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificado encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Certificado não encontrado")
    })
    public ResponseEntity<Certificado> getId(
            @Parameter(name = "ID do certificado", required = true) @PathVariable Long id) {
        Certificado certificado = certificadoService.listarCertificadoId(id);
        return ResponseEntity.ok().body(certificado);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    @Operation(description = "Atualiza um certificado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Certificado não encontrado")
    })
    public ResponseEntity<Certificado> update(
            @Parameter(name = "ID do certificado", required = true) @PathVariable Long id,
            @Valid @RequestBody Certificado obj) {
        obj = certificadoService.update(id, obj);
        return ResponseEntity.status(200).body(obj);
    }

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    @Operation(description = "Cria um novo certificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Certificado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<CertificadoDTO> criar(@Valid @RequestBody CertificadoDTO certificado) {
        CertificadoDTO certificados = certificadoService.criarCertificado(certificado);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificados);
    }
}