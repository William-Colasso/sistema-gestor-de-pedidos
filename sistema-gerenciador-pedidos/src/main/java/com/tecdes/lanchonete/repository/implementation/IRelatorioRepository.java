package com.tecdes.lanchonete.repository.implementation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.tecdes.lanchonete.repository.interfaces.RelatorioRepository;


public class IRelatorioRepository implements RelatorioRepository {

    @Override
    public synchronized void createRelatorio(String registro, String path) {
        File file = new File(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(registro);
            bw.newLine();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório TXT: " + e);
        }
    }
}
