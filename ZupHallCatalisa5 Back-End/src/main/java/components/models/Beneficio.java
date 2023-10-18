package components.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beneficios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidade de Benefício")
public class Beneficio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do Benefício", example = "1")
    private Long id;

    @NotNull(message = "Nome de benefício não pode ser vazio!")
    @Column(name = "nome", nullable = false)
    @Schema(description = "Nome do Benefício")
    private String nome;

    @Positive(message = "A quantidade deve ser maior que 0")
    @NotNull(message = "A quantidade de pontos não pode estar vazia")
    @Column(name = "qtdPontosParaComprar")
    @Schema(description = "Quantidade de pontos necessária para comprar o Benefício")
    private int qtdPontosParaComprar;

    @Positive(message = "A quantidade deve ser maior que 0")
    @NotNull(message = "A quantidade de pontos não pode estar vazia")
    @Schema(description = "Quantidade disponível do Benefício")
    private int qtdDisponivel;

    @Positive(message = "O valor deve ser maior que 0")
    @NotNull(message = "O valor não pode estar vazio")
    @Column(name = "valor")
    @Schema(description = "Valor do Benefício")
    private double valor;

    @Column(name = "voucher")
    @Schema(description = "Código do Voucher do Benefício")
    private String voucher;

    @Column(name = "tempoRegaste")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data de criação do Benefício")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToMany(mappedBy = "beneficios")
    @JsonIgnore
    @Schema(description = "Lista de Colaboradores que possuem o Benefício")
    private List<Colaborador> colaboradores = new ArrayList<>();
}