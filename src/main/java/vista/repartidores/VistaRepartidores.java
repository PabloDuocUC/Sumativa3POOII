package vista.repartidores;

import controlador.ControladorRepartidor;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaRepartidores extends JFrame{

    private JTextField txtNombre;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;

    private ControladorRepartidor controlador;

    public VistaRepartidores() {

        controlador = new ControladorRepartidor();

        setTitle("Gestor de repartidores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelForm = new JPanel(new FlowLayout());


        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);
        panelForm.add(txtNombre);

        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");


        panelForm.add(btnCrear);
        panelForm.add(btnActualizar);
        panelForm.add(btnEliminar);

        panel.add(panelForm, BorderLayout.NORTH);

        //tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        panel.add(scroll, BorderLayout.CENTER);

        //añadir boton para volver
        btnVolver = new JButton("Volver al Menú");
        panel.add(btnVolver, BorderLayout.SOUTH);

        add(panel);

        btnCrear.addActionListener(e -> crearRepartidor());
        btnActualizar.addActionListener(e -> actualizarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());
        btnVolver.addActionListener(e -> {
            new vista.MenuPrincipal().setVisible(true);
            dispose();
        });
    }

    private void cargarTabla() {

        modeloTabla.setRowCount(0);

        List<Repartidor> lista = controlador.listarRepartidores();

        for (Repartidor r : lista) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getNombre()});
        }
    }

    //creacion
    private void crearRepartidor() {

        String nombre = txtNombre.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede ser nulo.");
            return;
        }

        controlador.registrarRepartidor(nombre);
        cargarTabla();
        txtNombre.setText("");

        JOptionPane.showMessageDialog(this, "Repartidor creado correctamente.");
    }

    //actualizar
    private void actualizarRepartidor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor.");
            return;
        }

        String nombre = txtNombre.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        controlador.actualizarRepartidor(id, nombre);
        cargarTabla();

        JOptionPane.showMessageDialog(this, "Repartidor actualizado.");
    }

    private void eliminarRepartidor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        controlador.eliminarRepartidor(id);
        cargarTabla();

        JOptionPane.showMessageDialog(this, "Repartidor eliminado.");
    }
}
