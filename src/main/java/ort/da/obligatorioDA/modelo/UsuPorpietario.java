package ort.da.obligatorioDA.modelo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Vehiculo;

public class UsuPorpietario extends Usuario{
    @Getter @Setter
    private double saldoActual;
    @Getter @Setter
    private double saldoMinimo;
    @Getter @Setter
    private EstadoPropietario estado;
    @Getter @Setter
    private List<Vehiculo> vehiculos = new ArrayList<>();
    @Getter @Setter
    private List<BonificacionAsignada> bonificacionesAsignadas = new ArrayList<>();
    @Getter @Setter
    private List<Notificacion> notificaciones = new ArrayList<>();

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

    public void acreditarSaldo(double monto) {
        this.saldoActual += monto;
    }

    public void debitarSaldo(double monto) {
        this.saldoActual -= monto;
    }


    /*public void acreditarVehiculo(String matricula, double monto) throws PeajeException {
        Vehiculo v = vehiculos.stream()
            .filter(veh -> veh.getMatricula().equalsIgnoreCase(matricula))
            .findFirst()
            .orElseThrow(() -> new PeajeException("Vehículo no encontrado"));
        v.agregarSaldo(monto);
    }*/

    @Override
    public String toString() {
        return "Propietario: " + nombreCompleto + " (" + cedula + "), saldo: " + saldoActual;
    }

}
