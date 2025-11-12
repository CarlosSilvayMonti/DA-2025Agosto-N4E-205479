package ort.da.obligatorioDA.modelo;

import java.util.ArrayList;
import java.util.List;

public class UsuPorpietario extends Usuario{
    private double saldoActual;
    private double saldoMinimo;
    private EstadoPropietario estado;
    private List<Vehiculo> vehiculos = new ArrayList<>();

    public UsuPorpietario(String cedula, String contrasenia, String nombreCompleto, double saldoActual, double saldoMinimo, EstadoPropietario estado) {
        super(cedula, contrasenia, nombreCompleto);
        this.saldoActual = saldoActual;
        this.saldoMinimo = saldoMinimo;
        this.estado = estado;
    }

    @Override
    public String getTipoUsuario() {
        return "Propietario";
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public EstadoPropietario getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropietario estado) {
        this.estado = estado;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }

    @Override
    public String toString() {
        return "Propietario: " + nombreCompleto + " (" + cedula + "), saldo: " + saldoActual;
    }

}
