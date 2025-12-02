package com.tecdes.lanchonete.model.entity.dto;

public record Relatorio(Long idItemMaisVendido, int qntItemMaisVendido, int totalPedidos, double faturamentoTotal) {}
