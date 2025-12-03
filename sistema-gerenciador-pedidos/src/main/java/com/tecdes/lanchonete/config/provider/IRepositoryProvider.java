package com.tecdes.lanchonete.config.provider;

import com.tecdes.lanchonete.repository.implementation.*;

public class IRepositoryProvider {

    private final ICategoriaProdutoRepository categoriaProdutoRepository;
    private final IClienteRepository clienteRepository;
    private final IComboRepository comboRepository;
    private final ICupomRepository cupomRepository;
    private final IFuncionarioRepository funcionarioRepository;
    private final IGerenteRepository gerenteRepository;
    private final IItemRepository itemRepository;
    private final IMidiaRepository midiaRepository;
    private final IPagamentoRepository pagamentoRepository;
    private final IParceiroRepository parceiroRepository;
    private final IPedidoRepository pedidoRepository;
    private final IProdutoRepository produtoRepository;
    private final IRelatorioRepository relatorioRepository;

    public IRepositoryProvider(DAOProvider dao) {
        this.categoriaProdutoRepository = new ICategoriaProdutoRepository(dao.getCategoriaProdutoDAO());
        this.clienteRepository = new IClienteRepository(dao.getClienteDAO());
        this.comboRepository = new IComboRepository(dao.getComboDAO());
        this.cupomRepository = new ICupomRepository(dao.getCupomDAO());
        this.funcionarioRepository = new IFuncionarioRepository(dao.getFuncionarioDAO());
        this.gerenteRepository = new IGerenteRepository(dao.getGerenteDAO());
        this.itemRepository = new IItemRepository(dao.getItemDAO());
        this.midiaRepository = new IMidiaRepository(dao.getMidiaDAO());
        this.pagamentoRepository = new IPagamentoRepository(dao.getPagamentoDAO());
        this.parceiroRepository = new IParceiroRepository(dao.getParceiroDAO());
        this.pedidoRepository = new IPedidoRepository(dao.getPedidoDAO());
        this.produtoRepository = new IProdutoRepository(dao.getProdutoDAO());
        this.relatorioRepository = new IRelatorioRepository();
    }

    public ICategoriaProdutoRepository getCategoriaProdutoRepository() { return categoriaProdutoRepository; }
    public IClienteRepository getClienteRepository() { return clienteRepository; }
    public IComboRepository getComboRepository() { return comboRepository; }
    public ICupomRepository getCupomRepository() { return cupomRepository; }
    public IFuncionarioRepository getFuncionarioRepository() { return funcionarioRepository; }
    public IGerenteRepository getGerenteRepository() { return gerenteRepository; }
    public IItemRepository getItemRepository() { return itemRepository; }
    public IMidiaRepository getMidiaRepository() { return midiaRepository; }
    public IPagamentoRepository getPagamentoRepository() { return pagamentoRepository; }
    public IParceiroRepository getParceiroRepository() { return parceiroRepository; }
    public IPedidoRepository getPedidoRepository() { return pedidoRepository; }
    public IProdutoRepository getProdutoRepository() { return produtoRepository; }
    public IRelatorioRepository getRelatorioRepository() { return relatorioRepository; }
}


