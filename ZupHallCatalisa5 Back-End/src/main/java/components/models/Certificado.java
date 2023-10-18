package components.models;

import components.enums.TipoCertificado;
import components.enums.ValidarCertificado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "certificados")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Entidade de Certificado")
public class Certificado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do Certificado", example = "1")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    @Schema(description = "Nome do Certificado")
    private String nome;

    @Column(name = "numero_credencial", nullable = false, unique = true, length = 100)
    @Schema(description = "Número de Credencial do Certificado")
    private String numero_credencial;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_certificado", nullable = false, length = 25)
    @Schema(description = "Tipo de Certificado")
    private TipoCertificado tipo_certificado;

    @Column(name = "link", nullable = false)
    @Schema(description = "Link para o Certificado")
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "certificado_valido", length = 25)
    @Schema(description = "Status de Validação do Certificado", defaultValue = "PENDENTE")
    private ValidarCertificado certificado_valido = ValidarCertificado.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    @Schema(description = "Colaborador associado ao Certificado")
    private Colaborador colaborador;
}