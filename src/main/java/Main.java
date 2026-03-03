import controlador.ControladorRepartidor;
import modelo.Repartidor;
import vista.MenuPrincipal;
import vista.entregas.VistaEntregas;
import vista.repartidores.VistaRepartidores;
import vista.pedidos.VistaPedidos;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
