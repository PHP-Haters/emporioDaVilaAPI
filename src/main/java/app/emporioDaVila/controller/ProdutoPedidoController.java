package app.emporioDaVila.controller;

import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.service.ProdutoPedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/produtoPedidoPedido")
public class ProdutoPedidoController {

    @Autowired
    private final ProdutoPedidoService produtoPedidoService = new ProdutoPedidoService();

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid ProdutoPedido produtoPedido) {
        String result = this.produtoPedidoService.save(produtoPedido);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoPedido>> findAll() {
            var result = produtoPedidoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoPedido> findById(@PathVariable Integer id) {
        var result = produtoPedidoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid ProdutoPedido produtoPedidoUpdate) {
        produtoPedidoService.update(id, produtoPedidoUpdate);
        return new ResponseEntity<>("ProdutoPedido atualizado com sucesso.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        produtoPedidoService.delete(id);
        return ResponseEntity.noContent().build(); // status 204
    }
}
