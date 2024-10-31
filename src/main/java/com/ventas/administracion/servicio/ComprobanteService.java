package com.ventas.administracion.servicio;

import com.ventas.administracion.modelo.Cliente;
import com.ventas.administracion.modelo.Comprobante;
import com.ventas.administracion.modelo.Producto;
import com.ventas.administracion.persistencia.ClienteDAO;
import com.ventas.administracion.persistencia.ComprobanteDAO;
import com.ventas.administracion.persistencia.ProductoDAO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import org.json.JSONObject;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class ComprobanteService {
    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;
    private ComprobanteDAO comprobanteDAO;

    public ComprobanteService(ClienteDAO clienteDAO, ProductoDAO productoDAO, ComprobanteDAO comprobanteDAO) {
        this.clienteDAO = clienteDAO;
        this.productoDAO = productoDAO;
        this.comprobanteDAO = comprobanteDAO;
    }

    public RespuestaCreacionComprobante crearComprobante(int clienteId, Map<Integer, Integer> productosYcantidades) {
        List<String> errores = new ArrayList<>();
        double precioTotal = 0.0;
        int totalProductosVendidos = 0;
        Date fechaComprobante = obtenerFechaActual();

        // Validación del cliente
        Cliente cliente = clienteDAO.obtenerClientePorId(clienteId);
        if (cliente == null) {
            errores.add("El cliente no existe.");
            return new RespuestaCreacionComprobante(null, errores);
        }

        // Validación y proceso de productos
        Map<Producto, Integer> productosValidados = new HashMap<>();
        for (Map.Entry<Integer, Integer> entrada : productosYcantidades.entrySet()) {
            Producto producto = productoDAO.obtenerProductoPorId(entrada.getKey());
            if (producto == null) {
                errores.add("Producto con ID " + entrada.getKey() + " no existe.");
                continue;
            }
            if (producto.getStock() < entrada.getValue()) {
                errores.add("Stock insuficiente para el producto " + producto.getNombre() + ".");
                continue;
            }
            // Agregamos producto validado y reducimos stock
            productosValidados.put(producto, entrada.getValue());
            producto.setStock(producto.getStock() - entrada.getValue());
            productoDAO.actualizarProducto(producto);
            // Calcular precio total y cantidad de productos
            precioTotal += producto.getPrecio() * entrada.getValue();
            totalProductosVendidos += entrada.getValue();
        }

        if (!errores.isEmpty()) {
            return new RespuestaCreacionComprobante(null, errores);
        }

        // Creación y guardado del comprobante
        Comprobante comprobante = new Comprobante(cliente, productosValidados, fechaComprobante, precioTotal);
        comprobanteDAO.guardarComprobante(comprobante);
        return new RespuestaCreacionComprobante(comprobante, errores);
    }

    private Date obtenerFechaActual() {
        try {
            URL url = new URL("http://worldclockapi.com/api/json/utc/now");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = conn.getInputStream();
                Scanner scanner = new Scanner(responseStream);
                String jsonResponse = scanner.useDelimiter("\\A").next();
                scanner.close();

                JSONObject json = new JSONObject(jsonResponse);
                String dateString = json.getString("currentDateTime");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                return formatter.parse(dateString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // En caso de fallo, retornar la fecha actual usando Date
        return new Date();
    }
}
