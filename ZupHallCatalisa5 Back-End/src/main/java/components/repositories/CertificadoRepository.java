package components.repositories;

import components.models.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificadoRepository extends JpaRepository <Certificado, Long> {

}

