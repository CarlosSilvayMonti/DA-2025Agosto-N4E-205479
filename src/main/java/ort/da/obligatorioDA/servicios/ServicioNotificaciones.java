package ort.da.obligatorioDA.servicios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.Notificacion;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.observador.Observable;
import ort.da.obligatorioDA.observador.Observador;

public class ServicioNotificaciones extends Observable {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void registrarNotificacionTransito(UsuPorpietario propietario,
            String nombrePuesto,
            String matriculaVehiculo,
            LocalDateTime fechaHora) {

        String mensaje = FMT.format(fechaHora)
                + " - Pasaste por el puesto "
                + nombrePuesto
                + " con el vehículo "
                + matriculaVehiculo;

        propietario.getNotificaciones().add(
                new Notificacion(fechaHora, mensaje));

        notificar(Observador.Evento.NOTIFICACIONES_ACTUALIZADAS);
    }

    public void registrarNotificacionSaldoBajo(UsuPorpietario propietario,
            double saldoActual,
            LocalDateTime fechaHora) {

        String mensaje = FMT.format(fechaHora)
                + " - Tu saldo actual es de $ "
                + saldoActual
                + ". Te recomendamos hacer una recarga.";

        propietario.getNotificaciones().add(
                new Notificacion(fechaHora, mensaje));

        notificar(Observador.Evento.NOTIFICACIONES_ACTUALIZADAS);
    }

    public void registrarNotificacionCambioEstado(UsuPorpietario propietario,
                                                  EstadoPropietario nuevoEstado) {

        if (propietario == null || nuevoEstado == null) return;

        LocalDateTime ahora = LocalDateTime.now();

        String mensaje = FMT.format(ahora)
                + " - Se ha cambiado tu estado en el sistema. Tu estado actual es "
                + nuevoEstado;

        propietario.getNotificaciones().add(
                new Notificacion(ahora, mensaje)
        );
        notificar(Observador.Evento.NOTIFICACIONES_ACTUALIZADAS);
    }

    public List<Notificacion> obtenerNotificaciones(UsuPorpietario propietario) {
        return propietario.getNotificaciones()
                .stream()
                .sorted(Comparator.comparing(Notificacion::getFechaHora).reversed())
                .collect(Collectors.toList());
    }

    public boolean borrarNotificaciones(UsuPorpietario propietario) {
        if (propietario.getNotificaciones().isEmpty()) {
            return false;
        }
        propietario.getNotificaciones().clear();
        notificar(Observador.Evento.NOTIFICACIONES_ACTUALIZADAS);

        return true;
    }
}
