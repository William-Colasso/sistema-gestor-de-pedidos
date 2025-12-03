package com.tecdes.lanchonete.repository.interfaces;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Gerente;

public interface GerenteRepository extends Crud<Gerente>{
    Gerente getByLogin(String login);
}
