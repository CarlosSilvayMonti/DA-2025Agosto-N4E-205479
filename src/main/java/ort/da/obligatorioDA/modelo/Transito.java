package ort.da.obligatorioDA.modelo;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Transito {
    private UsuPorpietario propietario;
    private Vehiculo vehiculo;
    private Puesto puesto;
    private LocalDateTime fechaHora;

    private double montoTarifa;
    private String tipoBonificacion;   // "Exonerados", "Frecuentes", "Trabajadores" o null
    private double montoBonificacion;
    private double montoPagado;

    public Transito(UsuPorpietario propietario, Vehiculo vehiculo, Puesto puesto, LocalDateTime fechaHora,
                    double montoTarifa, String  tipoBonificacion,
                    double montoBonificacion, double montoPagado) {
        this.propietario = propietario;
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.fechaHora = fechaHora;
        this.montoTarifa = montoTarifa;
        this.tipoBonificacion = tipoBonificacion;
        this.montoBonificacion = montoBonificacion;
        this.montoPagado = montoPagado;
    }
}
