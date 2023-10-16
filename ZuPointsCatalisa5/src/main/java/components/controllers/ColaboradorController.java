package components.controllers;

import components.dtos.UsuarioResponseDto;
import components.mapper.UsuarioMapper;
import components.models.Colaborador;
import components.models.Usuario;
import components.services.ColaboradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colaboradores")
public class ColaboradorController {

    private  final ColaboradorService colaboradorService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    public ResponseEntity<Colaborador> getById(@PathVariable Long id){
        Colaborador user = colaboradorService.listarColaboradorId(id);
        return ResponseEntity.ok(user);
    }



    @GetMapping
    @PreAuthorize("hasRole('GESTOR') OR hasRole('COLABORADOR')")
    public ResponseEntity<List<Colaborador>> getAll(){
        List<Colaborador> usuarios = colaboradorService.listar();
        return ResponseEntity.ok(usuarios);
    }

}
