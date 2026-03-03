package dao;

import modelo.Entrega;
import java.util.List;

public interface EntregaDAO {

    void crear(Entrega entrega);

    List<Entrega> leerTodos();

    void actualizar(Entrega entrega);

    void borrar(int id);

}
