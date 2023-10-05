package components.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Beneficio implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Nome de beneficio não pode ser vazio!")
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "qtdPontosParaComprar")
    private int qtdPontosParaComprar;
    private int qtdDisponivel;
    @Column(name = "valor")
    private double valor;
    @Column(name = "voucher")
    private String voucher;

    @Column(name = "tempoRegaste")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime tempoRegaste = LocalDateTime.now();

    @ManyToMany(mappedBy = "beneficios")
    @JsonIgnore
    private List<Colaborador> colaboradores = new ArrayList<>();

}
