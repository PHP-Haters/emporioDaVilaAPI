package app.emporioDaVila.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido {

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pedido") // ignora o campo "pedido" dentro de ProdutoPedido
    private List<ProdutoPedido> produtoPedidos = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pedido") // ignora o campo "pedido" dentro de PagamentoPedido
    private List<PagamentoPedido> pagamentoPedidos = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O pedido deve estar atrelado a um usuário")
    private Long idUsuario;

    private Boolean estado;

    @PrePersist
    public void prePersist() {
        if (estado == null) {
            estado = true;
        }
    }
}
