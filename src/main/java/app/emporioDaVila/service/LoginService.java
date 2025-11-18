//AuthenticationService.java
package app.emporioDaVila.service;

import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.entity.Login;
import app.emporioDaVila.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import app.emporioDaVila.config.JwtServiceGenerator;

@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String logar(Login login) {
		String token = this.gerarToken(login);
		return token;
	}

	public String gerarToken(Login login) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getEmail(),
						login.getSenha()
						)
				);
		Usuario user = loginRepository.findByEmail(login.getEmail()).get();

        // Retorna o token gerado
		return jwtService.generateToken(user);
	}


}
