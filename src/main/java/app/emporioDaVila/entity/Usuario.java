package app.emporioDaVila.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
// 1. Implementa UserDetails
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    // O campo 'senha' será o password para o UserDetails
    private String senha;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    // O campo 'email' será o username para o UserDetails
    private String email;

    // Campo de papel/permissão que será usado pelo Spring Security
    private String role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    public Usuario(String nome, String senha, String telefone, String email, String role) {
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.role = role;
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        // Define o papel com base no campo 'role'
        if(this.role == null) {
            this.role = "ROLE_USER";
        }
    }

    // --- Métodos da Interface UserDetails ---

    /**
     * Retorna a coleção de autoridades (permissões/roles) concedidas ao usuário.
     * @return Lista de autoridades.
     */
    @Override
    @JsonIgnore // Evita que este método seja serializado no JSON de resposta, pois não faz parte do modelo de dados.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Usa o campo 'role' (definido em prePersist/preUpdate)
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
    }

    /**
     * Retorna a senha usada para autenticar o usuário.
     * @return A senha (o campo 'senha' da sua entidade).
     */
    @Override
    public String getPassword() {
        return this.senha;
    }

    /**
     * Retorna o nome de usuário (identificador) usado para autenticar o usuário.
     * @return O nome de usuário (o campo 'email' da sua entidade).
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    // --- Métodos de Status da Conta (Deixados como true por padrão) ---

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}