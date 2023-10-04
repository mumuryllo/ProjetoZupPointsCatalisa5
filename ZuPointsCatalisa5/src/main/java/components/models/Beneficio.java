package components.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    @Column(name = "valor")
    private double valor;
    @Column(name = "voucher")
    private String voucher;

    @Column(name = "tempoRegaste")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tempoRegaste = LocalDateTime.now();

}
