package ort.da.obligatorioDA.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Puesto;

public class ServicioPuestos {
    private List<Puesto> puestos;

    public ServicioPuestos() {
        this.puestos = new ArrayList<>();
    }

    public void agregar(Puesto puesto) throws PeajeException {
        if (puesto == null) {
            throw new PeajeException("El puesto no puede ser nulo");
        }

        for (Puesto p : puestos) {
            if (p.getNombre().equalsIgnoreCase(puesto.getNombre())) {
                throw new PeajeException("Ya existe un puesto con el nombre: " + puesto.getNombre());
            }
        }

        puestos.add(puesto);
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public Puesto buscarPorNombre(String nombre) throws PeajeException {
        for (Puesto p : puestos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        throw new PeajeException("No existe el puesto: " + nombre);
    }
}
