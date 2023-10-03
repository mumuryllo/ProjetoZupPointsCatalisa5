package components.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioSenhaDTO {
    @NotBlank
    @Size(min = 6,max = 6)
    private String senhaAtual;
    @NotBlank
    @Size(min = 6,max = 6)
    private String novaSenha;
    @NotBlank
    @Size(min = 6,max = 6)
    private String confirmarSenha;

}
