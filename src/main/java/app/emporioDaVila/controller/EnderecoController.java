package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Endereco;
import app.emporioDaVila.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Criar um novo endereço
    @PostMapping
    public ResponseEntity<String> saveEndereco(@RequestBody @Valid Endereco endereco){
        String response = enderecoService.saveEndereco(endereco);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Listar todos os endereços
    @GetMapping
    public ResponseEntity<List<Endereco>> findAll() {
        List<Endereco> result = enderecoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        Endereco result = enderecoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid Endereco enderecoAtualizado) {
        enderecoService.update(id, enderecoAtualizado);
        return new ResponseEntity<>("Endereço atualizado com sucesso.", HttpStatus.OK);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build(); // status 204
    }

    // Listar todos os endereços de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Endereco>> findByUsuarioId(@PathVariable Long usuarioId) {
        List<Endereco> result = enderecoService.findByUsuarioId(usuarioId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
