package com.tecdes.lanchonete.config.provider;

import com.tecdes.lanchonete.controller.*;

public final class ControllerProvider {

    private final ClienteController clienteController;
    private final ProdutoController produtoController;
    private final PedidoController pedidoController;
    private final CupomController cupomController;
    private final CategoriaProdutoController categoriaProdutoController;
    private final FuncionarioController funcionarioController;
    private final GerenteController gerenteController;
    private final MidiaController midiaController;
    private final ParceiroController parceiroController;
    private final ComboController comboController;
    private final PagamentoController pagamentoController;

    public ControllerProvider(ServiceProvider services) {
        this.clienteController = new ClienteController(services.getClienteService());
        this.produtoController = new ProdutoController(services.getProdutoService());
        this.pedidoController = new PedidoController(services.getPedidoService());
        this.cupomController = new CupomController(services.getCupomService());
        this.categoriaProdutoController = new CategoriaProdutoController(services.getCategoriaProdutoService());
        this.funcionarioController = new FuncionarioController(services.getFuncionarioService());
        this.gerenteController = new GerenteController(services.getGerenteService());
        this.midiaController = new MidiaController(services.getMidiaService());
        this.parceiroController = new ParceiroController(services.getParceiroService());
        this.comboController = new ComboController(services.getComboService());
        this.pagamentoController = new PagamentoController(services.getPagamentoService());
    }

    public ClienteController getClienteController() {
        return clienteController;
    }

    public ProdutoController getProdutoController() {
        return produtoController;
    }

    public PedidoController getPedidoController() {
        return pedidoController;
    }

    public CupomController getCupomController() {
        return cupomController;
    }

    public CategoriaProdutoController getCategoriaProdutoController() {
        return categoriaProdutoController;
    }

    public FuncionarioController getFuncionarioController() {
        return funcionarioController;
    }

    public GerenteController getGerenteController() {
        return gerenteController;
    }


    public MidiaController getMidiaController() {
        return midiaController;
    }

    public ParceiroController getParceiroController() {
        return parceiroController;
    }

    public ComboController getComboController() {
        return comboController;
    }

    public PagamentoController getPagamentoController() {
        return pagamentoController;
    }
}