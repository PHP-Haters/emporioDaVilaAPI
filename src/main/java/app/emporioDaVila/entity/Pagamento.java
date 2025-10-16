package app.emporioDaVila.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tipo de pagamento é obrigatório")
    private String tipo;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade mínima é 1")
    private Integer quantidade;

    private Boolean finalizado;

    @OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pagamento") // ignora o campo "pedido" dentro de PagamentoPedido
    private List<PagamentoPedido> pagamentoPedidos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (finalizado == null) {
            finalizado = false;
        }
    }
}
