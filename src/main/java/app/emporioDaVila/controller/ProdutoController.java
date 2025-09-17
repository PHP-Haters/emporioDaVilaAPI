package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private final ProdutoService produtoService = new  ProdutoService();

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid Produto produto) {
            String result = this.produtoService.save(produto);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        var result = produtoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id) {
            var result = produtoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> findByCategoria(@PathVariable Categoria categoria) {
        var result = produtoService.findByCategoria(categoria);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid Produto produtoUpdate) {
            produtoService.update(id, produtoUpdate);
            return new ResponseEntity<>("Produto atualizado com sucesso.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build(); // status 204
    }
}
