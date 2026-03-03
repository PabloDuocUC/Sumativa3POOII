package controlador;

import dao.RepartidorDAO;
import dao.impl.RepartidorDAOImpl;
import modelo.Repartidor;

import java.util.List;

public class ControladorRepartidor {

    private RepartidorDAO repartidorDAO;

    public ControladorRepartidor() {
        this.repartidorDAO = new RepartidorDAOImpl();
    }

    public void registrarRepartidor(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre no puede ser vacío.");
            return;
        }

        Repartidor repartidor = new Repartidor();
        repartidor.setNombre(nombre.trim());

        repartidorDAO.crear(repartidor);
    }

    public List<Repartidor> listarRepartidores() {
        return repartidorDAO.leerTodos();
    }

    public void actualizarRepartidor(int id, String nombre) {

        if (id <= 0 || nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Datos inválidos para actualizar.");
            return;
        }

        Repartidor repartidor = new Repartidor(id, nombre.trim());
        repartidorDAO.actualizar(repartidor);
    }

    public void eliminarRepartidor(int id) {

        if (id <= 0) {
            System.out.println("ID inválido.");
            return;
        }

        repartidorDAO.borrar(id);
    }

}
