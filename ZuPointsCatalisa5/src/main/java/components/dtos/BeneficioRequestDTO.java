package components.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BeneficioRequestDTO {

    @NotEmpty(message = "O id não pode ser vazio")
    private Long id;

}
