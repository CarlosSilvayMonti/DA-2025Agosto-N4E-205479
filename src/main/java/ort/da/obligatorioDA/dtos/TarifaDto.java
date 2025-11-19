package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.Tarifa;  

@Getter
public class TarifaDto {
    private String categoria;
    private double monto;

    public TarifaDto(Tarifa t) {
        this.categoria = t.getCategoria().toString(); // o getDescripcion()
        this.monto = t.getMonto();
    }
}
