package ort.da.obligatorioDA.dtos;

import java.time.format.DateTimeFormatter;

import ort.da.obligatorioDA.modelo.Transito;

public class TransitoDto {
    private String puesto;
    private String matricula;
    private String categoria;
    private double montoTarifa;
    private String tipoBonificacion; 
    private double montoBonificacion;
    private double montoPagado;
    private String fechaHora; 

    public TransitoDto(Transito t) {
        this.puesto = t.getPuesto().getNombre();
        this.matricula = t.getVehiculo().getMatricula();
        this.categoria = t.getVehiculo().getCategoria().toString();
        this.montoTarifa = t.getMontoTarifa();
        this.tipoBonificacion = t.getTipoBonificacion() == null ? "" : t.getTipoBonificacion();
        this.montoBonificacion = t.getMontoBonificacion();
        this.montoPagado = t.getMontoPagado();
        this.fechaHora = t.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getPuesto() {return puesto; }
    public String getMatricula() {return matricula; }
    public String getCategoria() {return categoria; }
    public double getMontoTarifa() {return montoTarifa; }
    public String getTipoBonificacion() {return tipoBonificacion; }
    public double getMontoBonificacion() {return montoBonificacion; }
    public double getMontoPagado() {return montoPagado; }
    public String getFechaHora() {return fechaHora; }

}
