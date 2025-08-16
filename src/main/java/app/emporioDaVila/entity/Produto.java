package app.emporioDaVila.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "O valor é obrigatório")
    @Min(value = 1, message = "Valor mínimo é 1 real")
    private Float valor;

    private Boolean stock;

    @NotBlank(message = "Produto precisa de uma imagem")
    private String imagem;

    @NotBlank(message = "Produto precisa de uma categoria")
    private String categoria;

    @OneToMany(mappedBy = "produto_pedido")
    private List<ProdutoPedido> produtosPedidos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (stock == null) {
            stock = true;
        }
    }
}
