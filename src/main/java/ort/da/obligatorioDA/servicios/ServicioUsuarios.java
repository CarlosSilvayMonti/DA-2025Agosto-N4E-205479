package ort.da.obligatorioDA.servicios;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.PrecargaDatos;
import ort.da.obligatorioDA.modelo.Sesion;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.modelo.Usuario;
import ort.da.obligatorioDA.modelo.Vehiculo;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.modelo.SesionPropietario;
import ort.da.obligatorioDA.modelo.UsuAdmin;

import java.util.Date;

public class ServicioUsuarios {


    private List<UsuPorpietario> propietarios;

    @Getter
    private List<Sesion> sesionesActivas;

    public ServicioUsuarios() {
        this.propietarios = new ArrayList<>();
        this.sesionesActivas = new ArrayList<>();
    }

    public void agregar(UsuPorpietario propietario) {
        propietarios.add(propietario);
    }

    public Sesion loginPropietario(String cedula, String contrasenia) throws PeajeException {
        UsuPorpietario propietario = (UsuPorpietario) login(cedula, contrasenia, propietarios, "Propietario y/o contraseña incorrectos");

        Sesion sesion = new Sesion(propietario);
        sesion.setFechaInicio(new Date());
        sesionesActivas.add(sesion);

        // Validación de estado
        if (propietario.getEstado() == EstadoPropietario.DESHABILITADO) {
            throw new PeajeException("Usuario deshabilitado, no puede ingresar al sistema.");
        }

        System.out.println("Vehículos del propietario al loguear: " + propietario.getVehiculos().size());

        return sesion;
    }


    private Usuario login(String cedula, String contrasenia, List<? extends Usuario> usuarios, String mensajeError)
            throws PeajeException {

        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula) && usuario.autenticar(cedula, contrasenia)) {
                return usuario;
            }
        }
        throw new PeajeException(mensajeError);
    }

    public UsuPorpietario buscarPorCedula(String cedula) {
        for (UsuPorpietario p : propietarios) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    public Vehiculo buscarVehiculoDe(UsuPorpietario propietario, String matricula) throws PeajeException {
        return propietario.getVehiculos().stream()
                .filter(v -> v.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new PeajeException("Vehículo no encontrado: " + matricula));
    }

    // Buscar propietario por matrícula
    public UsuPorpietario buscarPropietarioPorMatricula(String matricula) throws PeajeException {
        for (UsuPorpietario p : propietarios) {
            if (p.getVehiculos() != null) {
                for (Vehiculo v : p.getVehiculos()) {
                    if (v.getMatricula().equalsIgnoreCase(matricula)) {
                        return p;
                    }
                }
            }
        }
        throw new PeajeException("No existe el vehículo");
    }


    public void cambiarEstado(String cedula, EstadoPropietario nuevoEstado) throws PeajeException {
        UsuPorpietario propietario = buscarPorCedula(cedula);
        if (propietario.getEstado() == nuevoEstado) {
            throw new PeajeException("El propietario ya está en estado " + nuevoEstado);
        }
        propietario.setEstado(nuevoEstado);
    }

    /*public void acreditarVehiculo(String cedulaPropietario, String matriculaVehiculo, double monto) throws PeajeException {
        UsuPorpietario propietario = buscarPorCedula(cedulaPropietario);
        propietario.acreditarVehiculo(matriculaVehiculo, monto);
    }*/

    public void registrarTransito(String cedula, String matricula, double montoTarifaFinal) throws PeajeException {
        UsuPorpietario p = buscarPorCedula(cedula);
        Vehiculo v = p.getVehiculos().stream()
            .filter(veh -> veh.getMatricula().equalsIgnoreCase(matricula))
            .findFirst()
            .orElseThrow(() -> new PeajeException("Vehículo no encontrado: " + matricula));

        if (v.getSaldo() < montoTarifaFinal) {
            throw new PeajeException("Saldo insuficiente en " + v.getMatricula() + " (saldo: " + v.getSaldo() + ")");
        }
        v.descontarSaldo(montoTarifaFinal);
    }

    public void logout(Sesion sesion) {
        sesionesActivas.remove(sesion);
        /*falta el observador */
    }

}
