package ort.da.obligatorioDA.observador;

public interface Observador {

    public enum Evento {    
        AGENDA_ACTUALIZADA,
        SESION_ACTUALIZADA, 
        NOTIFICACIONES_ACTUALIZADAS
    }

    void actualizar(Observable origen, Object evento);
}
