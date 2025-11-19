package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.Puesto;

@Getter
public class PuestoDto {
    private String nombre;

    public PuestoDto(Puesto p) {
        this.nombre = p.getNombre();
    }
}
