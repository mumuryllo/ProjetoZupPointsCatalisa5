package components.controllers;

import components.dtos.CriarUsuarioDTO;
import components.dtos.UsuarioResponseDto;
import components.dtos.UsuarioSenhaDTO;
import components.mapper.UsuarioMapper;
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
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(criarUsuarioDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> upadtePassword(@Valid @PathVariable Long id, @RequestBody UsuarioSenhaDTO usuario){
        Usuario user = usuarioService.editarSenha(id,usuario.getSenhaAtual(),usuario.getNovaSenha(),usuario.getConfirmarSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
    }

}
