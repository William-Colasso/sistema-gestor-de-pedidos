package com.tecdes.lanchonete;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.tecdes.lanchonete.config.FlatLafConfig;
import com.tecdes.lanchonete.config.provider.ControllerProvider;
import com.tecdes.lanchonete.config.provider.DAOProvider;
import com.tecdes.lanchonete.config.provider.IRepositoryProvider;
import com.tecdes.lanchonete.config.provider.ServiceProvider;
import com.tecdes.lanchonete.view.MainFrame;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.color.LightTheme;

public class Main {

    public static void main(String[] args) {

        DAOProvider daoProvider = new DAOProvider();
        IRepositoryProvider iRepositoryProvider = new IRepositoryProvider(daoProvider);
        ServiceProvider serviceProvider = new ServiceProvider(iRepositoryProvider);
        ControllerProvider controllerProvider = new ControllerProvider(serviceProvider);

        FlatLafConfig.setLookAndFeel(new FlatMacLightLaf());

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(
                    new LightTheme(), // ColorTheme
                    new ImageService(), // ImageService
                    controllerProvider.getCategoriaProdutoController(), // CategoriaProdutoController
                    controllerProvider.getComboController(), // ComboController
                    controllerProvider.getProdutoController(), // ProdutoController
                    controllerProvider.getGerenteController(), // GerenteController
                    controllerProvider.getClienteController(), // ClienteController
                    controllerProvider.getFuncionarioController(), // FuncionarioController
                    controllerProvider.getMidiaController(), // MidiaController
                    controllerProvider.getRelatorioController(), // RelatorioController
                    controllerProvider.getPagamentoController(), // PagamentoController
                    controllerProvider.getPedidoController() // PedidoController
            );
            frame.setVisible(true);

        });

    }
}
