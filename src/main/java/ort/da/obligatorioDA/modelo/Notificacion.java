package ort.da.obligatorioDA.modelo;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Notificacion {
    private LocalDateTime fechaHora;
    private String mensaje;

    public Notificacion(LocalDateTime fechaHora, String mensaje) {
        this.fechaHora = fechaHora;
        this.mensaje = mensaje;
    }
}
