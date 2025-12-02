package com.tecdes.lanchonete.controller;

import com.tecdes.lanchonete.service.RelatorioService;

public class RelatorioController {
    private RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public void getRelatorioAnual(String path) {
        relatorioService.getRelatorioAnual(path);
    }
}
