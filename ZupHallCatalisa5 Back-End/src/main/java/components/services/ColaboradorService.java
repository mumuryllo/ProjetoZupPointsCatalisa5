package components.services;

import components.models.Certificado;
import components.models.Colaborador;
import components.repositories.ColaboradorRepository;
import components.services.exceptions.UsernameNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<Colaborador> listar() {
        return colaboradorRepository.findAll();
    }

    public Colaborador listarColaboradorId(Long id) {
        Optional<Colaborador> obj = colaboradorRepository.findById(id);
        return obj.orElseThrow(() -> new UsernameNaoEncontradoException(id + " Não encontrado!"));
    }


}
