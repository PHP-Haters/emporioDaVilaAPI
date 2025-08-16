package app.emporioDaVila.service;

import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public String save(Produto produto) {
        this.produtoRepository.save(produto);
        return "Produto salvo com sucesso";
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
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
