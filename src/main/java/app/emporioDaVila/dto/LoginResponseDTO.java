package app.emporioDaVila.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private boolean isAdmin;
    private String role;
    private String token;
}
