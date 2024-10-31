package com.ventas.administracion.controlador;

// import com.ventas.administracion.modelo.Comprobante;
import com.ventas.administracion.servicio.ComprobanteService;
import com.ventas.administracion.servicio.RespuestaCreacionComprobante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comprobantes")
public class ComprobanteController {
    @Autowired
    private ComprobanteService comprobanteService;

    @PostMapping("/crear")
    public RespuestaCreacionComprobante crearComprobante(
            @RequestParam int clienteId,
            @RequestBody Map<Integer, Integer> productosYcantidades) {
        return comprobanteService.crearComprobante(clienteId, productosYcantidades);
    }
}
