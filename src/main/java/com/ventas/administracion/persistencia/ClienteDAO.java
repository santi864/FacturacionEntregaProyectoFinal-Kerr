package com.ventas.administracion.persistencia;

import com.ventas.administracion.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public Cliente obtenerClientePorId(int id) {
        String query = "SELECT * FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
