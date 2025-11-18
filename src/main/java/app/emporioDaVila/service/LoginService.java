//AuthenticationService.java
package app.emporioDaVila.service;

import app.emporioDaVila.dto.LoginResponseDTO;
import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.entity.Login;
import app.emporioDaVila.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import app.emporioDaVila.service.UsuarioService;

import app.emporioDaVila.config.JwtServiceGenerator;

@Service
public class LoginService {

	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsuarioService usuarioService;

	public LoginResponseDTO logar(Login login) {
		return gerarToken(login);
	}

	public LoginResponseDTO gerarToken(Login login) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getEmail(),
						login.getSenha()
				)
		);

		Usuario user = usuarioService.findByEmail(login.getEmail());

		String token = jwtService.generateToken(user);

		return new LoginResponseDTO(
				user.getId(),
				user.getNome(),
				user.getEmail(),
				user.getTelefone(),
				user.getAdmin(),
				user.getRole() != null ? user.getRole().toString() : null,
				token
		);
	}
}
