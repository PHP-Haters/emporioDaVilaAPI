package app.emporioDaVila.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "1.0", message = "Valor mínimo é 1 real")
    private Float valor;

    private Boolean stock;

    private String imagem;

    @NotBlank(message = "Produto precisa de uma categoria")
    private String categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoPedido> produtosPedidos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (stock == null) {
            stock = true;
        }
    }
}
