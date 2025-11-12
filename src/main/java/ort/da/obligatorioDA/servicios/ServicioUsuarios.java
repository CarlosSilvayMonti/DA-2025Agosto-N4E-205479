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
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.modelo.SesionPropietario;
import ort.da.obligatorioDA.modelo.UsuAdmin;

import java.util.Date;

public class ServicioUsuarios {


    private List<UsuPorpietario> propietarios;
    private List<UsuAdmin> admins;

    @Getter
    private List<Sesion> sesionesActivas;

    public ServicioUsuarios() {
        this.propietarios = new ArrayList<>();
        this.sesionesActivas = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public void agregar(UsuPorpietario propietario) {
        propietarios.add(propietario);
    }

    public void agregar(UsuAdmin admin) {
        admins.add(admin);
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

        return sesion;
    }

    public UsuAdmin loginAdministrador(String cedula, String contrasenia) throws PeajeException {
        return (UsuAdmin) login(cedula, contrasenia, admins, "Administrador y/o contraseña incorrectos");
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

    public UsuPorpietario buscarPorCedula(String cedula) throws PeajeException {
        for (UsuPorpietario p : propietarios) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        throw new PeajeException("No existe el propietario con la cédula " + cedula);
    }

    public void cambiarEstado(String cedula, EstadoPropietario nuevoEstado) throws PeajeException {
        UsuPorpietario propietario = buscarPorCedula(cedula);
        if (propietario.getEstado() == nuevoEstado) {
            throw new PeajeException("El propietario ya está en estado " + nuevoEstado);
        }
        propietario.setEstado(nuevoEstado);
    }

    public void logout(Sesion sesion) {
        sesionesActivas.remove(sesion);
        /*falta el observador */
    }
}
