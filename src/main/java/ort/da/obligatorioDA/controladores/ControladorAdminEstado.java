package ort.da.obligatorioDA.controladores;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.obligatorioDA.dtos.NotificacionDto;
import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.Usuario;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@Scope("session")
@RequestMapping("/adminEstado")
public class ControladorAdminEstado {

    private final FachadaServicios fachada;

    public ControladorAdminEstado() {
        this.fachada = FachadaServicios.getInstancia();
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada(
            @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                    new Respuesta("usuarioNoAutenticado", "/html/login.html"));
        }

        List<String> estados = Arrays.stream(EstadoPropietario.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return Respuesta.lista(
                new Respuesta("estados", estados));
    }

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(
            @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
            @RequestParam String cedula) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                    new Respuesta("usuarioNoAutenticado", "/html/login.html"));
        }

        try {
            UsuPorpietario p = fachada.buscarPorCedula(cedula);
            PropietarioDto dto = new PropietarioDto(p);

            return Respuesta.lista(
                    new Respuesta("propietario", dto));

        } catch (PeajeException e) {
            return Respuesta.lista(
                    new Respuesta("mensaje", e.getMessage()));
        }
    }

    @PostMapping("/cambiarEstado")
    public List<Respuesta> cambiarEstado(
            @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
            @RequestParam String cedula,
            @RequestParam String nuevoEstado) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                    new Respuesta("usuarioNoAutenticado", "/html/login.html"));
        }

        System.out.println(">>> cambiarEstado() - cedula=" + cedula + " nuevoEstado=" + nuevoEstado);

        try {
            EstadoPropietario estado;
            try {
                estado = EstadoPropietario.valueOf(nuevoEstado);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> cambiarEstado() - estado no válido: " + nuevoEstado);
                return Respuesta.lista(
                        new Respuesta("mensaje", "Estado no válido: " + nuevoEstado));
            }

            fachada.cambiarEstadoPropietario(cedula, estado);

            UsuPorpietario p = fachada.buscarPorCedula(cedula);
            PropietarioDto dto = new PropietarioDto(p);
            List<NotificacionDto> notifs = fachada.notificacionesDePropietario(cedula);

            System.out.println(">>> cambiarEstado() OK - nuevo estado: " + dto.getEstado());

            return Respuesta.lista(
                    new Respuesta("propietario", dto),
                    new Respuesta("notificaciones", notifs),
                    new Respuesta("mensaje", "Estado cambiado correctamente."));
        } catch (PeajeException e) {
            System.out.println(">>> cambiarEstado() - PeajeException: " + e.getMessage());
            return Respuesta.lista(
                    new Respuesta("mensaje", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>> cambiarEstado() - Exception: "
                    + e.getClass().getName() + " - " + e.getMessage());
            return Respuesta.lista(
                    new Respuesta("mensaje", "Error inesperado al cambiar estado: "
                            + e.getClass().getSimpleName()
                            + (e.getMessage() != null ? " - " + e.getMessage() : "")));
        }
    }

}
