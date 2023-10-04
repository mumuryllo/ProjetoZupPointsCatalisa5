package components.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoResponseDTO {
    private int qtdPontos;
    private String dataDaAvaliacao;
    private String remetenteEmail;
    private String destinatarioEmail;
    private String mensagem;
}
