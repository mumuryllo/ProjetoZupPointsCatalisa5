package components.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import components.models.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BeneficioRequestDTO {
    private Long id;
}
