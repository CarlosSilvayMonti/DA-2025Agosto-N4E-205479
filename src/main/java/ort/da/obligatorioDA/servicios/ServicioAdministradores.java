package ort.da.obligatorioDA.servicios;

import java.util.Date;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.PrecargaDatos;
import ort.da.obligatorioDA.modelo.SesionPropietario;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.Usuario;

public class ServicioAdministradores {
    public void agregar(UsuAdmin admin) {
        PrecargaDatos.getAdministradores().add(admin);
    }

}
