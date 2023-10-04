package components.services;

import components.dtos.AvaliacaoResponseDTO;
import components.dtos.CriarAvaliacaoDTO;
import components.models.Avaliacao;
import components.models.Colaborador;
import components.models.Usuario;
import components.repositories.AvaliacaoRepository;
import components.repositories.ColaboradorRepository;
import components.services.exceptions.ColaboradorNaoLogadoException;
import components.services.exceptions.ColaboradorNotFoundException;
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
                    .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador destinatário não encontrado"));

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
                    .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador remetente não encontrado"));

            Colaborador destinatario = colaboradorRepository.findByUsername(criarAvaliacaoDTO.getDestinatarioEmail())
                    .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador destinatário não encontrado"));

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
            return avaliacaoResponseDTO;
        } else {
            throw new ColaboradorNaoLogadoException("Colaborador não está logado");
        }
    }
}
