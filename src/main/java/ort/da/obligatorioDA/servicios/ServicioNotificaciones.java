package ort.da.obligatorioDA.servicios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.Notificacion;
import ort.da.obligatorioDA.modelo.UsuPorpietario;

public class ServicioNotificaciones {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // 🔔 Notificación por tránsito
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
    }

    // 🔔 Notificación por saldo bajo
    public void registrarNotificacionSaldoBajo(UsuPorpietario propietario,
            double saldoActual,
            LocalDateTime fechaHora) {

        String mensaje = FMT.format(fechaHora)
                + " - Tu saldo actual es de $ "
                + saldoActual
                + ". Te recomendamos hacer una recarga.";

        propietario.getNotificaciones().add(
                new Notificacion(fechaHora, mensaje));
    }

    // Obtener ordenadas desc por fecha/hora
    public List<Notificacion> obtenerNotificaciones(UsuPorpietario propietario) {
        return propietario.getNotificaciones()
                .stream()
                .sorted(Comparator.comparing(Notificacion::getFechaHora).reversed())
                .collect(Collectors.toList());
    }

    // Borrar todas – devuelve true si había alguna
    public boolean borrarNotificaciones(UsuPorpietario propietario) {
        if (propietario.getNotificaciones().isEmpty()) {
            return false;
        }
        propietario.getNotificaciones().clear();
        return true;
    }

    public void registrarNotificacionCambioEstado(UsuPorpietario propietario, EstadoPropietario nuevoEstado) {
        if (propietario == null || nuevoEstado == null) {
            return;
        }
        String mensaje = "Se ha cambiado tu estado en el sistema. Tu estado actual es " + nuevoEstado;
        registrarNotificacion(propietario.getCedula(), mensaje, LocalDateTime.now());
    }

    private Map<String, List<Notificacion>> notificacionesPorCedula = new HashMap<>();

    private void registrarNotificacion(String cedula, String mensaje, LocalDateTime fechaHora) {
        List<Notificacion> lista = notificacionesPorCedula
                .computeIfAbsent(cedula, k -> new ArrayList<>());
        lista.add(new Notificacion(fechaHora, mensaje));
    }

}
