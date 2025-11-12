package ort.da.obligatorioDA.modelo;

public class Tarifa {
    private Categoria categoria;
    private double monto;

    public Tarifa(Categoria categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public double getMonto() {
        return monto;
    }
    
}
