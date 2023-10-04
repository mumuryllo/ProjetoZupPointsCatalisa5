package components.controllers;

import components.dtos.AvaliacaoResponseDTO;
import components.dtos.CriarAvaliacaoDTO;
import components.models.Avaliacao;
import components.services.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoesRecebidas(){
        return ResponseEntity.ok(avaliacaoService.listarAvaliacaoRecebidas());
    }

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<AvaliacaoResponseDTO> criar (@RequestBody CriarAvaliacaoDTO criarAvaliacaoDTO){
        AvaliacaoResponseDTO avaliacao = avaliacaoService.criarAvaliacao(criarAvaliacaoDTO);
        return new ResponseEntity<>(avaliacao, HttpStatus.CREATED);
    }
}
