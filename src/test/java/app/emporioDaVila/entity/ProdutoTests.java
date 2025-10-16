package app.emporioDaVila.entity;

import app.emporioDaVila.entity.Enum.Categoria;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ProdutoTests {

    // TESTES DO prePersist

    @Test
    void prePersist_cenario01() {
        // Estoque recebido como nulo, mas deve ser persistido como true

        Produto produto = new Produto();
        produto.setStock(null);

        produto.prePersist();

        assertTrue(produto.getStock());
    }

    @Test
    void prePersist_cenario02_stockTrue_naoDeveAlterar() {
        // Estoque deve ser persistido como true

        Produto produto = new Produto();
        produto.setStock(true);

        produto.prePersist();

        assertTrue(produto.getStock());
    }

    @Test
    void prePersist_cenario03() {
        // Estoque deve ser persistido como false

        Produto produto = new Produto();
        produto.setStock(false);

        produto.prePersist();

        assertFalse(produto.getStock());
    }

    // TESTES DO produtosPedidosCount

    @Test
    void produtosPedidosCount_cenario01() {
        // Espera-se que o retorno de produtosPedidosCount seja zero

        Produto produto = new Produto();
        produto.setProdutosPedidos(new ArrayList<>());

        int count = produto.produtosPedidosCount();

        assertEquals(0, count);
    }

    @Test
    void produtosPedidosCount_cenario02() {
        // Espera-se que o retorno de produtosPedidosCount seja diferente de zero
        Produto produto = new Produto();

        ProdutoPedido pp1 = new ProdutoPedido();
        ProdutoPedido pp2 = new ProdutoPedido();

        // adiciona os produtosPedidos
        produto.getProdutosPedidos().add(pp1);
        produto.getProdutosPedidos().add(pp2);

        int count = produto.produtosPedidosCount();

        assertEquals(2, count);
    }

    @Test
    void produtosPedidosCount_cenario03(){
        // Enviando lista nula, espera-se retorno igual a zero

        Produto produto = new Produto();
        produto.setProdutosPedidos(null);

        int count = produto.produtosPedidosCount();

        assertEquals(0, count);
    }
}