package app.emporioDaVila.entity;

import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    @OneToMany(mappedBy = "produto_pedido")
    private List<ProdutoPedido>produtoPedidos = new ArrayList<>();
}