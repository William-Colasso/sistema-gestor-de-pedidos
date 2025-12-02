package com.tecdes.lanchonete.repository.interfaces;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.model.entity.dto.Relatorio;


public interface PedidoRepository extends Crud<Pedido>{
    Relatorio getRelatorioAnual();
    Relatorio getRelatorioMensal();
    Relatorio getRelatorioSemanal();
}
