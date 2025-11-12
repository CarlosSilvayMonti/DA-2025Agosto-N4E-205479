package ort.da.obligatorioDA.modelo;

import java.util.ArrayList;
import java.util.List;

public class Puesto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifas = new ArrayList<>();

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public List<Tarifa> getTarifas() {
        return tarifas;
    }
    public void agregarTarifa(Tarifa tarifa) {
        tarifas.add(tarifa);
    }
    @Override
    public String toString() {
        return "Puesto: " + nombre + ", Direccion: " + direccion;
    }
}
