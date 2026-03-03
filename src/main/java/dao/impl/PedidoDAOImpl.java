package dao.impl;

import dao.PedidoDAO;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO{

    @Override
    public void crear(Pedido pedido) {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo().name());
            stmt.setString(3, pedido.getEstado().name());

            stmt.executeUpdate();

            System.out.println("Pedido ingresado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al ingresar pedido.");
            e.printStackTrace();
        }

    }

    @Override
    public List<Pedido> leerTodos() {

        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos.");
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void actualizar(Pedido pedido) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo().name());
            stmt.setString(3, pedido.getEstado().name());
            stmt.setInt(4, pedido.getId());

            stmt.executeUpdate();

            System.out.println("Pedido actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido.");
            e.printStackTrace();
        }

    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Pedido eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido.");
            e.printStackTrace();
        }
    }
}
