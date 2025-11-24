package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;

@DisplayName("Testes de Integração para ComboDAO")
public class ComboDAOTest {

    private ComboDAO comboDAO;

    @BeforeEach
    void setup() throws Exception {
        comboDAO = new ComboDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar combo corretamente")
    void deveCriarCombo() {
        // Arrange
        Combo combo = criarCombo("Combo Teste");
        criarItemBaseCombo(combo);

        // Act
        comboDAO.create(combo);
        Combo criado = comboDAO.getById(combo.getId());

        // Assert
        verificarCombo(combo, criado);
    }

    @Test
    @DisplayName("Não deve criar combo com campos obrigatórios nulos")
    void naoDeveCriarComboComCamposNulos() {
        // Nome nulo
        Combo nomeNulo = criarCombo(null);
        assertThrows(RuntimeException.class, () -> comboDAO.create(nomeNulo), "Nome nulo deve gerar exceção");

        // Descrição nula
        Combo descricaoNula = criarCombo("Combo Teste");
        descricaoNula.setDescricao(null);
        assertThrows(RuntimeException.class, () -> comboDAO.create(descricaoNula), "Descrição nula deve gerar exceção");

        // Desconto nulo ou inválido (negativo)
        // Combo descontoNulo = criarCombo("Combo Teste");
        // descontoNulo.setDesconto(null);
        // assertThrows(RuntimeException.class, () -> comboDAO.create(descontoNulo), "Desconto negativo deve gerar exceção");
    }



    @Test
    @DisplayName("Deve atualizar combo existente")
    void deveAtualizarCombo() {
        // Arrange
        Combo combo = criarCombo("Combo Antigo");
        combo.setDesconto(5);
        criarItemBaseCombo(combo);
        comboDAO.create(combo);

        // Act
        combo.setNome("Combo Atualizado");
        combo.setDesconto(15);
        comboDAO.update(combo);

        // Assert
        Combo atualizado = comboDAO.getById(combo.getId());
        assertEquals(15, atualizado.getDesconto());
    }

    @Test
    @DisplayName("Deve deletar combo existente")
    void deveDeletarCombo() {
        // Arrange
        Combo combo = criarCombo("Combo para deletar");
        criarItemBaseCombo(combo);
        comboDAO.create(combo);

        // Act
        comboDAO.delete(combo.getId());

        // Assert
        Combo deletado = comboDAO.getById(combo.getId());
        assertEquals(null, deletado, "Combo deve ser removido do banco");
    }

    @Test
    @DisplayName("Deve buscar combo por ID")
    void deveBuscarComboPorId() {
        // Arrange
        Combo combo = criarCombo("Combo Busca");
        criarItemBaseCombo(combo);
        comboDAO.create(combo);

        // Act
        Combo encontrado = comboDAO.getById(combo.getId());

        // Assert
        verificarCombo(combo, encontrado);
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Combo criarCombo(String nome) {
        Combo combo = new Combo();
        combo.setNome(nome);
        combo.setDescricao("Descrição " + nome);
        combo.setTipoItem(TipoItem.COMBO);
        combo.setDataCriacao(new Date(System.currentTimeMillis()));
        combo.setStatusAtivo(1);
        combo.setDesconto(10);

        // Produtos do combo
        Produto p1 = criarProduto("Produto 1", 10.0, 3);
        Produto p2 = criarProduto("Produto 2", 15.0, 2);
        combo.setProdutos(Arrays.asList(p1, p2));

        return combo;
    }

    private Produto criarProduto(String nome, double valor, int quantidade) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao("Descrição " + nome);
        p.setTipoItem(TipoItem.PRODUTO);
        p.setDataCriacao(new Date(System.currentTimeMillis()));
        p.setStatusAtivo(1);
        p.setValor(valor);
        p.setQuantidade(quantidade);

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(1L);
        categoria.setNome("Categoria Teste");
        categoria.setSigla("CT");
        salvarCategoriaNoBanco(categoria);
        p.setCategoria(categoria);

        salvarProdutoNoBanco(p);

        return p;
    }

    private void salvarCategoriaNoBanco(CategoriaProduto categoria) {
        String sql = "INSERT INTO t_sgp_categoria_produto (nm_categoria, sg_categoria) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, categoria.getNome());
            pr.setString(2, categoria.getSigla());
            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) categoria.setId(rs.getLong(1));
            else throw new RuntimeException("Não foi possível gerar ID da categoria de teste");
        } catch(Exception e) {
            throw new RuntimeException("Erro ao salvar categoria no banco: " + e);
        }
    }

    private void salvarProdutoNoBanco(Produto produto) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            salvarItemBase(produto, conn);  
            salvarProduto(produto, conn);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir produto de teste: " + e);
        }
    }

    private void salvarItemBase(Produto produto, Connection conn) throws SQLException{
        // Persiste item base
            String sqlItem = """
                INSERT INTO t_sgp_item (nm_item, ds_item, tp_item, dt_criacao, st_ativo)
                VALUES (?, ?, ?, ?, ?)
            """;
            try (PreparedStatement pr = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS)) {
                pr.setString(1, produto.getNome());
                pr.setString(2, produto.getDescricao());
                pr.setString(3, String.valueOf(produto.getTipoItem().getValue()));
                pr.setDate(4, produto.getDataCriacao());
                pr.setInt(5, produto.getStatusAtivo());
                pr.executeUpdate();

                ResultSet rs = pr.getGeneratedKeys();
                if (rs.next()) {
                    produto.setId(rs.getLong(1));
                }
            }
    }

    private void salvarProduto(Produto produto, Connection conn) throws SQLException{
        // Persiste dados específicos de produto
        String sqlProduto = """
            INSERT INTO t_sgp_produto (id_item, id_categoria, vl_produto)
            VALUES (?, ?, ?)
        """;
        try (PreparedStatement pr = conn.prepareStatement(sqlProduto)) {
            pr.setLong(1, produto.getId());
            pr.setLong(2, produto.getCategoria().getId()); // assume que categoria já existe
            pr.setDouble(3, produto.getValor());
            pr.executeUpdate();
        }
    }

    private void verificarCombo(Combo esperado, Combo atual) {
        assertEquals(esperado.getDesconto(), atual.getDesconto(), "Desconto do combo deve ser igual ao esperado. Esperado: " + esperado.getDesconto() + ", Atual: " + atual.getDesconto());
        assertEquals(esperado.getProdutos().size(), atual.getProdutos().size(), "Quantidade de produtos deve ser igual. Esperado: " + esperado.getProdutos().size() + ", Atual: " + atual.getProdutos().size());

        for (int i = 0; i < esperado.getProdutos().size(); i++) {
            Produto esperadoProd = esperado.getProdutos().get(i);
            Produto atualProd = atual.getProdutos().get(i);
            assertEquals(esperadoProd.getNome(), atualProd.getNome(), "Nome do produto na posição " + i + " deve ser igual. Esperado: " + esperadoProd.getNome() + ", Atual: " + atualProd.getNome());
            assertEquals(esperadoProd.getValor(), atualProd.getValor(), "Valor do produto na posição " + i + " deve ser igual. Esperado: " + esperadoProd.getValor() + ", Atual: " + atualProd.getValor());
            assertEquals(esperadoProd.getQuantidade(), atualProd.getQuantidade(), "Quantidade do produto na posição " + i + " deve ser igual. Esperado: " + esperadoProd.getQuantidade() + ", Atual: " + atualProd.getQuantidade());
        }
    }

    private void criarItemBaseCombo(Combo t) {
        String sqlItem = """
            INSERT INTO t_sgp_item (
                nm_item, ds_item, tp_item, dt_criacao, st_ativo
            ) VALUES (?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement pr = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getDescricao());
            pr.setString(3, String.valueOf(t.getTipoItem().getValue()));
            pr.setDate(4, t.getDataCriacao());
            pr.setInt(5, t.getStatusAtivo());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir item Combo no banco: " + e);
        }
    }
}
