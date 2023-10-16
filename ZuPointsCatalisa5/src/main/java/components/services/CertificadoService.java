package components.services;

import components.dtos.CertificadoDTO;
import components.enums.ValidarCertificado;
import components.models.Certificado;
import components.models.Colaborador;
import components.repositories.CertificadoRepository;
import components.repositories.ColaboradorRepository;
import components.services.exceptions.ColaboradorNaoLogadoException;
import components.services.exceptions.ColaboradorNaoEncontradoException;
import components.services.exceptions.CredencialUniqueViolationException;
import components.services.exceptions.UsernameNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CertificadoService {

    @Autowired
    private CertificadoRepository certificadoRepository;
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<Certificado> listar() {
        return certificadoRepository.findAll();
    }

    public Certificado listarCertificadoId(Long id) {
        Optional<Certificado> obj = certificadoRepository.findById(id);
        return obj.orElseThrow(() -> new UsernameNaoEncontradoException(id + " Não encontrado!"));
    }

    public CertificadoDTO criarCertificado(CertificadoDTO certificadoDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        try{


        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String colaboradorUsername = userDetails.getUsername();

            Certificado certificados = new Certificado();


            Colaborador colaborador = colaboradorRepository.findByUsername(colaboradorUsername)
                    .orElseThrow(() -> new ColaboradorNaoEncontradoException("Colaborador remetente não encontrado"));

            certificados.setNome(certificadoDto.getNome());
            certificados.setNumero_credencial(certificadoDto.getNumero_credencial());
            certificados.setTipo_certificado(certificadoDto.getTipoCertificado());
            certificados.setLink(certificadoDto.getLink());
            certificados.setCertificado_valido(certificadoDto.getValidarCertificado());
            certificados.setColaborador(colaborador);

            colaborador.getCertificadosEnviados().add(certificados);
            colaboradorRepository.save(colaborador);
            certificadoRepository.save(certificados);

            return certificadoDto;
        } else {
            throw new ColaboradorNaoLogadoException("Colaborador não está logado");
        }
        } catch (DataIntegrityViolationException e){
            throw new CredencialUniqueViolationException("Um certificado com essa credencial já existe");
        }
    }

    @Transactional
    public Certificado atualizarTipoCertificado(Long id) {
        Certificado certificado = new Certificado();
        certificado.getId();
        certificado.setCertificado_valido(certificado.getCertificado_valido());
        return certificadoRepository.save(certificado);
    }

    public Certificado update(Long id,Certificado obj){
            Certificado certificado = certificadoRepository.getReferenceById(id);
            updateData(certificado,obj);
            return certificadoRepository.save(certificado);
    }
    private void updateData(Certificado certificado, Certificado obj) {
        certificado.setCertificado_valido(obj.getCertificado_valido());

        if (obj.getCertificado_valido()==ValidarCertificado.VALIDO) {
            Colaborador colaborador = certificado.getColaborador();
            if (colaborador != null) {
                colaborador.setPontosAcumulados(colaborador.getPontosAcumulados() + 10);
            }else {
                colaborador.setPontosAcumulados(colaborador.getPontosAcumulados() - 10);
            }
        }

    }

}
