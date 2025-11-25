package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.model.entity.Parceiro;

@DisplayName("Testes de Integração para CupomDAO")
public class CupomDAOTest {

    private CupomDAO cupomDAO;
    private ParceiroDAO parceiroDAO;

    @BeforeEach
    void setup() throws Exception {
        cupomDAO = new CupomDAO();
        parceiroDAO = new ParceiroDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {

            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar cupom corretamente")
    void deveCriarCupom() {
        Parceiro parceiro = criarParceiro("TecMart", "contato@tecmart.com", "11999999999");
        parceiroDAO.create(parceiro);

        Cupom cupom = criarCupom(parceiro, "Cupom de Desconto", "DESCONTO10", 10, 1);

        cupomDAO.create(cupom);

        assertNotNull(cupom.getId(), "ID deve ser preenchido após criação");

        Cupom criado = cupomDAO.getById(cupom.getId());

        verificarCupom(cupom, criado);
    }

    @Test
    @DisplayName("Deve atualizar cupom existente")
    void deveAtualizarCupom() {
        Parceiro parceiro = criarParceiro("Loja XPTO", "xpto@email.com", "11888888888");
        parceiroDAO.create(parceiro);

        Cupom cupom = criarCupom(parceiro, "Cupom Inicial", "CUPOM1", 5, 1);
        cupomDAO.create(cupom);

        cupom.setDescricao("Cupom Atualizado");
        cupom.setValorDesconto(15);
        cupom.setValido(0);

        cupomDAO.update(cupom);

        Cupom atualizado = cupomDAO.getById(cupom.getId());

        assertEquals("Cupom Atualizado", atualizado.getDescricao());
        assertEquals(15, atualizado.getValorDesconto());
        assertEquals(0, atualizado.getValido());
    }

    @Test
    @DisplayName("Deve deletar cupom existente")
    void deveDeletarCupom() {
        Parceiro parceiro = criarParceiro("Tech Store", "ts@email.com", "11333333333");
        parceiroDAO.create(parceiro);

        Cupom cupom = criarCupom(parceiro, "Cupom Del", "DEL123", 20, 1);
        cupomDAO.create(cupom);

        cupomDAO.delete(cupom.getId());

        List<Cupom> todos = cupomDAO.getAll();
        assertEquals(0, todos.size(), "Nenhum cupom deve existir após deleção");
    }

    @Test
    @DisplayName("Deve buscar cupom por ID")
    void deveBuscarCupomPorId() {
        Parceiro parceiro = criarParceiro("MegaStore", "mega@email.com", "11444444444");
        parceiroDAO.create(parceiro);

        Cupom cupom = criarCupom(parceiro, "Cupom ID", "CUPID", 12, 1);
        cupomDAO.create(cupom);

        Cupom encontrado = cupomDAO.getById(cupom.getId());

        verificarCupom(cupom, encontrado);
    }

    @Test
    @DisplayName("Deve retornar todos os cupons do banco")
    void deveBuscarTodosCupons() {
        Parceiro parceiro = criarParceiro("Parceiro All", "all@mail.com", "11555555555");
        parceiroDAO.create(parceiro);

        Cupom c1 = criarCupom(parceiro, "Cupom A", "A1", 5, 1);
        Cupom c2 = criarCupom(parceiro, "Cupom B", "B1", 10, 0);
        Cupom c3 = criarCupom(parceiro, "Cupom C", "C1", 15, 1);

        cupomDAO.create(c1);
        cupomDAO.create(c2);
        cupomDAO.create(c3);

        List<Cupom> todos = cupomDAO.getAll();
        todos.sort(Comparator.comparing(Cupom::getId));

        List<Cupom> esperado = Arrays.asList(c1, c2, c3);
        esperado.sort(Comparator.comparing(Cupom::getId));

        for (int i = 0; i < esperado.size(); i++) {
            verificarCupom(esperado.get(i), todos.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Parceiro criarParceiro(String nome, String email, String telefone) {
        Parceiro p = new Parceiro();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone);
        return p;
    }

    private Cupom criarCupom(Parceiro parceiro, String descricao, String nome, int desconto, int valido) {
        Cupom c = new Cupom();
        c.setParceiro(parceiro);
        c.setDescricao(descricao);
        c.setNome(nome);
        c.setValorDesconto(desconto);
        c.setValido(valido);
        return c;
    }

    private void verificarCupom(Cupom esperado, Cupom atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getDescricao(), atual.getDescricao());
        assertEquals(esperado.getValorDesconto(), atual.getValorDesconto());
        assertEquals(esperado.getValido(), atual.getValido());
        assertEquals(esperado.getParceiro().getNome(), atual.getParceiro().getNome());
        assertEquals(esperado.getParceiro().getEmail(), atual.getParceiro().getEmail());
    }
}
