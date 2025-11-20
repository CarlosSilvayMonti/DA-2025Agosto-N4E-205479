package ort.da.obligatorioDA.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import ort.da.obligatorioDA.dtos.BonificacionDto;
import ort.da.obligatorioDA.dtos.PuestoDto;
import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Usuario;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@Scope("session")
@RequestMapping("/adminBonificaciones")
public class ControladorAdminBonificaciones {

    private final FachadaServicios fachada;

    public ControladorAdminBonificaciones() {
        this.fachada = FachadaServicios.getInstancia();
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        List<BonificacionDto> bonificaciones = fachada.getBonificacionesDto()
                .stream()
                .map(BonificacionDto::new)
                .collect(Collectors.toList());

        List<PuestoDto> puestos = fachada.getPuestos()
                .stream()
                .map(PuestoDto::new)
                .collect(Collectors.toList());

        return Respuesta.lista(
            new Respuesta("bonificaciones", bonificaciones),
            new Respuesta("puestos", puestos)
        );
    }

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
        @RequestParam String cedula) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        try {
            PropietarioDto dto = fachada.obtenerTableroPropietario(cedula);

            return Respuesta.lista(
                new Respuesta("propietario", dto),
                new Respuesta("bonificacionesPropietario", dto.getBonificaciones())
            );

        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("mensaje", e.getMessage())
            );
        }
    }

    @PostMapping("/asignar")
    public List<Respuesta> asignar(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
        @RequestParam String cedula,
        @RequestParam(required = false) String nombreBonificacion,
        @RequestParam(required = false) String nombrePuesto) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        try {
            if (nombreBonificacion == null || nombreBonificacion.isBlank()) {
                throw new PeajeException("Debe especificar una bonificación");
            }
            if (nombrePuesto == null || nombrePuesto.isBlank()) {
                throw new PeajeException("Debe especificar un puesto");
            }

            fachada.asignarBonificacion(cedula, nombreBonificacion, nombrePuesto);

            PropietarioDto dto = fachada.obtenerTableroPropietario(cedula);

            return Respuesta.lista(
                new Respuesta("bonificacionesPropietario", dto.getBonificaciones()),
                new Respuesta("mensaje", "Bonificación asignada correctamente.")
            );

        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("mensaje", e.getMessage())
            );
        }
    }
}
