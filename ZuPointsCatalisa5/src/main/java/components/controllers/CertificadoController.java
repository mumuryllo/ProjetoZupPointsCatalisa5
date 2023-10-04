package components.controllers;

import components.models.Certificado;
import components.services.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/certificados")
public class CertificadoController {
    @Autowired
    private CertificadoService certificadoService;
    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<List<Certificado>> Listar(){
        List <Certificado> certificados= certificadoService.listar ();
        return ResponseEntity.ok ( certificados );
    }
    @GetMapping ("/{id}")
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<Certificado> getId(@PathVariable Long id){
        Certificado certificado= certificadoService.listarCategoriaId (id);
        return ResponseEntity.ok () .body (certificado);
    }

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<Certificado> criar(@RequestBody Certificado certificado){
        Certificado certificados= certificadoService.cadastrar (certificado);
        return ResponseEntity.status (HttpStatus.CREATED).body (certificados);

    }
}
