package com.ventas.administracion.modelo;

import java.util.Date;
import java.util.Map;

public class Comprobante {
    private int id;
    private Cliente cliente;
    private Map<Producto, Integer> productosYcantidades; // Producto y cantidad vendida
    private Date fecha;
    private double total;

    public Comprobante(Cliente cliente, Map<Producto, Integer> productosYcantidades, Date fecha, double total) {
        this.cliente = cliente;
        this.productosYcantidades = productosYcantidades;
        this.fecha = fecha;
        this.total = total;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Map<Producto, Integer> getProductosYcantidades() { return productosYcantidades; }
    public void setProductosYcantidades(Map<Producto, Integer> productosYcantidades) { this.productosYcantidades = productosYcantidades; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
