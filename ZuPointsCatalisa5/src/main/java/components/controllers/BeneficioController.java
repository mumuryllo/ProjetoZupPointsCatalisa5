package components.controllers;

import components.models.Beneficio;
import components.services.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;


    @GetMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<List<Beneficio>> listarBeneficios() {
        List<Beneficio> beneficios = beneficioService.listarTodosBeneficios();
        return new ResponseEntity<>(beneficios, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<Beneficio> listarBeneficioPorId(@PathVariable Long id) {
        Optional<Beneficio> beneficio = beneficioService.listarBeneficioPorId(id);
        if (beneficio.isPresent()) {
            return new ResponseEntity<>(beneficio.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('COLABORADOR')")
    public ResponseEntity<Beneficio> criarBeneficio(@RequestBody Beneficio beneficio) {
        Beneficio novoBeneficio = beneficioService.criarBeneficio(beneficio);
        return new ResponseEntity<>(novoBeneficio, HttpStatus.CREATED);
    }

}