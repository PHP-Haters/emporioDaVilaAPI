package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid Pagamento pagamento) {
        try {
            String result = this.pagamentoService.save(pagamento);
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Deu algo errado!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> findAll() {
        try {
            var result = pagamentoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> findById(@PathVariable Integer id) {
        try {
            var result = pagamentoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid Pagamento pagamentoUpdate) {
        try {
            var result = pagamentoService.update(id, pagamentoUpdate);
            return new ResponseEntity<>("Pagamento atualizado com sucesso.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            pagamentoService.delete(id);
            return ResponseEntity.noContent().build(); // status 204
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build(); // status 400
        }
    }
}
