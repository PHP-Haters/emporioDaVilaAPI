package app.emporioDaVila.controller;

import app.emporioDaVila.dto.LoginResponseDTO;
import app.emporioDaVila.entity.Login;
import app.emporioDaVila.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<?> logar(@RequestBody Login login) {
		try {
			LoginResponseDTO resposta = loginService.logar(login);
			return ResponseEntity.ok(resposta);
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciais inválidas"));
		} catch (RuntimeException ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "Erro no login";
			if (msg.toLowerCase().contains("não encontrado") || msg.toLowerCase().contains("nao encontrado")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", msg));
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", msg));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erro interno"));
		}
	}

}
