package dao;

import modelo.Repartidor;
import java.util.List;

public interface RepartidorDAO {
    void crear(Repartidor repartidor);

    List<Repartidor> leerTodos();

    void actualizar(Repartidor repartidor);

    void borrar(int id);


}
