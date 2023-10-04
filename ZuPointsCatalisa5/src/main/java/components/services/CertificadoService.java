package components.services;

import components.models.Certificado;
import components.repositories.CertificadoRepository;
import components.services.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificadoService {

    @Autowired
    private CertificadoRepository certificadoRepository;

    public List <Certificado> listar(){
        return certificadoRepository.findAll();
    }

    public Certificado listarCategoriaId (Long id){
        Optional<Certificado> obj=certificadoRepository.findById(id);
        return obj.orElseThrow(() -> new UsernameNotFoundException (id + " Não encontrado!"));
    }

    public Certificado cadastrar(Certificado certificado) {
        return certificadoRepository.save(certificado);
    }
}
