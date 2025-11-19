package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.Transito;
import ort.da.obligatorioDA.modelo.UsuPorpietario;

@Getter
public class ResultadoTransitoDto {

    private String nombrePropietario;
    private String estadoPropietario;
    private String nombrePuesto;
    private String matricula;
    private String categoria;
    private String bonificacion;       // nombre o vacío
    private double montoTarifa;
    private double montoBonificacion;
    private double montoPagado;
    private double saldoPosterior;

    public ResultadoTransitoDto(Transito t) {
        UsuPorpietario p = t.getPropietario();

        this.nombrePropietario  = p.getNombreCompleto();
        this.estadoPropietario  = p.getEstado().toString();
        this.nombrePuesto       = t.getPuesto().getNombre();
        this.matricula          = t.getVehiculo().getMatricula();
        this.categoria          = t.getVehiculo().getCategoria().toString(); // o getDescripcion()

        this.bonificacion       = t.getTipoBonificacion() != null ? t.getTipoBonificacion() : "";
        this.montoTarifa        = t.getMontoTarifa();
        this.montoBonificacion  = t.getMontoBonificacion();
        this.montoPagado        = t.getMontoPagado();

        // Saldo actual del propietario después del tránsito
        this.saldoPosterior     = p.getSaldoActual();
    }
}