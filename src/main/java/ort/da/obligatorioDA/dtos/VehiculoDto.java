package ort.da.obligatorioDA.dtos;

import ort.da.obligatorioDA.modelo.Vehiculo;

public class VehiculoDto {
    private String matricula;
    private String modelo;
    private String color;
    private long transitos;
    private double montoTotalGastado;

    public VehiculoDto(Vehiculo v, long transitos, double gasto) {
        this.matricula = v.getMatricula();
        this.modelo = v.getModelo();
        this.color = v.getColor();
        this.transitos = transitos;
        this.montoTotalGastado = gasto;
    }

    public String getMatricula() { return matricula; }
    public String getModelo() { return modelo; }
    public String getColor() { return color; }
    public long getTransitos() { return transitos; }
    public double getMontoTotalGastado() { return montoTotalGastado; }
}
