package components.dtos;

import components.enums.TipoCertificado;
import components.enums.ValidarCertificado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertificadoDTO {

    private String nome;
    private String numero_credencial;
    private String link;
    private ValidarCertificado validarCertificado = ValidarCertificado.PENDENTE;
    private TipoCertificado tipoCertificado;

}
