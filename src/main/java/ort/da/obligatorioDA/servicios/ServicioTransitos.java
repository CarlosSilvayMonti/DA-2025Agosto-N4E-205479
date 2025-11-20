package ort.da.obligatorioDA.servicios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.BonificacionAsignada;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.Puesto;
import ort.da.obligatorioDA.modelo.Tarifa;
import ort.da.obligatorioDA.modelo.Transito;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.modelo.Vehiculo;

public class ServicioTransitos {
    @Getter
    private List<Transito> transitos = new ArrayList<>();
    private ServicioNotificaciones sNotificaciones;

    public ServicioTransitos() {
        sNotificaciones = new ServicioNotificaciones();
    }

    public Transito registrarTransito(UsuPorpietario p, Vehiculo v, Puesto puesto, LocalDateTime fechaHora)
            throws PeajeException {


        if (p.getEstado() == EstadoPropietario.DESHABILITADO){
            throw new PeajeException("El propietario del vehículo está deshabilitado, no puede realizar tránsito");
        }
        
        if (p.getEstado() == EstadoPropietario.SUSPENDIDO){
            throw new PeajeException("El usuario esta suspendido, no puede realizar tánsitos");
        }

        double tarifaBase = puesto.getTarifas().stream()
                .filter(t -> t.getCategoria().equals(v.getCategoria()))
                .findFirst()
                .orElseThrow(() -> new PeajeException("No hay tarifa para la categoría del vehículo en este puesto"))
                .getMonto();

        Descuento desc; 

        if (p.getEstado() == EstadoPropietario.PENALIZADO){
            desc = new Descuento(null, 0);
        }else{
            desc = calcularDescuento(p, v, puesto, fechaHora, tarifaBase);
        }

        double pagado = Math.max(0, tarifaBase - desc.monto);

        if (p.getSaldoActual() < pagado) {
            throw new PeajeException("Saldo insuficiente: $" + p.getSaldoActual());
        }

        p.debitarSaldo(pagado);

        Transito t = new Transito(
                p, v, puesto, fechaHora,
                tarifaBase,
                desc.tipo,
                desc.monto,
                pagado);
        transitos.add(t);

        if (p.getEstado() != EstadoPropietario.PENALIZADO){
            sNotificaciones.registrarNotificacionTransito(
                p,
                puesto.getNombre(),
                v.getMatricula(),
                fechaHora);

            if (p.getSaldoActual() < p.getSaldoMinimo()) {
                sNotificaciones.registrarNotificacionSaldoBajo(
                        p,
                        p.getSaldoActual(),
                        fechaHora);
            }
        }
        
        return t;
    }

    public List<Transito> obtenerTransitosDePropietario(String cedulaPropietario) {
        return transitos.stream()
                .filter(t -> t.getPropietario().getCedula().equals(cedulaPropietario))
                .sorted(Comparator.comparing(Transito::getFechaHora).reversed())
                .collect(Collectors.toList());
    }


    public List<Transito> transitosDe(UsuPorpietario p) {
        return transitos.stream()
                .filter(t -> t.getPropietario().equals(p))
                .sorted(Comparator.comparing(Transito::getFechaHora).reversed())
                .toList();
    }

    public long cantidadTransitos(UsuPorpietario p, Vehiculo v) {
        return transitos.stream()
                .filter(t -> t.getPropietario().equals(p) && t.getVehiculo().equals(v))
                .count();
    }

    public double totalGastado(UsuPorpietario p, Vehiculo v) {
        return transitos.stream()
                .filter(t -> t.getPropietario().equals(p) && t.getVehiculo().equals(v))
                .mapToDouble(Transito::getMontoPagado)
                .sum();
    }

    private record Descuento(String tipo, double monto) {
    }

    private Descuento calcularDescuento(UsuPorpietario p, Vehiculo v, Puesto puesto,
            LocalDateTime fechaHora, double tarifaBase) {

        if (p.getEstado() == EstadoPropietario.PENALIZADO)
            return new Descuento(null, 0);

        var asignadaOpt = p.getBonificacionesAsignadas().stream()
                .filter(ba -> ba.getPuesto().equals(puesto))
                .findFirst();
        if (asignadaOpt.isEmpty())
            return new Descuento(null, 0);

        String nombre = asignadaOpt.get().getBonificacion().getNombre().toLowerCase();

        switch (nombre) {
            case "exonerados":
                return new Descuento("Exonerados", tarifaBase);

            case "frecuentes":
                long hoy = transitos.stream()
                        .filter(t -> t.getVehiculo().equals(v))
                        .filter(t -> t.getPuesto().equals(puesto))
                        .filter(t -> t.getFechaHora().toLocalDate().equals(fechaHora.toLocalDate()))
                        .count();

                return (hoy >= 1)
                        ? new Descuento("Frecuentes", tarifaBase * 0.5)
                        : new Descuento("Frecuentes", 0);

            case "trabajadores":
                DayOfWeek dow = fechaHora.getDayOfWeek();
                boolean laboral = dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY;
                return new Descuento("Trabajadores", laboral ? tarifaBase * 0.8 : 0);

            default:
                return new Descuento(null, 0);
        }
    }

}
