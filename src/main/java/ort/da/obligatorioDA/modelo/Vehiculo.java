package ort.da.obligatorioDA.modelo;

public class Vehiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private Categoria categoria;
    private double saldo;
    private int cantidadTransitos;
    private double montoTotalGastado;

    public Vehiculo(String matricula, String marca, String modelo, String color, Categoria categoria) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
        this.saldo = 0.0;
        this.cantidadTransitos = 0;
        this.montoTotalGastado = 0.0;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }
    
    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void agregarSaldo(double monto) {
        this.saldo += monto;
    }

    public void descontarSaldo(double montoTarifaFinal) {
        this.saldo -= montoTarifaFinal;
        this.montoTotalGastado += montoTarifaFinal;
        this.cantidadTransitos++;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getCantidadTransitos() {
        return cantidadTransitos;
    }
    
    public double getMontoTotalGastado() {
        return montoTotalGastado;
    }

    @Override
    public String toString() {
        return "Vehiculo: " + marca + " " + modelo + " (" + matricula + "), Color: " + color + ", Categoria: " + categoria.getTipo();
    }

}
