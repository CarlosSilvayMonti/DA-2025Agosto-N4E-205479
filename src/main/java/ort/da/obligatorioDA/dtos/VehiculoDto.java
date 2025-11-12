package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.Vehiculo;

public class VehiculoDto {
    private String matricula;
    private String modelo;
    private String color;
    private String categoria;
    private int cantidadTransitos;
    private double totalGastado;

    public VehiculoDto(Vehiculo vehiculo) {
        this.matricula = vehiculo.getMatricula();
        this.modelo = vehiculo.getModelo();
        this.color = vehiculo.getColor();
        this.categoria = vehiculo.getCategoria().getTipo();

        // Por ahora sin transitos reales, valores iniciales en 0
        this.cantidadTransitos = 0;
        this.totalGastado = 0;
    }
}
