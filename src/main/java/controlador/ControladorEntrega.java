package controlador;

import dao.EntregaDAO;
import dao.impl.EntregaDAOImpl;
import modelo.Entrega;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ControladorEntrega {

    private EntregaDAO entregaDAO;

    public ControladorEntrega() {
        this.entregaDAO = new EntregaDAOImpl();
    }

    public void registrarEntrega(int idPedido, int idRepartidor, Date fecha, Time hora) {

        if (idPedido <= 0 || idRepartidor <= 0) {
            System.out.println("Asegurese de ingresar Pedido y Repartidor");
            return;
        }

        if (fecha == null || hora == null) {
            System.out.println("Ingrese fecha y hora");
            return;
        }

        Entrega entrega = new Entrega();
        entrega.setIdPedido(idPedido);
        entrega.setIdRepartidor(idRepartidor);
        entrega.setFecha(fecha);
        entrega.setHora(hora);

        entregaDAO.crear(entrega);
    }

    public List<Entrega> listarEntregas() {
        return entregaDAO.leerTodos();
    }

    public void actualizarEntrega(int id, int idPedido, int idRepartidor, Date fecha, Time hora) {

        if (id <= 0) {
            System.out.println("ID inválido.");
            return;
        }

        Entrega entrega = new Entrega(id, idPedido, idRepartidor, fecha, hora);
        entregaDAO.actualizar(entrega);
    }

    public void eliminarEntrega(int id) {

        if (id <= 0) {
            System.out.println("ID inválido.");
            return;
        }

        entregaDAO.borrar(id);
    }
}
