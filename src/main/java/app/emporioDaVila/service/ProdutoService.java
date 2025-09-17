package app.emporioDaVila.service;

import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public String save(Produto produto) {
        try {
            produtoRepository.save(produto);
            return "Produto salvo com sucesso";
        }
        catch (DataIntegrityViolationException ex) {
            throw new GenericExceptions.InvalidData(
                    "Dados inválidos para o produto: " + ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new GenericExceptions.General(
                    "Erro inesperado ao salvar o produto: " + ex.getMessage()
            );
        }
    }

    public List<Produto> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            throw new GenericExceptions.General(
                    "Não existem produtos cadastrados."
            );
        } else {
            return produtos;
        }
    }

    public Produto findById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new GenericExceptions.NotFound("Produto não encontrado."));
    }

    public List<Produto> findByCategoria(Categoria categoria) {
        List<Produto> produtos = produtoRepository.findAllByCategoria(categoria);
        if (produtos.isEmpty()) {
            throw new GenericExceptions.NotFound (
                    "Não existem produtos dessa categoria cadastrados."
            );
        } else {
            return produtos;
        }
    }

    public Produto update(Integer id, Produto novoProduto) {
        Produto update = findById(id);

        if (novoProduto.getCategoria() != null) {
            update.setCategoria(novoProduto.getCategoria());
        }

        if (novoProduto.getImagem() != null) {
            update.setImagem(novoProduto.getImagem());
        }

        if (novoProduto.getValor() != null) {
            update.setValor(novoProduto.getValor());
        }

        if (novoProduto.getStock() != null) {
            update.setStock(novoProduto.getStock());
        }

        if (novoProduto.getNome() != null) {
            update.setNome(novoProduto.getNome());
        }

        return produtoRepository.save(update);
    }

    public void delete(Integer id) {
        Produto delete = findById(id);
        produtoRepository.delete(delete);
    }
}
