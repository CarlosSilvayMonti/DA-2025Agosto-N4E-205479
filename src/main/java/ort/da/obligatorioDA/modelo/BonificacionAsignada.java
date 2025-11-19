package ort.da.obligatorioDA.modelo;

import java.util.Date;
import lombok.Getter;

@Getter
public class BonificacionAsignada {

    private Bonificacion bonificacion;
    private Puesto puesto;
    private Date fechaAsignacion;

    public BonificacionAsignada(Bonificacion bonificacion, Puesto puesto, Date fechaAsignacion) {
        this.bonificacion = bonificacion;
        this.puesto = puesto;
        this.fechaAsignacion = fechaAsignacion;
    }
}