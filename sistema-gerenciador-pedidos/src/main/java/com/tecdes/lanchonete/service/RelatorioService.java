package com.tecdes.lanchonete.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.model.entity.dto.Relatorio;
import com.tecdes.lanchonete.repository.interfaces.ItemRepository;
import com.tecdes.lanchonete.repository.interfaces.PedidoRepository;
import com.tecdes.lanchonete.repository.interfaces.RelatorioRepository;

public class RelatorioService {
    private RelatorioRepository relatorioRepository;
    private PedidoRepository pedidoRepository;
    private ItemRepository itemRepository;



    public RelatorioService(RelatorioRepository relatorioRepository, PedidoRepository pedidoRepository, ItemRepository itemRepository) {
        this.relatorioRepository = relatorioRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemRepository = itemRepository;
    }

    public void getRelatorioAnual(String path) {
        if (path == null || path.isEmpty()) {
            throw new InvalidFieldException("Path não pode ser nulo ou vazio.");
        }

        String registro = createStringRelatorio("Anual", pedidoRepository.getRelatorioAnual());

        relatorioRepository.createRelatorio(registro, path);
    }

    public void getRelatorioMensal(String path) {
        if (path == null || path.isEmpty()) {
            throw new InvalidFieldException("Path não pode ser nulo ou vazio.");
        }

        String registro = createStringRelatorio("Mensal", pedidoRepository.getRelatorioMensal());

        relatorioRepository.createRelatorio(registro, path);
    }

    public void getRelatorioSemanal(String path) {
        if (path == null || path.isEmpty()) {
            throw new InvalidFieldException("Path não pode ser nulo ou vazio.");
        }

        String registro = createStringRelatorio("Semanal", pedidoRepository.getRelatorioSemanal());

        relatorioRepository.createRelatorio(registro, path);
    }

    private String createStringRelatorio(String tipo, Relatorio r) {
        String nomeItem = itemRepository.getById(r.idItemMaisVendido()).getNome();
        return String.format("""
            |===============================================================|\n
            |          Relatório %7s - Gerado em %10s             |\n
            |===============================================================|\n
            | Item mais vendido: %-43s|\n
            |---------------------------------------------------------------|\n
            | Quantidade de %-48s|\n
            |---------------------------------------------------------------|\n
            | Total de pedidos: %-44d|\n
            |---------------------------------------------------------------|\n
            | Faturamento total: %-43.2f|\n
            |---------------------------------------------------------------|\n        
        """, tipo, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), nomeItem, String.format("%s vendidos(as): %d", nomeItem, r.qntItemMaisVendido()), r.totalPedidos(), r.faturamentoTotal());
    }
}
