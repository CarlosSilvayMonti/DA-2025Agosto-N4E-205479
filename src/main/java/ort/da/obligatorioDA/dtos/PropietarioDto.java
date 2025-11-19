package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import ort.da.obligatorioDA.modelo.UsuPorpietario;

@Getter
public class PropietarioDto {

    private final String cedula;
    private final String nombreCompleto;
    private final String estado;
    private final double saldoActual;

    // Dejamos los siguientes opcionales, los inyectamos desde la fachada si los querés en el mismo payload
    private final List<VehiculoDto> vehiculos;
    private final List<BonificacionAsignadaDto> bonificaciones;
    private final List<NotificacionDto> notificaciones;

    // Constructor “básico” (solo datos del propietario)
    public PropietarioDto(UsuPorpietario propietario) {
        this(propietario, Collections.emptyList(), Collections.emptyList());
    }

    // Constructor “completo” (si querés enviar todo junto)
    public PropietarioDto(UsuPorpietario propietario,
                          List<VehiculoDto> vehiculos,
                          List<BonificacionAsignadaDto> bonificaciones) {
        this.cedula = propietario.getCedula();
        this.nombreCompleto = propietario.getNombreCompleto();
        this.estado = propietario.getEstado().toString();
        this.saldoActual = propietario.getSaldoActual();
        this.vehiculos = vehiculos == null ? Collections.emptyList() : vehiculos;
        this.bonificaciones =
            (propietario.getBonificacionesAsignadas() == null || propietario.getBonificacionesAsignadas().isEmpty())
                ? Collections.emptyList()
                : propietario.getBonificacionesAsignadas()
                    .stream()
                    .map(BonificacionAsignadaDto::new)
                    .collect(Collectors.toList());
        this.notificaciones =
            (propietario.getNotificaciones() == null || propietario.getNotificaciones().isEmpty())
                ? Collections.emptyList()
                : propietario.getNotificaciones()
                    .stream()
                    .map(NotificacionDto::new)
                    .collect(Collectors.toList());  
    }

    public String getCedula() {return cedula; }
    public String getNombreCompleto() {return nombreCompleto; }   
    public String getEstado() {return estado; }   
    public double getSaldoActual() {return saldoActual; }   
    public List<VehiculoDto> getVehiculos() {return vehiculos; }
    public List<BonificacionAsignadaDto> getBonificaciones() {return bonificaciones; }   
}
