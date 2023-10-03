package components.dtos;

import components.enums.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString
public class CriarUsuarioDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email(message = "Formato de e-mail está inválido!")
    private String username;

    @NotBlank
    @Size(min = 6,max = 6)
    private String password;

    private Role role=Role.ROLE_COLABORADOR;

}
