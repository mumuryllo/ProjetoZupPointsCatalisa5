package components.services;

import components.enums.Role;
import components.models.Usuario;
import components.repositories.UsuarioRepository;
import components.services.exceptions.UsernameNotFoundException;
import components.services.exceptions.UsernameUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException("Usuário já cadastrado",usuario.getUsername());
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarId(Long id){
        return  usuarioRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException(String.format("Não encontrado! ID - '%d'",id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmarSenha){

        if (!novaSenha.equals(confirmarSenha)){
            throw  new RuntimeException("Nova senha não condiz com confirmação de senha!");
        }

        Usuario user = buscarId(id);
        if (!passwordEncoder.matches(senhaAtual,user.getPassword())){
            throw new RuntimeException("Senha não confere!");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUserName(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(String.format("Usuario com '%s' encontrado!",username))
        );
    }

    @Transactional(readOnly = true)
    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
