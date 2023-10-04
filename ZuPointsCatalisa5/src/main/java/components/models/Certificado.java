package components.models;

import components.enums.TipoCertificado;
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

    @Column(name = "quantidadePontosCertificado", nullable = false)
    private int quantidadePontosCertificado;




}
