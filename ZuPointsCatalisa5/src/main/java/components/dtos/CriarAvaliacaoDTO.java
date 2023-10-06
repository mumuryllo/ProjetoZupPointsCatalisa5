package components.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarAvaliacaoDTO {
    @Positive(message = "A quantidade deve ser maior que 0")
    @NotEmpty(message = "A quantidade de pontos não pode estar vazia")
    private int qtdPontos;

    @Email(message = "Digite um email válido. Exemplo: destinatario@example.com", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @NotEmpty(message = "Email não pode estar vazio.")
    private String destinatarioEmail;

    @NotBlank(message = "A mensagem não pode estar vazia")
    private String mensagem;
}
