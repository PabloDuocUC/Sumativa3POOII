package dao.impl;

import dao.RepartidorDAO;
import modelo.Repartidor;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAOImpl implements RepartidorDAO{

    @Override
    public void crear(Repartidor repartidor) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            stmt.executeUpdate();

            System.out.println("Repartidor ingresado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al ingresar repartidor.");
            e.printStackTrace();
        }

    }

    @Override
    public List<Repartidor> leerTodos() {

        List<Repartidor> lista = new ArrayList<>();

        String sql = "SELECT * FROM repartidores";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Repartidor r = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al mostrar repartidores.");
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void actualizar(Repartidor repartidor) {

        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            stmt.setInt(2, repartidor.getId());
            stmt.executeUpdate();

            System.out.println("Repartidor actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar repartidor.");
            e.printStackTrace();
        }

    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Repartidor eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor.");
            e.printStackTrace();
        }
    }
}
