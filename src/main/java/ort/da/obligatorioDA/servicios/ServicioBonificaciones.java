package ort.da.obligatorioDA.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Bonificacion;
import ort.da.obligatorioDA.modelo.BonificacionAsignada;
import ort.da.obligatorioDA.modelo.Puesto;
import ort.da.obligatorioDA.modelo.UsuPorpietario;

public class ServicioBonificaciones {
    private List<Bonificacion> bonificaciones;

    public ServicioBonificaciones() {
        this.bonificaciones = new ArrayList<>();
    }

    public void agregar(Bonificacion bonificacion) throws PeajeException {
        if (bonificacion == null) {
            throw new PeajeException("La bonificación no puede ser nula");
        }

        for (Bonificacion b : bonificaciones) {
            if (b.getNombre().equalsIgnoreCase(bonificacion.getNombre())) {
                throw new PeajeException("Ya existe una bonificación con el nombre: " + bonificacion.getNombre());
            }
        }

        bonificaciones.add(bonificacion);
    }

    public List<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public Bonificacion buscarPorNombre(String nombre) throws PeajeException {
        for (Bonificacion b : bonificaciones) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {
                return b;
            }
        }
        throw new PeajeException("No existe la bonificación: " + nombre);
    }

    public void asignarBonificacion(UsuPorpietario propietario, Bonificacion bonificacion, Puesto puesto)
            throws PeajeException {
        if (propietario == null || bonificacion == null || puesto == null) {
            throw new PeajeException("Datos incompletos para asignar bonificación");
        }

        boolean yaAsignada = propietario.getBonificacionesAsignadas().stream()
                .anyMatch(ba -> ba.getBonificacion().getNombre().equalsIgnoreCase(bonificacion.getNombre())
                        && ba.getPuesto().equals(puesto));

        if (yaAsignada) {
            throw new PeajeException("La bonificación ya está asignada a este propietario para el puesto especificado");
        }

        propietario.getBonificacionesAsignadas().add(
                new BonificacionAsignada(bonificacion, puesto, new java.util.Date()));

    }

    public List<String> nombresBonificaciones() {
        return bonificaciones.stream().map(Bonificacion::getNombre).toList();
    }



}
