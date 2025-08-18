package app.emporioDaVila.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido {
    @OneToMany(mappedBy = "produto_pedido")
    private List<ProdutoPedido>produtoPedidos = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O pedido deve estar atrelado a um usuário")
    private Long id_usuario;

    private Boolean estado;
}