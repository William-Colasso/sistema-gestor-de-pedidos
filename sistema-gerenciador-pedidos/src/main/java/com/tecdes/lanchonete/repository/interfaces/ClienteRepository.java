package com.tecdes.lanchonete.repository.interfaces;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Cupom;

public interface ClienteRepository extends Crud<Cliente> {
    List<Cupom> getCuponsByIdCliente(Long id);
}
