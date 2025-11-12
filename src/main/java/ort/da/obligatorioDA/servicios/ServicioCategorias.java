package ort.da.obligatorioDA.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Categoria;

public class ServicioCategorias {
    private List<Categoria> categorias;

    public ServicioCategorias() {
        this.categorias = new ArrayList<>();
    }

    public void agregar(Categoria categoria) throws PeajeException {
        if (categoria == null) {
            throw new PeajeException("La categoría no puede ser nula");
        }

        for (Categoria c : categorias) {
            if (c.getTipo().equalsIgnoreCase(categoria.getTipo())) {
                throw new PeajeException("Ya existe una categoría con el tipo: " + categoria.getTipo());
            }
        }

        categorias.add(categoria);
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Categoria buscarPorTipo(String tipo) throws PeajeException {
        for (Categoria c : categorias) {
            if (c.getTipo().equalsIgnoreCase(tipo)) {
                return c;
            }
        }
        throw new PeajeException("No existe la categoría: " + tipo);
    }
}
