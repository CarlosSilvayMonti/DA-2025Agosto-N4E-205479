package ort.da.obligatorioDA.servicios.fachada;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;

import ort.da.obligatorioDA.modelo.Bonificacion;
import ort.da.obligatorioDA.modelo.Categoria;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.Notificacion;
import ort.da.obligatorioDA.modelo.PrecargaDatos;
import ort.da.obligatorioDA.modelo.Puesto;
import ort.da.obligatorioDA.modelo.Sesion;
import ort.da.obligatorioDA.modelo.Transito;
import ort.da.obligatorioDA.servicios.*;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.modelo.Vehiculo;
import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.dtos.TransitoDto;
import ort.da.obligatorioDA.dtos.VehiculoDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.dtos.BonificacionAsignadaDto;
import ort.da.obligatorioDA.dtos.NotificacionDto;

public class FachadaServicios {

    private static FachadaServicios instancia;
    private ServicioAdministradores sAdministradores;
    private ServicioUsuarios sPropietarios;
    private ServicioPuestos sPuestos;
    private ServicioCategorias sCategorias;
    private ServicioBonificaciones sBonificaciones;
    private ServicioTransitos sTransitos;
    private ServicioNotificaciones sNotificaciones;

    private FachadaServicios() {
        sAdministradores = new ServicioAdministradores();
        sPropietarios = new ServicioUsuarios();
        sPuestos = new ServicioPuestos();
        sCategorias = new ServicioCategorias();
        sBonificaciones = new ServicioBonificaciones();
        sTransitos = new ServicioTransitos();
        sNotificaciones = new ServicioNotificaciones();
    }

    public static FachadaServicios getInstancia() {
        if (instancia == null) {
            instancia = new FachadaServicios();
        }
        return instancia;
    }

    // ==================== AGREGAR ====================
    public void agregar(UsuAdmin admin) throws PeajeException {
        sAdministradores.agregar(admin);
    }

    public void agregar(UsuPorpietario propietario) throws PeajeException {
        sPropietarios.agregar(propietario);
    }

    public void agregar(Puesto puesto) throws PeajeException {
        sPuestos.agregar(puesto);
    }

    public void agregar(Categoria categoria) throws PeajeException {
        sCategorias.agregar(categoria);
    }

    public void agregar(Bonificacion bonificacion) throws PeajeException {
        sBonificaciones.agregar(bonificacion);
    }

    // ==================== LOGIN ====================
    public Sesion loginPropietario(String cedula, String contrasenia) throws PeajeException {
        return sPropietarios.loginPropietario(cedula, contrasenia);
    }

    public UsuAdmin loginAdministrador(String cedula, String contrasenia) throws PeajeException {
        return sAdministradores.loginAdministrador(cedula, contrasenia);
    }

    /*
     * public UsuAdmin loginAdministrador(String cedula, String contrasenia) throws
     * PeajeException {
     * return sAdministradores.loginAdministrador(cedula, contrasenia);
     * }
     */

    public PropietarioDto obtenerTableroPropietario(String cedula) throws PeajeException {
        UsuPorpietario propietario = sPropietarios.buscarPorCedula(cedula);
        if (propietario == null) {
            throw new PeajeException("No existe un propietario con la cédula indicada.");
        }
        return new PropietarioDto(propietario);
    }

    public void asignarBonificacion(String cedulaPropietario, String nombreBonificacion, String nombrePuesto)
            throws PeajeException {
        UsuPorpietario propietario = sPropietarios.buscarPorCedula(cedulaPropietario);
        Bonificacion bonificacion = sBonificaciones.buscarPorNombre(nombreBonificacion);
        Puesto puesto = sPuestos.buscarPorNombre(nombrePuesto);

        sBonificaciones.asignarBonificacion(propietario, bonificacion, puesto);
    }

    public Transito registrarTransito(String cedula, String matricula, String nombrePuesto, LocalDateTime fechaHora)
            throws PeajeException {

        UsuPorpietario p = sPropietarios.buscarPorCedula(cedula);
        Vehiculo v = sPropietarios.buscarVehiculoDe(p, matricula); // ← TIPO Vehiculo
        Puesto puesto = sPuestos.buscarPorNombre(nombrePuesto);

        return sTransitos.registrarTransito(p, v, puesto, fechaHora);
    }

    /*
     * public void acreditarVehiculo(String cedula, String matricula, double monto)
     * throws PeajeException {
     * sPropietarios.acreditarVehiculo(cedula, matricula, monto);
     * }
     */

    public List<String> nombresBonificaciones() {
        return sBonificaciones.getBonificaciones()
                .stream()
                .map(Bonificacion::getNombre)
                .toList();
    }

    public List<VehiculoDto> vehiculosResumen(String cedula) throws PeajeException {
        UsuPorpietario p = sPropietarios.buscarPorCedula(cedula);
        List<VehiculoDto> list = new ArrayList<>();
        for (Vehiculo v : p.getVehiculos()) {
            long cant = sTransitos.cantidadTransitos(p, v); // devuelve el número real
            double gasto = sTransitos.totalGastado(p, v); // suma real
            list.add(new VehiculoDto(v, cant, gasto));
        }
        return list;
    }

    public List<TransitoDto> transitosDe(String cedula) throws PeajeException {
        UsuPorpietario p = sPropietarios.buscarPorCedula(cedula);
        return sTransitos.transitosDe(p).stream().map(TransitoDto::new).toList();
    }

    public UsuPorpietario buscarPorCedula(String cedula) throws PeajeException {
        return sPropietarios.buscarPorCedula(cedula);
    }

    public List<TransitoDto> transitosDePropietario(String cedula) {
        return sTransitos.obtenerTransitosDePropietario(cedula)
                .stream()
                .map(TransitoDto::new)
                .collect(Collectors.toList());
    }

    public List<NotificacionDto> notificacionesDePropietario(String cedula) {
        UsuPorpietario p = sPropietarios.buscarPorCedula(cedula);

        return sNotificaciones.obtenerNotificaciones(p)
                .stream()
                .map(NotificacionDto::new)
                .collect(Collectors.toList());
    }

    public boolean borrarNotificaciones(UsuPorpietario p) {
        return sNotificaciones.borrarNotificaciones(p);
    }

    public void logout(Sesion sesion) {
        sPropietarios.logout(sesion);
    }

    public Transito emularTransito(String matricula, String nombrePuesto, LocalDateTime fechaHora)
            throws PeajeException {

        // 1) Buscar propietario a partir de la matrícula
        UsuPorpietario p = sPropietarios.buscarPropietarioPorMatricula(matricula);

        // 2) Buscar vehículo de ese propietario
        Vehiculo v = sPropietarios.buscarVehiculoDe(p, matricula);

        // 3) Buscar puesto
        Puesto puesto = sPuestos.buscarPorNombre(nombrePuesto);

        // 4) Registrar tránsito (aplica descuentos, valida saldo, genera
        // notificaciones, etc.)
        return sTransitos.registrarTransito(p, v, puesto, fechaHora);
    }

    public List<Puesto> getPuestos() {
        return sPuestos.getPuestos();
    }

    public Puesto buscarPuestoPorNombre(String nombre) throws PeajeException {
        return sPuestos.buscarPorNombre(nombre);
    }

    // En FachadaServicios
    public List<Bonificacion> getBonificacionesDto() {
        return sBonificaciones.getBonificaciones();
    }

    public List<Puesto> getPuestosDto() {
        return sPuestos.getPuestos();
    }

    public void cambiarEstadoPropietario(String cedula, EstadoPropietario nuevoEstado) throws PeajeException {
        UsuPorpietario propietario = sPropietarios.buscarPorCedula(cedula);
        sPropietarios.cambiarEstado(cedula, nuevoEstado);
        // Siempre registra notificación, según enunciado
        sNotificaciones.registrarNotificacionCambioEstado(propietario, nuevoEstado);
    }

}
