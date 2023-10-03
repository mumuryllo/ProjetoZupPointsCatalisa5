package components.dtos;

import components.models.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarAvaliacaoDTO {
    private int qtdPontos;
    private String destinatarioEmail;
    private String mensagem;
}
