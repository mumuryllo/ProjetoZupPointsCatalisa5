package components.models;

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
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private int qtdPontos;
    private LocalDate dataDaAvaliacao;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Colaborador remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Colaborador destinatario;

    private String mensagem;
}
