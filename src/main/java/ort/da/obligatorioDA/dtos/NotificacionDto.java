package ort.da.obligatorioDA.dtos;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import ort.da.obligatorioDA.modelo.Notificacion;

@Getter
public class NotificacionDto {

    private String fechaHora;
    private String mensaje;

    public NotificacionDto(Notificacion n) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.fechaHora = n.getFechaHora().format(f);
        this.mensaje   = n.getMensaje();
    }
}
