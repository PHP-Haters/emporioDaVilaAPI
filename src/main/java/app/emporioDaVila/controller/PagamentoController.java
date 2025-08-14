package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Pagamento;
import app.emporioDaVila.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) { this.pagamentoService = pagamentoService; }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Pagamento> findById(@PathVariable Integer id) {
        try {
            var result = pagamentoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
