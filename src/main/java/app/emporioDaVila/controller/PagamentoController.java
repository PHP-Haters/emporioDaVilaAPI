package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private final PagamentoService pagamentoService = new  PagamentoService();

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid Pagamento pagamento) {
        String result = this.pagamentoService.save(pagamento);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> findAll() {
        var result = pagamentoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> findById(@PathVariable Integer id) {
        var result = pagamentoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid Pagamento pagamentoUpdate) {
        pagamentoService.update(id, pagamentoUpdate);
        return new ResponseEntity<>("Pagamento atualizado com sucesso.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build(); // status 204
    }
}
