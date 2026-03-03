package dao.impl;

import dao.EntregaDAO;
import modelo.Entrega;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAOImpl implements EntregaDAO{

    @Override
    public void crear(Entrega entrega) {

        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, entrega.getFecha());
            stmt.setTime(4, entrega.getHora());

            stmt.executeUpdate();

            System.out.println("Entrega ingresada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al ingresar entrega.");
            e.printStackTrace();
        }
    }

    @Override
    public List<Entrega> leerTodos() {

        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregas";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entrega e = new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha"),
                        rs.getTime("hora")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar entregas.");
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void actualizar(Entrega entrega) {

        String sql = "UPDATE entregas SET id_pedido=?, id_repartidor=?, fecha=?, hora=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, entrega.getFecha());
            stmt.setTime(4, entrega.getHora());
            stmt.setInt(5, entrega.getId());

            stmt.executeUpdate();

            System.out.println("Entrega actualizada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar entrega.");
            e.printStackTrace();
        }
    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM entregas WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Entrega eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar entrega.");
            e.printStackTrace();
        }

    }
}
