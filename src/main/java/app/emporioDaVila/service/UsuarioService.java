package app.emporioDaVila.service;

import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String saveUsuario(Usuario novoUsuario){
        this.usuarioRepository.save(novoUsuario);
        return "Usuário salvo com sucesso.";
    }
}
