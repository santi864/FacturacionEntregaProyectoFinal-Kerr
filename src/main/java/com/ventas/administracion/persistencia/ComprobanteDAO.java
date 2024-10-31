package com.ventas.administracion.persistencia;

import com.ventas.administracion.modelo.Comprobante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComprobanteDAO {
    private Connection connection;

    public ComprobanteDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardarComprobante(Comprobante comprobante) {
        String query = "INSERT INTO comprobante (cliente_id, fecha, total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, comprobante.getCliente().getId());
            stmt.setDate(2, new java.sql.Date(comprobante.getFecha().getTime()));
            stmt.setDouble(3, comprobante.getTotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
