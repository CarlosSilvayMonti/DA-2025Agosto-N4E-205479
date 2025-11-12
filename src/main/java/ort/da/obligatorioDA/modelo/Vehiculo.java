package ort.da.obligatorioDA.modelo;

public class Vehiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private Categoria categoria;

    public Vehiculo(String matricula, String marca, String modelo, String color, Categoria categoria) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
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

    @Override
    public String toString() {
        return "Vehiculo: " + marca + " " + modelo + " (" + matricula + "), Color: " + color + ", Categoria: " + categoria.getTipo();
    }

}
