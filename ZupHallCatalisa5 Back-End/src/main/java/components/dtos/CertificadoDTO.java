package components.dtos;

import components.enums.TipoCertificado;
import components.enums.ValidarCertificado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertificadoDTO {

    @NotBlank(message = "Nome do certificado não pode ser vazio.")
    private String nome;

    @NotBlank(message = "Número da credencial não pode ser vazio")
    private String numero_credencial;

    @NotBlank(message = "O link não pode estar vazio")
    private String link;

    private ValidarCertificado validarCertificado = ValidarCertificado.PENDENTE;

    private TipoCertificado tipoCertificado;

}
