package components.models;

import components.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Colaborador extends Usuario{

    private int pontosAcumulados;

    private int pontosDoacao = 30;

    @OneToMany(mappedBy = "destinatario")
    private List<Avaliacao> avaliacoesRecebidas;

    @OneToMany(mappedBy = "remetente")
    private List<Avaliacao> avaliacoesFeitas;

    @ManyToMany
    @JoinTable(
            name = "colaborador_beneficio",
            joinColumns = @JoinColumn(name = "colaborador_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficio_id")
    )
    private List<Beneficio> beneficios = new ArrayList<>();


    public Colaborador() {
        setRole(Role.ROLE_COLABORADOR);
    }

    // Lógica para resetar os pontos de doação a cada mês no dia 01 às 00:00
    @Scheduled(cron = "0 0 1 * * ?")
    public void resetPontosDoacao() {
        this.pontosDoacao = 30;
    }
}
