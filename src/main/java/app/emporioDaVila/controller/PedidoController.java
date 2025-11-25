package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Pedido;
import app.emporioDaVila.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
public class PedidoController {

    @Autowired
    private  PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid Pedido pedido) {
        String result = this.pedidoService.save(pedido);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        var result = pedidoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
        var result = pedidoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid Pedido pedidoUpdate) {
        pedidoService.updateState(id, pedidoUpdate);
        return new ResponseEntity<>("Pedido atualizado com sucesso.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build(); // status 204
    }
}
