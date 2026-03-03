package vista.entregas;

import controlador.ControladorEntrega;
import controlador.ControladorPedido;
import controlador.ControladorRepartidor;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class VistaEntregas extends JFrame{

    private JComboBox<Pedido> comboPedido;
    private JComboBox<Repartidor> comboRepartidor;
    private JTextField txtFecha;
    private JTextField txtHora;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;

    private ControladorEntrega controladorEntrega;
    private ControladorPedido controladorPedido;
    private ControladorRepartidor controladorRepartidor;

    public VistaEntregas() {

        controladorEntrega = new ControladorEntrega();
        controladorPedido = new ControladorPedido();
        controladorRepartidor = new ControladorRepartidor();

        setTitle("Gestión de Entregas");
        setSize(950, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarPedidos();
        cargarTabla();
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(2, 1));

        JPanel fila1 = new JPanel(new FlowLayout());
        JPanel fila2 = new JPanel(new FlowLayout());

        comboPedido = new JComboBox<>();
        comboRepartidor = new JComboBox<>();
        txtFecha = new JTextField(10);
        txtHora = new JTextField(8);

        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        fila1.add(new JLabel("Pedido:"));
        fila1.add(comboPedido);

        fila1.add(new JLabel("Repartidor:"));
        fila1.add(comboRepartidor);

        fila1.add(new JLabel("Fecha (YYYY-MM-DD):"));
        fila1.add(txtFecha);

        fila1.add(new JLabel("Hora (HH:MM:SS):"));
        fila1.add(txtHora);

        fila2.add(btnCrear);
        fila2.add(btnActualizar);
        fila2.add(btnEliminar);

        panelForm.add(fila1);
        panelForm.add(fila2);

        panel.add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("ID Pedido");
        modeloTabla.addColumn("ID Repartidor");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        panel.add(scroll, BorderLayout.CENTER);

        btnVolver = new JButton("Volver al Menú");
        panel.add(btnVolver, BorderLayout.SOUTH);

        add(panel);

        btnCrear.addActionListener(e -> crearEntrega());
        btnActualizar.addActionListener(e -> actualizarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());

        btnVolver.addActionListener(e -> {
            new vista.MenuPrincipal().setVisible(true);
            dispose();
        });

        //se agrega seleccion automatica al hacer click en la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {

                    int idPedido = (int) modeloTabla.getValueAt(fila, 1);
                    int idRepartidor = (int) modeloTabla.getValueAt(fila, 2);

                    for (int i = 0; i < comboPedido.getItemCount(); i++) {
                        if (comboPedido.getItemAt(i).getId() == idPedido) {
                            comboPedido.setSelectedIndex(i);
                            break;
                        }
                    }

                    for (int i = 0; i < comboRepartidor.getItemCount(); i++) {
                        if (comboRepartidor.getItemAt(i).getId() == idRepartidor) {
                            comboRepartidor.setSelectedIndex(i);
                            break;
                        }
                    }

                    txtFecha.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtHora.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private void cargarPedidos() {

        comboPedido.removeAllItems();
        comboRepartidor.removeAllItems();

        List<Pedido> pedidos = controladorPedido.listarPedidos();
        for (Pedido p : pedidos) {
            comboPedido.addItem(p);
        }

        List<Repartidor> repartidores = controladorRepartidor.listarRepartidores();
        for (Repartidor r : repartidores) {
            comboRepartidor.addItem(r);
        }
    }

    private void cargarTabla() {

        modeloTabla.setRowCount(0);

        List<Entrega> lista = controladorEntrega.listarEntregas();

        for (Entrega e : lista) {
            modeloTabla.addRow(new Object[]{
                    e.getId(),
                    e.getIdPedido(),
                    e.getIdRepartidor(),
                    e.getFecha(),
                    e.getHora()
            });
        }
    }

    private void crearEntrega() {

        Pedido pedido = (Pedido) comboPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();

        if (pedido == null || repartidor == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar pedido y repartidor.");
            return;
        }

        try {
            Date fecha = Date.valueOf(txtFecha.getText().trim());
            Time hora = Time.valueOf(txtHora.getText().trim());

            controladorEntrega.registrarEntrega(
                    pedido.getId(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            cargarTabla();
            JOptionPane.showMessageDialog(this, "Entrega registrada correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato inválido de fecha u hora.");
        }
    }

    private void actualizarEntrega() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una entrega.");
            return;
        }

        Pedido pedido = (Pedido) comboPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();

        try {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Date fecha = Date.valueOf(txtFecha.getText().trim());
            Time hora = Time.valueOf(txtHora.getText().trim());

            controladorEntrega.actualizarEntrega(
                    id,
                    pedido.getId(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            cargarTabla();
            JOptionPane.showMessageDialog(this, "Entrega actualizada.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar.");
        }
    }

    private void eliminarEntrega() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una entrega.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        controladorEntrega.eliminarEntrega(id);
        cargarTabla();

        JOptionPane.showMessageDialog(this, "Entrega eliminada.");
    }
}
