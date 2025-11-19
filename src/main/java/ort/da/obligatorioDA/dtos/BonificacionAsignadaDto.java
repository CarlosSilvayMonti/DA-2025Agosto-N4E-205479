package ort.da.obligatorioDA.dtos;

import java.text.SimpleDateFormat;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.BonificacionAsignada;

@Getter
public class BonificacionAsignadaDto {

    private String nombreBonificacion;
    private String nombrePuesto;
    private String fechaAsignada;

    public BonificacionAsignadaDto(BonificacionAsignada ba) {
        this.nombreBonificacion = ba.getBonificacion().getNombre();
        this.nombrePuesto = ba.getPuesto().getNombre();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaAsignada = sdf.format(ba.getFechaAsignacion());
    }
}
