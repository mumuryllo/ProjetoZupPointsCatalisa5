package components.controllers;

import components.dtos.CriarUsuarioDTO;
import components.dtos.UsuarioResponseDto;
import components.enums.Role;
import components.mapper.UsuarioMapper;
import components.models.Colaborador;
import components.models.Usuario;
import components.services.UsuarioService;
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
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid  @RequestBody CriarUsuarioDTO criarUsuarioDTO){
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
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }



    @GetMapping
    @PreAuthorize("hasRole('GESTOR') OR hasRole('COLABORADOR')")
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
    }

}
