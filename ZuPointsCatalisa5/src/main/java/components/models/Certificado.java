package components.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import components.enums.TipoCertificado;
import components.enums.ValidarCertificado;
import lombok.*;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import java.io.Serializable;

@Table (name = "certificados")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Certificado implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "numero_credencial", nullable = false, unique = true, length = 100)
    private String numero_credencial;

    @Enumerated(EnumType.STRING)
    @Column(name= "tipo_certificado", nullable = false, length = 25)
    private TipoCertificado tipo_certificado;

    @Column(name = "link",nullable = false)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name= "certificado_valido", length = 25)
    private ValidarCertificado certificado_valido = ValidarCertificado.PENDENTE;

    @Column(name = "quantidadePontosCertificado", nullable = false)
    private int quantidadePontosCertificado;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

}
