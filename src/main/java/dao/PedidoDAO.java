package dao;

import modelo.Pedido;
import java.util.List;

public interface PedidoDAO {
    void crear(Pedido pedido);

    List<Pedido> leerTodos();

    void actualizar(Pedido pedido);

    void borrar(int id);
}
