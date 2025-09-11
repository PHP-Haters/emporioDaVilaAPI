package app.emporioDaVila.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "produto")
public class Produto {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Include
    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @ToString.Include
    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "1.0", message = "Valor mínimo é 1 real")
    private Float valor;

    @ToString.Include
    private Boolean stock;

    @ToString.Include
    private String imagem;

    @ToString.Include
    @NotBlank(message = "Produto precisa de uma categoria")
    private String categoria;

    @ToString.Exclude
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("produto") // ignora o campo "produto" dentro de ProdutoPedido
    private List<ProdutoPedido> produtosPedidos = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (stock == null) {
            stock = true;
        }
    }

    // Optional: expose a lightweight computed field without touching the collection
    // If you REALLY want to show something, do this instead of listing items:
     @ToString.Include(name = "produtosPedidosCount")
     int produtosPedidosCount() { return (produtosPedidos == null) ? 0 : produtosPedidos.size(); };
}
