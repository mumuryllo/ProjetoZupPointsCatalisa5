package components.services;

import components.dtos.BeneficioRequestDTO;
import components.models.Beneficio;
import components.models.Colaborador;
import components.repositories.BeneficioRepository;
import components.repositories.ColaboradorRepository;
import components.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public Beneficio criarBeneficio(Beneficio beneficio) {
        beneficio.setVoucher(gerarVoucher());
        return beneficioRepository.save(beneficio);
    }

    public Beneficio adicionarBeneficioColaborador(BeneficioRequestDTO beneficioRequestDTO){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String colaboradorUsername = userDetails.getUsername();

            Colaborador colaboradorLogado = colaboradorRepository.findByUsername(colaboradorUsername)
                    .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador não encontrado"));

            Beneficio beneficioResgatado = beneficioRepository.findById(beneficioRequestDTO.getId())
                    .orElseThrow(() -> new BeneficioNotFoundException("Benefício não encontrado"));

            validarBeneficio(colaboradorLogado, beneficioResgatado);
            definirTempoDeResgate(beneficioResgatado);


            colaboradorLogado.getBeneficios().add(beneficioResgatado);

            beneficioResgatado.setQtdDisponivel(beneficioResgatado.getQtdDisponivel() - 1);

            colaboradorRepository.save(colaboradorLogado);
            beneficioRepository.save(beneficioResgatado);

            return beneficioResgatado;
        } else {
            throw new ColaboradorNaoLogadoException("Colaborador não está logado");
        }
    }

    public List<Beneficio> listarTodosBeneficios() {
        return beneficioRepository.findAll();
    }

    public Optional<Beneficio> listarBeneficioPorId(Long id) {
        return beneficioRepository.findById(id);
    }

    private String gerarVoucher() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder voucher = new StringBuilder();
        int length = 10;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            voucher.append(characters.charAt(index));
        }

        return voucher.toString();
    }

    private void definirTempoDeResgate(Beneficio beneficio) {

        LocalDateTime dataAtual = LocalDateTime.now();

        LocalDateTime dataDeExpiracao = dataAtual.plus(30, ChronoUnit.DAYS);

        beneficio.setDataCriacao(dataDeExpiracao);
    }

    public void validarBeneficio(Colaborador colaborador, Beneficio beneficio){
        if (beneficio.getQtdDisponivel() <= 0) {
            throw new ResgatarBeneficioException("Não há quantidade disponível deste benefício");
        }
        if (colaborador.getPontosAcumulados() < beneficio.getQtdPontosParaComprar()){
            throw new ResgatarBeneficioException("O colaborador não possui quantidade de pontos para comprar este benefício");
        }
    }

}