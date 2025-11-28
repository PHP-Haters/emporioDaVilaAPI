package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String saveUsuario(Usuario novoUsuario){
        try {
            // criptografar senha
            String hashed = passwordEncoder.encode(novoUsuario.getSenha());
            novoUsuario.setSenha(hashed);

            this.usuarioRepository.save(novoUsuario);
            return "Usuário salvo com sucesso.";
        }
        catch (DataIntegrityViolationException ex) {
            throw new GenericExceptions.InvalidData(
                    "Dados inválidos para o usuário: " + ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new GenericExceptions.General(
                    "Erro inesperado ao salvar usuário: " + ex.getMessage()
            );
        }
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem usuarios cadastrados."
            );
        } else {
            return usuarios;
        }
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new GenericExceptions.NotFound("Usuário não encontrado."));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new GenericExceptions.NotFound("Usuário não encontrado."));
    }

    public Usuario update(Long id, Usuario novoUsuario) {
        Usuario update = findById(id);

        if (novoUsuario.getNome() != null) {
            update.setNome(novoUsuario.getNome());
        }

        if (novoUsuario.getSenha() != null) {
            // recript se a senha for atualizada
            update.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));
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
    public Usuario login(Usuario usuario) {
        Usuario foundUser = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> new GenericExceptions.NotFound("Usuário não encontrado."));

        // verifica senha
        boolean senhaCorreta = passwordEncoder.matches(usuario.getSenha(), foundUser.getSenha());

        if (foundUser.getSenha().equals(usuario.getSenha())) {
            foundUser.setSenha(null);
            foundUser.setTelefone(null);
            foundUser.setEmail(null);
            foundUser.setNome(null);
            return foundUser;
        }
        else {
            throw new GenericExceptions.Unauthorized("Senha errada.");
        }
    }
}
