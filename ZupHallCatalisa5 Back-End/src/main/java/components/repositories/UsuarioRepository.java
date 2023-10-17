package components.repositories;

import components.enums.Role;
import components.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    Optional<Usuario> findByUsername(String username);

    @Query("select u.role from Usuario u where u.username like :username")
    Role findRoleByUsername(String username);
}
