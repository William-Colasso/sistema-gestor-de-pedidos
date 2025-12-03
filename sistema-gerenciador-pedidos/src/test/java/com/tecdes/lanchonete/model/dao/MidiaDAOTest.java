package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.model.enums.TipoMidia;

@DisplayName("Testes de Integração para MidiaDAO")
public class MidiaDAOTest {

    private MidiaDAO midiaDAO;

    @BeforeEach
    void setup() throws Exception {
        midiaDAO = new MidiaDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar mídia corretamente")
    void deveCriarMidia() {
        // Arrange
        Midia midia = criarMidia("Imagem do Produto", new byte[]{1, 2, 3}, TipoMidia.IMAGEM);

        // Act
        midiaDAO.create(midia);
        assertNotNull(midia.getId(), "ID deve ser gerado pelo banco");
        Midia criado = midiaDAO.getById(midia.getId());

        // Assert
        verificarMidia(midia, criado);
    }

    @Test
    @DisplayName("Não deve criar mídia com campos obrigatórios nulos")
    void naoDeveCriarMidiaComCamposNulos() {
        Midia descricaoNula = criarMidia(null, new byte[]{1,2}, TipoMidia.IMAGEM);
        Midia arquivoNulo = criarMidia("Descrição", null, TipoMidia.IMAGEM);
        Midia tipoNulo = criarMidia("Descrição", new byte[]{1,2}, null);

        assertThrows(Exception.class, () -> midiaDAO.create(descricaoNula), "Descrição nula deve gerar exceção");
        assertThrows(Exception.class, () -> midiaDAO.create(arquivoNulo), "Arquivo nulo deve gerar exceção");
        assertThrows(Exception.class, () -> midiaDAO.create(tipoNulo), "Tipo nulo deve gerar exceção");
    }

    @Test
    @DisplayName("Deve atualizar mídia existente")
    void deveAtualizarMidia() {
        // Arrange
        Midia midia = criarMidia("Vídeo Produto", new byte[]{1,2,3}, TipoMidia.VIDEO);
        midiaDAO.create(midia);

        // Act
        midia.setDescricao("Vídeo Atualizado");
        midia.setArquivo(new byte[]{4,5,6});
        midia.setTipo(TipoMidia.IMAGEM);
        midiaDAO.update(midia);

        // Assert
        Midia atualizado = midiaDAO.getById(midia.getId());
        assertEquals("Vídeo Atualizado", atualizado.getDescricao());
        assertEquals(TipoMidia.IMAGEM, atualizado.getTipo());
        assertEquals(3, atualizado.getArquivo().length);
    }

    @Test
    @DisplayName("Deve deletar mídia existente")
    void deveDeletarMidia() {
        // Arrange
        Midia midia = criarMidia("Imagem Delete", new byte[]{1}, TipoMidia.IMAGEM);
        midiaDAO.create(midia);

        // Act
        midiaDAO.delete(midia.getId());
        List<Midia> lista = midiaDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhuma mídia no banco");
    }

    @Test
    @DisplayName("Deve buscar mídia por ID")
    void deveBuscarMidiaPorId() {
        // Arrange
        Midia midia = criarMidia("Imagem ID", new byte[]{1,2}, TipoMidia.IMAGEM);
        midiaDAO.create(midia);

        // Act
        Midia encontrado = midiaDAO.getById(midia.getId());

        // Assert
        verificarMidia(midia, encontrado);
    }

    @Test
    @DisplayName("Deve buscar todas as mídias corretamente")
    void deveBuscarTodasMidias() {
        // Arrange
        Midia m1 = criarMidia("Imagem 1", new byte[]{1}, TipoMidia.IMAGEM);
        Midia m2 = criarMidia("Vídeo 1", new byte[]{2}, TipoMidia.VIDEO);
        Midia m3 = criarMidia("Imagem 2", new byte[]{3}, TipoMidia.IMAGEM);

        // Act
        midiaDAO.create(m1);
        midiaDAO.create(m2);
        midiaDAO.create(m3);

        List<Midia> todos = midiaDAO.getAll();
        todos.sort((a,b) -> Long.compare(a.getId(), b.getId()));
        List<Midia> esperado = Arrays.asList(m1, m2, m3);
        esperado.sort((a,b) -> Long.compare(a.getId(), b.getId()));

        // Assert
        for (int i=0;i<esperado.size();i++) {
            verificarMidia(esperado.get(i), todos.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Midia criarMidia(String descricao, byte[] arquivo, TipoMidia tipo) {
        Midia m = new Midia();
        m.setIdItem(mapItemProduto());
        m.setDescricao(descricao);
        m.setArquivo(arquivo);
        m.setTipo(tipo);
        return m;
    }

    private void verificarMidia(Midia esperado, Midia atual) {
        assertEquals(esperado.getIdItem(), atual.getIdItem());
        assertEquals(esperado.getDescricao(), atual.getDescricao());
        assertEquals(esperado.getTipo(), atual.getTipo());
        assertEquals(esperado.getArquivo().length, atual.getArquivo().length);
    }

    private Long mapItemProduto() {
        Item item = new Produto();
        item.setNome("Produto");
        item.setDescricao("Descrição");
        item.setTipoItem(TipoItem.PRODUTO);
        item.setDataCriacao(new Date(System.currentTimeMillis()));
        item.setStatusAtivo(1);
        salvarNoBanco(item);
        return item.getId();
    }

    public Item salvarNoBanco(Item t) {
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
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir item Combo no banco: " + e);
        }
    }

   
}
