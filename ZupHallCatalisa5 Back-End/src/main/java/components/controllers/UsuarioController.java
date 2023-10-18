package components.controllers;

import components.dtos.CriarUsuarioDTO;
import components.dtos.UsuarioResponseDto;
import components.enums.Role;
import components.mapper.UsuarioMapper;
import components.models.Usuario;
import components.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@Tag(name = "UsuarioController", description = "Endpoints relacionados a usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(description = "Cria um novo usuário com base nas informações fornecidas no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuarioResponseDto> create(
            @Valid @RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        Usuario usuario;
        if (criarUsuarioDTO.getRole() == Role.ROLE_COLABORADOR) {
            usuario = usuarioService.criarColaborador(UsuarioMapper.toColaborador(criarUsuarioDTO));
        } else {
            usuario = usuarioService.salvar(UsuarioMapper.toUsuario(criarUsuarioDTO));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    @Operation(description = "Obtém as informações de um usuário com base no ID especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponseDto> getById(
            @Parameter(name = "ID do usuário", required = true) @PathVariable Long id) {
        Usuario user = usuarioService.buscarId(id);
        if (user != null) {
            return ResponseEntity.ok(UsuarioMapper.toDto(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('GESTOR') OR hasRole('COLABORADOR')")
    @Operation(description = "Obtém a lista de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado")
    })
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        if (!usuarios.isEmpty()) {
            return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}