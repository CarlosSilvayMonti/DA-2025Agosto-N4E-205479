package ort.da.obligatorioDA.servicios.fachada;

import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;

import ort.da.obligatorioDA.modelo.Bonificacion;
import ort.da.obligatorioDA.modelo.Categoria;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.PrecargaDatos;
import ort.da.obligatorioDA.modelo.Puesto;
import ort.da.obligatorioDA.modelo.Sesion;
import ort.da.obligatorioDA.servicios.*;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.excepciones.PeajeException;


public class FachadaServicios {

    private static FachadaServicios instancia;
    private ServicioAdministradores sAdministradores;
    private ServicioUsuarios sPropietarios;
    private ServicioPuestos sPuestos;
    private ServicioCategorias sCategorias;
    private ServicioBonificaciones sBonificaciones;
    private ServicioUsuarios sUsuarios;

    private FachadaServicios() {
        sAdministradores = new ServicioAdministradores();
        sPropietarios = new ServicioUsuarios();
        sPuestos = new ServicioPuestos();
        sCategorias = new ServicioCategorias();
        sBonificaciones = new ServicioBonificaciones();
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

    /*public UsuAdmin loginAdministrador(String cedula, String contrasenia) throws PeajeException {
        return sAdministradores.loginAdministrador(cedula, contrasenia);
    }*/

    public PropietarioDto obtenerTableroPropietario(String cedula) throws PeajeException {
    UsuPorpietario propietario = sPropietarios.buscarPorCedula(cedula);
    if (propietario == null) {
        throw new PeajeException("No existe un propietario con la cédula indicada.");
    }
        return new PropietarioDto(propietario);
    }

    public void logout(Sesion sesion) {
        sUsuarios.logout(sesion);
    }

}
