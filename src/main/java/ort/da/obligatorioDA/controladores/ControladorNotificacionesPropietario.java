package ort.da.obligatorioDA.controladores;

import jakarta.servlet.http.HttpSession;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.observador.Observador;
import ort.da.obligatorioDA.observador.Observable;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.servicios.ServicioNotificaciones;
import ort.da.obligatorioDA.dtos.EventoNotificacionesDto;
import ort.da.obligatorioDA.dtos.NotificacionDto;
import ort.da.obligatorioDA.utils.ConexionNavegador;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@Scope("session")
@RequestMapping("/propietarios")
public class ControladorNotificacionesPropietario implements Observador {

    private final ConexionNavegador conexion;
    private final HttpSession httpSession;
    private final ServicioNotificaciones servicioNotificaciones;

    public ControladorNotificacionesPropietario(ConexionNavegador conexion,
                                                HttpSession httpSession) {
        this.conexion = conexion;
        this.httpSession = httpSession;

        // ✅ Usamos SIEMPRE la instancia única desde la fachada
        this.servicioNotificaciones =
                FachadaServicios.getInstancia().getServicioNotificaciones();

        // ✅ Nos suscribimos a los eventos (patrón Observador)
        this.servicioNotificaciones.suscribir(this);
    }

    // SSE para esta sesión de propietario
    @GetMapping(value = "/registroSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexion.conectarSSE();
        return conexion.getConexionSSE();
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        // Acá depende cómo modelaste el "evento", 
        // te doy una versión simple: nueva lista de notificaciones del propietario.
        if (!(evento instanceof EventoNotificacionesDto ev)) { // tu tipo de evento
            return;
        }

        UsuPorpietario enSesion =
                (UsuPorpietario) httpSession.getAttribute("usuarioLogueado");

        if (enSesion == null) return;
        if (!enSesion.getCedula().equals(ev.getCedula())) return;

        List<NotificacionDto> notifs = ev.getNotificaciones();

        var respuesta = Respuesta.lista(
            new Respuesta("notificaciones", notifs)
        );

        conexion.enviarJSON(respuesta);
    }
}
