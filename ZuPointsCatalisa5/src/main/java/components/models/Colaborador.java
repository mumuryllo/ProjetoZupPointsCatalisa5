package components.models;

import components.enums.Role;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("colaborador")
public class Colaborador extends Usuario{

    private int pontosAcumulados;

    private int pontosDoacao = 30;

    @OneToMany(mappedBy = "destinatario")
    private List<Avaliacao> avaliacoesRecebidas;

    @OneToMany(mappedBy = "remetente")
    private List<Avaliacao> avaliacoesFeitas;


    public Colaborador() {
        setRole(Role.ROLE_COLABORADOR);
    }

    // Lógica para resetar os pontos de doação a cada mês no dia 01 às 00:00
    @Scheduled(cron = "0 0 1 * * ?")
    public void resetPontosDoacao() {
        this.pontosDoacao = 30;
    }
}
