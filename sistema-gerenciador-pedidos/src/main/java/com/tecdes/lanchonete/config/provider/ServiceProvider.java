package com.tecdes.lanchonete.config.provider;

import com.tecdes.lanchonete.service.*;

public class ServiceProvider {

    private final CategoriaProdutoService categoriaProdutoService;
    private final ClienteService clienteService;
    private final ComboService comboService;
    private final CupomService cupomService;
    private final FuncionarioService funcionarioService;
    private final GerenteService gerenteService;
    private final MidiaService midiaService;
    private final PagamentoService pagamentoService;
    private final ParceiroService parceiroService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final RelatorioService relatorioService;

    public ServiceProvider(IRepositoryProvider repo) {

        this.midiaService = new MidiaService(repo.getMidiaRepository());
        this.categoriaProdutoService = new CategoriaProdutoService(repo.getCategoriaProdutoRepository());
        this.clienteService = new ClienteService(repo.getClienteRepository());
        this.comboService = new ComboService(repo.getComboRepository(), repo.getItemRepository(), this.midiaService);
        this.cupomService = new CupomService(repo.getCupomRepository());
        this.funcionarioService = new FuncionarioService(repo.getFuncionarioRepository());
        this.gerenteService = new GerenteService(repo.getGerenteRepository(), this.funcionarioService);
        this.pagamentoService = new PagamentoService(repo.getPagamentoRepository());
        this.parceiroService = new ParceiroService(repo.getParceiroRepository());
        this.pedidoService = new PedidoService(repo.getPedidoRepository());
        this.produtoService = new ProdutoService(repo.getProdutoRepository(), repo.getItemRepository(), this.midiaService);
        this.relatorioService = new RelatorioService(repo.getRelatorioRepository(), repo.getPedidoRepository(), repo.getItemRepository());
    }

    public CategoriaProdutoService getCategoriaProdutoService() { return categoriaProdutoService; }
    public ClienteService getClienteService() { return clienteService; }
    public ComboService getComboService() { return comboService; }
    public CupomService getCupomService() { return cupomService; }
    public FuncionarioService getFuncionarioService() { return funcionarioService; }
    public GerenteService getGerenteService() { return gerenteService; }
    public MidiaService getMidiaService() { return midiaService; }
    public PagamentoService getPagamentoService() { return pagamentoService; }
    public ParceiroService getParceiroService() { return parceiroService; }
    public PedidoService getPedidoService() { return pedidoService; }
    public ProdutoService getProdutoService() { return produtoService; }
    public RelatorioService getRelatorioService() {return relatorioService;}
}