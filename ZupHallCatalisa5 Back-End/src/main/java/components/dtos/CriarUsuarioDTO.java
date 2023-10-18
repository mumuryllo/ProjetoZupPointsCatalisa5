package components.dtos;

import components.enums.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString
public class CriarUsuarioDTO {

    @NotBlank(message = "O nome do usuário não pode ser vazio")
    private String nome;

    @NotBlank(message = "O username do usuário não pode ser vazio")
    @Email(message = "Formato de e-mail está inválido!", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 6, message = "A senha deve ter o mínimo de 6 dígitos")
    private String password;

    private Role role=Role.ROLE_COLABORADOR;

}
