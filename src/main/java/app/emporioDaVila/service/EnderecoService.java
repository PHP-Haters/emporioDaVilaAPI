package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Endereco;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public String saveEndereco(Endereco novoEndereco) {
        try {
            enderecoRepository.save(novoEndereco);
            return "Endereço salvo com sucesso";
        }
        catch (DataIntegrityViolationException ex) {
            throw new GenericExceptions.InvalidData(
                    "Dados inválidos para o endereço: " + ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new GenericExceptions.General(
                    "Erro inesperado ao salvar o endereço: " + ex.getMessage()
            );
        }
    }

    public List<Endereco> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        if (enderecos.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem endereços cadastrados."
            );
        } else {
            return enderecos;
        }
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new GenericExceptions.NotFound("Endereço não encontrado."));
    }

    public Endereco update(Long id, Endereco novoEndereco) {
        Endereco update = findById(id);

        if (novoEndereco.getRua() != null) {
            update.setRua(novoEndereco.getRua());
        }
        if (novoEndereco.getNumero() != null) {
            update.setNumero(novoEndereco.getNumero());
        }
        if (novoEndereco.getCep() != null) {
            update.setCep(novoEndereco.getCep());
        }
        if (novoEndereco.getBairro() != null) {
            update.setBairro(novoEndereco.getBairro());
        }
        if (novoEndereco.getComplemento() != null) {
            update.setComplemento(novoEndereco.getComplemento());
        }
        if (novoEndereco.getUsuario() != null) {
            update.setUsuario(novoEndereco.getUsuario());
        }

        return enderecoRepository.save(update);
    }

    public void delete(Long id) {
        Endereco delete = findById(id);
        enderecoRepository.delete(delete);
    }

    public List<Endereco> findByUsuarioId(Long usuarioId) {
        return enderecoRepository.findByUsuarioId(usuarioId);
    }
}
