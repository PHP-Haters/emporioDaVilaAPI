package app.emporioDaVila.dataLoader;

import app.emporioDaVila.entity.Enum.Categoria;
import app.emporioDaVila.entity.Produto;
import app.emporioDaVila.service.ProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutosDataLoader {

    @Bean
    CommandLineRunner initDatabase(ProdutoService produtoService) {
        return args -> {
            Produto p1 = new Produto();
            p1.setNome("Amendoim");
            p1.setValor(22.5F);
            p1.setStock(true);
            p1.setCategoria(Categoria.GRAOS);
            produtoService.save(p1);

            Produto p2 = new Produto();
            p2.setNome("Maçã");
            p2.setValor(5.0F);
            p2.setStock(true);
            p2.setCategoria(Categoria.FRUTAS);
            produtoService.save(p2);

            Produto p3 = new Produto();
            p3.setNome("Banana");
            p3.setValor(4.2F);
            p3.setStock(true);
            p3.setCategoria(Categoria.FRUTAS);
            produtoService.save(p3);

            Produto p4 = new Produto();
            p4.setNome("Cenoura");
            p4.setValor(3.5F);
            p4.setStock(true);
            p4.setCategoria(Categoria.VERDURAS);
            produtoService.save(p4);

            Produto p5 = new Produto();
            p5.setNome("Alface");
            p5.setValor(2.8F);
            p5.setStock(true);
            p5.setCategoria(Categoria.VERDURAS);
            produtoService.save(p5);

            Produto p6 = new Produto();
            p6.setNome("Chá Verde");
            p6.setValor(15.0F);
            p6.setStock(true);
            p6.setCategoria(Categoria.CHAS);
            produtoService.save(p6);

            Produto p7 = new Produto();
            p7.setNome("Camomila");
            p7.setValor(12.0F);
            p7.setStock(true);
            p7.setCategoria(Categoria.CHAS);
            produtoService.save(p7);

            Produto p8 = new Produto();
            p8.setNome("Pimenta-do-reino");
            p8.setValor(8.5F);
            p8.setStock(true);
            p8.setCategoria(Categoria.ESPECIARIAS);
            produtoService.save(p8);

            Produto p9 = new Produto();
            p9.setNome("Cúrcuma");
            p9.setValor(9.0F);
            p9.setStock(true);
            p9.setCategoria(Categoria.ESPECIARIAS);
            produtoService.save(p9);

            Produto p10 = new Produto();
            p10.setNome("Whey Protein");
            p10.setValor(120.0F);
            p10.setStock(true);
            p10.setCategoria(Categoria.SUPLEMENTOS);
            produtoService.save(p10);

            Produto p11 = new Produto();
            p11.setNome("Creatina");
            p11.setValor(95.0F);
            p11.setStock(true);
            p11.setCategoria(Categoria.SUPLEMENTOS);
            produtoService.save(p11);

            Produto p12 = new Produto();
            p12.setNome("BCAA");
            p12.setValor(80.0F);
            p12.setStock(true);
            p12.setCategoria(Categoria.SUPLEMENTOS);
            produtoService.save(p12);
        };
    }
}
