package app.emporioDaVila.service;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String saveUsuario(Usuario novoUsuario){
        this.usuarioRepository.save(novoUsuario);
        return "Usuário salvo com sucesso.";
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Usuario update(Long id, Usuario novoUsuario) {
        Usuario update = findById(id);

        if (novoUsuario.getNome() != null) {
            update.setNome(novoUsuario.getNome());
        }

        if (novoUsuario.getSenha() != null) {
            update.setSenha(novoUsuario.getSenha());
        }

        if (novoUsuario.getTelefone() != null) {
            update.setTelefone(novoUsuario.getTelefone());
        }

        if (novoUsuario.getEmail() != null) {
            update.setEmail(novoUsuario.getEmail());
        }

        return usuarioRepository.save(update);
    }

    public void delete(Long id) {
        Usuario delete = findById(id);
        usuarioRepository.delete(delete);
    }
}
