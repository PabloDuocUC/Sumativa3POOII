package controlador;

import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;

import java.util.List;

public class ControladorPedido {
    private PedidoDAO pedidoDAO;

    public ControladorPedido() {
        this.pedidoDAO = new PedidoDAOImpl();
    }

    //registramos el pedido
    public void registrarPedido(String direccion, TipoPedido tipo, EstadoPedido estado) {

        if (direccion == null || direccion.trim().isEmpty()) {
            System.out.println("La dirección no puede ser nula.");
            return;
        }

        if (tipo == null || estado == null) {
            System.out.println("Tipo y estado no pueden ser null.");
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setDireccion(direccion.trim());
        pedido.setTipo(tipo);
        pedido.setEstado(estado);

        pedidoDAO.crear(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.leerTodos();
    }

    //actualizar
    public void actualizarPedido(int id, String direccion, TipoPedido tipo, EstadoPedido estado) {

        if (id <= 0) {
            System.out.println("ID inválido.");
            return;
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            System.out.println("La dirección es obligatoria.");
            return;
        }

        Pedido pedido = new Pedido(id, direccion.trim(), tipo, estado);
        pedidoDAO.actualizar(pedido);
    }

    //eliminar
    public void eliminarPedido(int id) {

        if (id <= 0) {
            System.out.println("ID inválido.");
            return;
        }

        pedidoDAO.borrar(id);
    }

}
