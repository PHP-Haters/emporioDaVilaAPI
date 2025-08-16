package app.emporioDaVila.controller;

import app.emporioDaVila.entity.ProdutoPedido;
import app.emporioDaVila.service.ProdutoPedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtoPedidoPedido")
public class ProdutoPedidoController {

    @Autowired
    private ProdutoPedidoService produtoPedidoService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid ProdutoPedido produtoPedido) {
        try {
            String result = this.produtoPedidoService.save(produtoPedido);
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Deu algo errado!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProdutoPedido>> findAll() {
        try {
            var result = produtoPedidoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoPedido> findById(@PathVariable Integer id) {
        try {
            var result = produtoPedidoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid ProdutoPedido produtoPedidoUpdate) {
        try {
            produtoPedidoService.update(id, produtoPedidoUpdate);
            return new ResponseEntity<>("ProdutoPedido atualizado com sucesso.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            produtoPedidoService.delete(id);
            return ResponseEntity.noContent().build(); // status 204
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build(); // status 400
        }
    }
}
