package components.services;

import components.models.Beneficio;
import components.repositories.BeneficioRepository;
import components.services.exceptions.BeneficioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    public Beneficio criarBeneficio(Beneficio beneficio) {
        int pontos = calcularPontos(beneficio);
        beneficio.setQtdPontosParaComprar(pontos);

        beneficio.setVoucher(gerarVoucher());

        definirTempoDeResgate(beneficio);

        return beneficioRepository.save(beneficio);
    }

    public List<Beneficio> listarTodosBeneficios() {
        return beneficioRepository.findAll();
    }

    public Optional<Beneficio> listarBeneficioPorId(Long id) {
        return beneficioRepository.findById(id);
    }

    private int calcularPontos(Beneficio beneficio) throws BeneficioException{
        double valor = beneficio.getValor();
        String nome = beneficio.getNome();

        int pontos = beneficio.getQtdPontosParaComprar();

        if ("Netflix".equalsIgnoreCase(nome)) {
            pontos = 30;
            beneficio.setValor(50);
        } else if ("Uber".equalsIgnoreCase(nome) ) {
            pontos = 30;
            beneficio.setValor(50);
        } else if ("Americanas".equalsIgnoreCase(nome)) {
            pontos = 20;
            beneficio.setValor(30);
        } else if ("Ifood".equalsIgnoreCase(nome)) {
            pontos = 50;
            beneficio.setValor(100);
        } else if ("Amazon".equalsIgnoreCase(nome)) {
            pontos = 75;
            beneficio.setValor(60);
        } else if ("Udemy".equalsIgnoreCase(nome)) {
            pontos = 40;
            beneficio.setValor(15);
        } else if ("Alura".equalsIgnoreCase(nome)) {
            pontos =120;
            beneficio.setValor(100);
        }else {
            throw new BeneficioException("Nome de beneficio inválido!");
        }
        return pontos;
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

        beneficio.setTempoRegaste(dataDeExpiracao);
    }

}
