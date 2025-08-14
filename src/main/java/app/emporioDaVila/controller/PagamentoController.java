package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) { this.pagamentoService = pagamentoService; }


    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Pagamento pagamento) {
        try {
            String result = this.pagamentoService.save(pagamento);
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Deu algo errado!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Pagamento>> findAll() {
        try {
            var result = pagamentoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Pagamento> findById(@PathVariable Integer id) {
        try {
            var result = pagamentoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pagamento> update(@PathVariable Integer id, @RequestBody Pagamento pagamentoUpdate) {
        try {
            var result = pagamentoService.update(id, pagamentoUpdate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            pagamentoService.delete(id);
            return ResponseEntity.noContent().build(); // status 204
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build(); // status 400
        }
    }
}
