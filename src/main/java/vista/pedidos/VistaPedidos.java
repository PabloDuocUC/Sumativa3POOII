package vista.pedidos;

import controlador.ControladorPedido;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaPedidos extends JFrame{

    private JTextField txtDireccion;
    private JComboBox<TipoPedido> comboTipo;
    private JComboBox<EstadoPedido> comboEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;

    private ControladorPedido controlador;

    public VistaPedidos() {

        controlador = new ControladorPedido();

        setTitle("Gestión de pedidos");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelForm = new JPanel(new FlowLayout());

        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(15);
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(TipoPedido.values());
        panelForm.add(comboTipo);

        panelForm.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(EstadoPedido.values());
        panelForm.add(comboEstado);

        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelForm.add(btnCrear);
        panelForm.add(btnActualizar);
        panelForm.add(btnEliminar);

        panel.add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Estado");

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        panel.add(scroll, BorderLayout.CENTER);

        btnVolver = new JButton("Volver al Menú");
        panel.add(btnVolver, BorderLayout.SOUTH);

        add(panel);

        btnCrear.addActionListener(e -> crearPedido());
        btnActualizar.addActionListener(e -> actualizarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());

        btnVolver.addActionListener(e -> {
            new vista.MenuPrincipal().setVisible(true);
            dispose();
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {

                    txtDireccion.setText(modeloTabla.getValueAt(fila, 1).toString());

                    comboTipo.setSelectedItem(
                            TipoPedido.valueOf(modeloTabla.getValueAt(fila, 2).toString())
                    );

                    comboEstado.setSelectedItem(
                            EstadoPedido.valueOf(modeloTabla.getValueAt(fila, 3).toString())
                    );
                }
            }
        });
    }

    private void cargarTabla() {

        modeloTabla.setRowCount(0);

        List<Pedido> lista = controlador.listarPedidos();

        for (Pedido p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getDireccion(),
                    p.getTipo(),
                    p.getEstado()
            });
        }
    }

    private void crearPedido() {

        String direccion = txtDireccion.getText();

        if (direccion == null || direccion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una dirección.");
            return;
        }

        TipoPedido tipo = (TipoPedido) comboTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) comboEstado.getSelectedItem();

        controlador.registrarPedido(direccion, tipo, estado);

        cargarTabla();
        txtDireccion.setText("");

        JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
    }

    private void actualizarPedido() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }

        String direccion = txtDireccion.getText();

        if (direccion == null || direccion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una dirección.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        TipoPedido tipo = (TipoPedido) comboTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) comboEstado.getSelectedItem();

        controlador.actualizarPedido(id, direccion, tipo, estado);

        cargarTabla();

        JOptionPane.showMessageDialog(this, "Pedido actualizado.");
    }

    private void eliminarPedido() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        controlador.eliminarPedido(id);
        cargarTabla();

        JOptionPane.showMessageDialog(this, "Pedido eliminado.");
    }
}
