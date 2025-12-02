package com.tecdes.lanchonete.repository.interfaces;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.model.entity.dto.Relatorio;


public interface PedidoRepository extends Crud<Pedido>{
    Relatorio getRelatorioAnual();
    Relatorio getRelatorioMensal();
    Relatorio getRelatorioSemanal();
    List<Pedido> getByCliente(Long id);
    List<Pedido> getByStatusPedido(char status);
    public List<Pedido> getByFuncionario(Long id);
}
