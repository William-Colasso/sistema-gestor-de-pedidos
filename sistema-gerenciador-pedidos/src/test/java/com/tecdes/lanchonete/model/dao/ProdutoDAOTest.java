package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;

@DisplayName("Testes de Integração para ProdutoDAO")
public class ProdutoDAOTest {

    private ProdutoDAO produtoDAO;

    @BeforeEach
    void setup() throws Exception {
        produtoDAO = new ProdutoDAO();

        try (Connection conn = ConnectionFactory.getConnection()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar produto corretamente")
    void deveCriarProduto() {
        // Arrange
        Produto produto = criarProduto("Produto Teste", 25.0, 2);
        System.out.println("Produto antes do DAO.create -> ID: " + produto.getId() + ", Nome: " + produto.getNome());

        // Act
        produtoDAO.create(produto);
        System.out.println("Produto após DAO.create -> ID: " + produto.getId());

        Produto criado = produtoDAO.getById(produto.getId());
        System.out.println("Produto buscado do banco -> " + (criado != null ? "Nome: " + criado.getNome() + ", ID: " + criado.getId() : "null"));

        // Assert
        verificarProduto(produto, criado);
    }


    @Test
    @DisplayName("Não deve criar produto com campos obrigatórios nulos")
    void naoDeveCriarProdutoComCamposNulos() {
        Produto categoriaNula = criarProduto("Produto", 25.0, 2);
        categoriaNula.setCategoria(null);

        assertThrows(RuntimeException.class, () -> produtoDAO.create(categoriaNula), "Categoria nula deve gerar exceção");
    }

    @Test
    @DisplayName("Deve atualizar produto existente")
    void deveAtualizarProduto() {
        Produto produto = criarProduto("Produto Antigo", 10.0, 1);
        produtoDAO.create(produto);

        // Act
        produto.setValor(50.0);
        produtoDAO.update(produto);

        // Assert
        Produto atualizado = produtoDAO.getById(produto.getId());
        assertEquals(50.0, atualizado.getValor());
    }

    @Test
    @DisplayName("Deve deletar produto existente")
    void deveDeletarProduto() {
        Produto produto = criarProduto("Produto Delete", 10.0, 1);
        produtoDAO.create(produto);

        // Act
        produtoDAO.delete(produto.getId());
        List<Produto> todos = produtoDAO.getAll();

        // Assert
        assertEquals(0, todos.size(), "Todos os produtos devem ser removidos");
    }

    @Test
    @DisplayName("Deve buscar todos os produtos corretamente")
    void deveBuscarTodosProdutos() {
        Produto p1 = criarProduto("Produto 1", 10.0, 2);
        Produto p2 = criarProduto("Produto 2", 20.0, 3);

        produtoDAO.create(p1);
        produtoDAO.create(p2);

        List<Produto> esperado = Arrays.asList(p1, p2);
        List<Produto> atual = produtoDAO.getAll();
        esperado.sort(Comparator.comparing(Produto::getId));
        atual.sort(Comparator.comparing(Produto::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarProduto(esperado.get(i), atual.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Produto criarProduto(String nome, double valor, int quantidade) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao("Descrição " + nome);
        p.setValor(valor);
        p.setQuantidade(quantidade);
        p.setTipoItem(TipoItem.PRODUTO);
        p.setDataCriacao(Date.valueOf("2025-10-10"));
        p.setCategoria(mapCategoria());

        // Criar combos associados
        Combo c1 = criarCombo("Combo 1");
        Combo c2 = criarCombo("Combo 2");
        List<Combo> combos = Arrays.asList(c1, c2);
        p.setCombos(combos);
        salvarItemBase(p); // Persistir item base antes do produto
        
        return p;
    }

    private CategoriaProduto mapCategoria() {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome("Categoria Teste");
        categoria.setSigla("CT");
        categoria.setImagem("midia-teste".getBytes());
        salvarCategoriaNoBanco(categoria);
        return categoria;
    }

    private Combo criarCombo(String nome) {
        Combo c = new Combo();
        c.setNome(nome);
        c.setDescricao("Descrição " + nome);
        c.setDesconto(10);
        c.setTipoItem(TipoItem.COMBO);
        c.setQuantidade(1);
        c.setDataCriacao(Date.valueOf("2025-11-11"));
        salvarItemBase(c); // Persistir item base antes do combo
        salvarComboNoBanco(c);
        return c;
    }

    private void salvarItemBase(Item item) {
        String sql = """
            INSERT INTO t_sgp_item (nm_item, ds_item, tp_item, dt_criacao, st_ativo)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, item.getNome());
            pr.setString(2, item.getDescricao());
            pr.setString(3, String.valueOf(item.getTipoItem().getValue())); 
            pr.setDate(4, item.getDataCriacao());
            pr.setInt(5, item.getStatusAtivo());
            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                item.setId(generatedId);
                System.out.println("Item base inserido -> ID: " + generatedId + ", Nome: " + item.getNome());

                if (item instanceof Produto) {
                    ((Produto) item).setId(generatedId);
                    System.out.println("Produto.id atualizado -> " + generatedId);
                }
            } else {
                System.out.println("Nenhum ID retornado para item: " + item.getNome());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir item base: " + e);
        }
    }


    private void salvarCategoriaNoBanco(CategoriaProduto categoria) {
        String sql = "INSERT INTO t_sgp_categoria_produto (nm_categoria, sg_categoria, sq_imagem) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, categoria.getNome());
            pr.setString(2, categoria.getSigla());
            pr.setBytes(3, categoria.getImagem());
            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) categoria.setId(rs.getLong(1));
        } catch(Exception e) {
            throw new RuntimeException("Erro ao salvar categoria no banco: " + e);
        }
    }

    private void salvarComboNoBanco(Combo combo) {
        String sql = "INSERT INTO t_sgp_combo (id_item, vl_desconto) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, combo.getId()); // ID do item base
            pr.setInt(2, combo.getDesconto());
            pr.executeUpdate();
        } catch(Exception e) {
            throw new RuntimeException("Erro ao persistir combo: " + e);
        }
    }

    private void verificarProduto(Produto esperado, Produto atual) {
        assertEquals(esperado.getValor(), atual.getValor());
        assertEquals(esperado.getCategoria().getId(), atual.getCategoria().getId());
        assertEquals(esperado.getCombos().size(), atual.getCombos().size());
    }
}
