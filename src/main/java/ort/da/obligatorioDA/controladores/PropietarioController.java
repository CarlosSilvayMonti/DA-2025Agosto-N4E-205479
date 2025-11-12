package ort.da.obligatorioDA.controladores;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@Scope("session")
@RequestMapping("/propietarios")
public class PropietarioController {
    private FachadaServicios fachada;

    public PropietarioController() {
        this.fachada = FachadaServicios.getInstancia();
    }

    // ======================================================
    // 🔹 Inicialización de la vista del propietario
    // ======================================================

    @PostMapping("/vistaConectado")
    public List<Respuesta> inicializarVista(
            @SessionAttribute(name = "usuarioLogueado", required = false) UsuPorpietario usuario) {

        if (usuario == null) {
            // Si no hay sesión activa, se redirige al login
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        try {
            PropietarioDto propietarioDto = fachada.obtenerTableroPropietario(usuario.getCedula());
            return Respuesta.lista(
                    new Respuesta("tableroPropietario", propietarioDto),
                    new Respuesta("mensaje", "Bienvenido " + propietarioDto.getNombreCompleto())
            );
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    // ======================================================
    // 🔹 Opción: borrar notificaciones (curso alternativo)
    // ======================================================

    /*
    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(
            @SessionAttribute(name = "usuarioLogueado", required = false) UsuPorpietario usuario) {

        if (usuario == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        try {
            boolean hayNotificaciones = fachada.borrarNotificaciones(usuario.getCedula());
            if (!hayNotificaciones) {
                return Respuesta.lista(new Respuesta("mensaje", "No hay notificaciones para borrar."));
            }

            PropietarioDto actualizado = fachada.obtenerTableroPropietario(usuario.getCedula());
            return Respuesta.lista(
                    new Respuesta("tableroPropietario", actualizado),
                    new Respuesta("mensaje", "Notificaciones eliminadas correctamente.")
            );

        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    } */


}
