package com.ventas.administracion.servicio;

import com.ventas.administracion.modelo.Comprobante;
import java.util.List;

public class RespuestaCreacionComprobante {
    private Comprobante comprobante;
    private List<String> errores;

    public RespuestaCreacionComprobante(Comprobante comprobante, List<String> errores) {
        this.comprobante = comprobante;
        this.errores = errores;
    }

    public Comprobante getComprobante() { return comprobante; }
    public void setComprobante(Comprobante comprobante) { this.comprobante = comprobante; }

    public List<String> getErrores() { return errores; }
    public void setErrores(List<String> errores) { this.errores = errores; }
}
