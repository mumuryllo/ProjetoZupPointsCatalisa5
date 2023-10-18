package components.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import components.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Schema(description = "Entidade de Colaborador")
public class Colaborador extends Usuario {

    @Schema(description = "Pontos acumulados pelo Colaborador", example = "100")
    private int pontosAcumulados;

    @Schema(description = "Pontos de doação do Colaborador", example = "30")
    private int pontosDoacao = 30;

    @OneToMany(mappedBy = "destinatario")
    @JsonIgnore
    @Schema(description = "Lista de avaliações recebidas pelo Colaborador")
    private List<Avaliacao> avaliacoesRecebidas;

    @OneToMany(mappedBy = "remetente")
    @JsonIgnore
    @Schema(description = "Lista de avaliações feitas pelo Colaborador")
    private List<Avaliacao> avaliacoesFeitas;

    @OneToMany(mappedBy = "colaborador")
    @JsonIgnore
    @Schema(description = "Lista de certificados enviados pelo Colaborador")
    private List<Certificado> certificadosEnviados;

    @ManyToMany
    @JoinTable(
            name = "colaborador_beneficio",
            joinColumns = @JoinColumn(name = "colaborador_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficio_id")
    )
    @Schema(description = "Lista de Benefícios do Colaborador")
    private List<Beneficio> beneficios = new ArrayList<>();

    public Colaborador() {
        setRole(Role.ROLE_COLABORADOR);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void resetPontosDoacao() {
        this.pontosDoacao = 30;
    }
}