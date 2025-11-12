package ort.da.obligatorioDA.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Bonificacion;

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
}
