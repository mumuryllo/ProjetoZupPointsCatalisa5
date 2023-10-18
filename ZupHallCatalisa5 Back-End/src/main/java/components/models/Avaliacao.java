package components.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Entidade de Avaliação")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da Avaliação", example = "1")
    private Long id;

    @Schema(description = "Quantidade de Pontos da Avaliação")
    private int qtdPontos;

    @Schema(description = "Data da Avaliação")
    private LocalDate dataDaAvaliacao;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    @Schema(description = "Colaborador que envia a avaliação")
    private Colaborador remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    @Schema(description = "Colaborador que recebe a avaliação")
    private Colaborador destinatario;

    @Schema(description = "Mensagem da Avaliação")
    private String mensagem;
}