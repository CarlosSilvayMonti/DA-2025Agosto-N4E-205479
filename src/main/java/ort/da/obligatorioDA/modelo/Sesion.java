package ort.da.obligatorioDA.modelo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Sesion {
    @Getter
    private UsuPorpietario prop;
    @Setter
    @Getter
    private Date fechaInicio;

    public Sesion(UsuPorpietario prop) {
        this.prop = prop;
    }
}
