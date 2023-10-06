package components.services;

import components.dtos.AvaliacaoResponseDTO;
import components.dtos.CriarAvaliacaoDTO;
import components.models.Avaliacao;
import components.models.Colaborador;
import components.repositories.AvaliacaoRepository;
import components.repositories.ColaboradorRepository;
import components.services.exceptions.AvaliacaoInvalidaException;
import components.services.exceptions.ColaboradorNaoLogadoException;
import components.services.exceptions.ColaboradorNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<AvaliacaoResponseDTO> listarAvaliacaoRecebidas() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String destinatarioEmail = userDetails.getUsername();

            Colaborador destinatario = colaboradorRepository.findByUsername(destinatarioEmail)
                    .orElseThrow(() -> new ColaboradorNaoEncontradoException("Colaborador destinatário não encontrado"));

            List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByDestinatario_Id(destinatario.getId());
            List<AvaliacaoResponseDTO> avaliacaoResponseDTOList = new ArrayList<>();
            for (Avaliacao avaliacao : avaliacoes) {
                AvaliacaoResponseDTO avaliacaoResponseDTO = new AvaliacaoResponseDTO();
                avaliacaoResponseDTO.setDataDaAvaliacao(avaliacao.getDataDaAvaliacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                avaliacaoResponseDTO.setMensagem(avaliacao.getMensagem());
                avaliacaoResponseDTO.setDestinatarioEmail(avaliacao.getDestinatario().getUsername());
                avaliacaoResponseDTO.setRemetenteEmail(avaliacao.getRemetente().getUsername());
                avaliacaoResponseDTO.setQtdPontos(avaliacao.getQtdPontos());
                avaliacaoResponseDTOList.add(avaliacaoResponseDTO);
            }
            return avaliacaoResponseDTOList;

        } else {
            throw new ColaboradorNaoLogadoException("Colaborador não está logado");
        }
    }

    public AvaliacaoResponseDTO criarAvaliacao(CriarAvaliacaoDTO criarAvaliacaoDTO) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String remetenteUsername = userDetails.getUsername();

            Colaborador remetente = colaboradorRepository.findByUsername(remetenteUsername)
                    .orElseThrow(() -> new ColaboradorNaoEncontradoException("Colaborador remetente não encontrado"));

            Colaborador destinatario = colaboradorRepository.findByUsername(criarAvaliacaoDTO.getDestinatarioEmail())
                    .orElseThrow(() -> new ColaboradorNaoEncontradoException("Colaborador destinatário não encontrado"));

            validarAvaliacao(remetente, destinatario, criarAvaliacaoDTO);

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setDataDaAvaliacao(LocalDate.now());
            avaliacao.setQtdPontos(criarAvaliacaoDTO.getQtdPontos());
            avaliacao.setMensagem(criarAvaliacaoDTO.getMensagem());
            avaliacao.setRemetente(remetente);
            avaliacao.setDestinatario(destinatario);

            avaliacaoRepository.save(avaliacao);

            AvaliacaoResponseDTO avaliacaoResponseDTO = new AvaliacaoResponseDTO();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            avaliacaoResponseDTO.setDataDaAvaliacao(avaliacao.getDataDaAvaliacao().format(formatter));
            avaliacaoResponseDTO.setMensagem(avaliacao.getMensagem());
            avaliacaoResponseDTO.setDestinatarioEmail(avaliacao.getDestinatario().getUsername());
            avaliacaoResponseDTO.setRemetenteEmail(avaliacao.getRemetente().getUsername());
            avaliacaoResponseDTO.setQtdPontos(avaliacao.getQtdPontos());

            // Salvando novas pontuações dos colaboradores
            distribuirPontosAvaliacao(remetente, destinatario, criarAvaliacaoDTO);
            colaboradorRepository.save(remetente);
            colaboradorRepository.save(destinatario);

            return avaliacaoResponseDTO;
        } else {
            throw new ColaboradorNaoLogadoException("Colaborador não está logado");
        }
    }

    public void validarAvaliacao(Colaborador remetente, Colaborador destinatario, CriarAvaliacaoDTO avaliacaoDTO){
        if (remetente.getUsername().equalsIgnoreCase(destinatario.getUsername())){
            throw new AvaliacaoInvalidaException("Um colaborador não pode se auto-avaliar");
        }
        else if (remetente.getPontosDoacao() < avaliacaoDTO.getQtdPontos()){
            throw new AvaliacaoInvalidaException(String.format("Quantidade de pontos para doar ( %d ) é maior que a quantidade de pontos disponíveis ( %d )",
                    avaliacaoDTO.getQtdPontos(), remetente.getPontosDoacao()));
        }
        else if (avaliacaoDTO.getQtdPontos() > 10) {
            throw new AvaliacaoInvalidaException("Quantidade de pontos maior que a quantidade máxima permitida ( 10 )");
        }
    }

    public void distribuirPontosAvaliacao(Colaborador remetente, Colaborador destinatario, CriarAvaliacaoDTO avaliacaoDTO){
        remetente.setPontosDoacao(remetente.getPontosDoacao() - avaliacaoDTO.getQtdPontos());
        destinatario.setPontosAcumulados(destinatario.getPontosAcumulados() + avaliacaoDTO.getQtdPontos());
    }
}
