package ort.da.obligatorioDA.modelo;

import ort.da.obligatorioDA.modelo.UsuPorpietario;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SesionPropietario {
    private UsuPorpietario propietario;
    private Date fechaInicio;
    private Date fechaFin;

    public SesionPropietario(UsuPorpietario propietario) {
        this.propietario = propietario;
        this.fechaInicio = new Date();
    }
}
