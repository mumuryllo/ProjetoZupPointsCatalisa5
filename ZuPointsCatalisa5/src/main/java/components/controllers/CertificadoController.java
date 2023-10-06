package components.controllers;

import components.dtos.CertificadoDTO;
import components.models.Certificado;
import components.models.Colaborador;
import components.services.CertificadoService;
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
public class CertificadoController {
    @Autowired
    private CertificadoService certificadoService;
    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    public ResponseEntity<List<Certificado>> Listar(){
        List <Certificado> certificados= certificadoService.listar ();
        return ResponseEntity.ok ( certificados );
    }
    @GetMapping ("/{id}")
    @PreAuthorize("hasRole('COLABORADOR') OR hasRole('GESTOR')")
    public ResponseEntity<Certificado> getId(@PathVariable Long id){
        Certificado certificado= certificadoService.listarCertificadoId(id);
        return ResponseEntity.ok ().body(certificado);
    }

    @PreAuthorize("hasRole('GESTOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Certificado> update(@PathVariable Long id,@Valid @RequestBody Certificado obj){
        obj= certificadoService.update(id,obj);
        return  ResponseEntity.status(200).body(obj);
    }

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<CertificadoDTO> criar(@Valid @RequestBody CertificadoDTO certificado){
        CertificadoDTO certificados = certificadoService.criarCertificado(certificado);
        return ResponseEntity.status(HttpStatus.CREATED).body (certificados);
    }
}
