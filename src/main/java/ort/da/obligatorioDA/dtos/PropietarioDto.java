package ort.da.obligatorioDA.dtos;

import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;
import ort.da.obligatorioDA.modelo.UsuPorpietario;

@Getter
public class PropietarioDto {
    private String cedula;
    private String nombreCompleto;
    private String estado;
    private double saldoActual;
    private List<VehiculoDto> vehiculos;
    private List<BonificacionDto> bonificaciones;

    public PropietarioDto(UsuPorpietario propietario) {
        this.cedula = propietario.getCedula();
        this.nombreCompleto = propietario.getNombreCompleto();
        this.estado = propietario.getEstado().toString();
        this.saldoActual = propietario.getSaldoActual();
        this.vehiculos = propietario.getVehiculos()
                .stream()
                .map(VehiculoDto::new)
                .toList();
        this.bonificaciones = List.of();
    }
}
