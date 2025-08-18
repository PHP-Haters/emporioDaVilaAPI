package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Endereco;
import app.emporioDaVila.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Criar um novo endereço
    @PostMapping
    public ResponseEntity<String> saveEndereco(@RequestBody @Valid Endereco endereco){
        try {
            String response = enderecoService.saveEndereco(endereco);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu algo errado!", HttpStatus.BAD_REQUEST);
        }
    }

    // Listar todos os endereços
    @GetMapping
    public ResponseEntity<List<Endereco>> findAll() {
        try {
            List<Endereco> result = enderecoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        try {
            Endereco result = enderecoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid Endereco enderecoAtualizado) {
        try {
            enderecoService.update(id, enderecoAtualizado);
            return new ResponseEntity<>("Endereço atualizado com sucesso.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Deu algo errado!", HttpStatus.BAD_REQUEST);
        }
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            enderecoService.delete(id);
            return ResponseEntity.noContent().build(); // status 204
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build(); // status 400
        }
    }

    // Listar todos os endereços de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Endereco>> findByUsuarioId(@PathVariable Long usuarioId) {
        try {
            List<Endereco> result = enderecoService.findByUsuarioId(usuarioId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
