package ort.da.obligatorioDA.dtos;

import java.util.List;

public class EventoNotificacionesDto {
    private String cedula;
    private List<NotificacionDto> notificaciones;

    public EventoNotificacionesDto(String cedula, List<NotificacionDto> notificaciones) {
        this.cedula = cedula;
        this.notificaciones = notificaciones;
    }

    public String getCedula() {return cedula; }
    public List<NotificacionDto> getNotificaciones() { return notificaciones; }
}
