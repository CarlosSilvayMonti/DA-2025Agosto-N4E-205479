package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.Bonificacion;

@Getter
public class BonificacionDto {
    private String nombre;

    public BonificacionDto(Bonificacion bonificacion) {
        this.nombre = bonificacion.getNombre();
    }
}
