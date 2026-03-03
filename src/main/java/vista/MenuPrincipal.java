package vista;

import vista.repartidores.VistaRepartidores;
import vista.pedidos.VistaPedidos;
import vista.entregas.VistaEntregas;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("SpeedFast - Menú Principal");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JButton btnRepartidores = new JButton("Gestión de Repartidores");
        JButton btnPedidos = new JButton("Gestión de Pedidos");
        JButton btnEntregas = new JButton("Gestión de Entregas");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnRepartidores);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnEntregas);
        panelBotones.add(btnSalir);

        panel.add(panelBotones, BorderLayout.CENTER);

        add(panel);

        btnRepartidores.addActionListener(e -> {
            new VistaRepartidores().setVisible(true);
            dispose();
        });

        btnPedidos.addActionListener(e -> {
            new VistaPedidos().setVisible(true);
            dispose();
        });

        btnEntregas.addActionListener(e -> {
            new VistaEntregas().setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}
