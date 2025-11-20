package ort.da.obligatorioDA.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.UsuAdmin;

public class ServicioAdministradores {
    private List<UsuAdmin> admins;

    public ServicioAdministradores() {
        this.admins = new ArrayList<>();
    }

    public void agregar(UsuAdmin admin) throws PeajeException {
        if (admin == null) {
            throw new PeajeException("El administrador no puede ser nulo");
        }
        for (UsuAdmin a : admins) {
            if (a.getCedula().equals(admin.getCedula())) {
                throw new PeajeException("Ya existe un administrador con esa cédula");
            }
        }
        admins.add(admin);
    }


    public UsuAdmin loginAdministrador(String cedula, String contrasenia) throws PeajeException {
        for (UsuAdmin admin : admins) {
            if (admin.getCedula().equals(cedula) && admin.autenticar(cedula, contrasenia)) {
                return admin;
            }
        }
        throw new PeajeException("Acceso denegado");
    }

}
