package com.tecdes.lanchonete.config.provider;
import com.tecdes.lanchonete.model.dao.*;
public class DAOProvider {

    private final CategoriaProdutoDAO categoriaProdutoDAO;
    private final ClienteDAO clienteDAO;
    private final ComboDAO comboDAO;
    private final CupomDAO cupomDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final GerenteDAO gerenteDAO;
    private final ItemDAO itemDAO;
    private final MidiaDAO midiaDAO;
    private final PagamentoDAO pagamentoDAO;
    private final ParceiroDAO parceiroDAO;
    private final PedidoDAO pedidoDAO;
    private final ProdutoDAO produtoDAO;

    public DAOProvider() {
        this.categoriaProdutoDAO = new CategoriaProdutoDAO();
        this.clienteDAO = new ClienteDAO();
        this.comboDAO = new ComboDAO();
        this.cupomDAO = new CupomDAO();
        this.funcionarioDAO = new FuncionarioDAO();
        this.gerenteDAO = new GerenteDAO();
        this.itemDAO = new ItemDAO();
        this.midiaDAO = new MidiaDAO();
        this.pagamentoDAO = new PagamentoDAO();
        this.parceiroDAO = new ParceiroDAO();
        this.pedidoDAO = new PedidoDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    public CategoriaProdutoDAO getCategoriaProdutoDAO() { return categoriaProdutoDAO; }
    public ClienteDAO getClienteDAO() { return clienteDAO; }
    public ComboDAO getComboDAO() { return comboDAO; }
    public CupomDAO getCupomDAO() { return cupomDAO; }
    public FuncionarioDAO getFuncionarioDAO() { return funcionarioDAO; }
    public GerenteDAO getGerenteDAO() { return gerenteDAO; }
    public ItemDAO getItemDAO() { return itemDAO; }
    public MidiaDAO getMidiaDAO() { return midiaDAO; }
    public PagamentoDAO getPagamentoDAO() { return pagamentoDAO; }
    public ParceiroDAO getParceiroDAO() { return parceiroDAO; }
    public PedidoDAO getPedidoDAO() { return pedidoDAO; }
    public ProdutoDAO getProdutoDAO() { return produtoDAO; }
}
