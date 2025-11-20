package ort.da.obligatorioDA.controladores;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.observador.Observador;
import ort.da.obligatorioDA.observador.Observable;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.servicios.ServicioNotificaciones;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.utils.ConexionNavegador;
import ort.da.obligatorioDA.utils.Respuesta;

import java.util.List;

@RestController
@RequestMapping("/propietarios")
@Scope("session")
public class ControladorNotificacionesPropietario implements Observador {

    private final FachadaServicios fachada;
    private final ConexionNavegador conexion;
    private final ServicioNotificaciones sNotificaciones;

    private String cedulaActual; 

    public ControladorNotificacionesPropietario(ConexionNavegador conexion) {
        this.fachada  = FachadaServicios.getInstancia();
        this.conexion = conexion;
        this.sNotificaciones = fachada.getServicioNotificaciones();
        
        System.out.println("[ControladorNotificacionesPropietario] suscribiendo observador...");
        this.sNotificaciones.suscribir(this);
    }

    @GetMapping(value = "/registroSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE(
        @SessionAttribute(name = "usuarioLogueado", required = false) UsuPorpietario prop
    ) throws PeajeException {

        if (prop == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        cedulaActual = prop.getCedula();
        System.out.println("[ControladorNotificacionesPropietario] registrarSSE para " + cedulaActual);

        conexion.conectarSSE();

        // Estado de notificaciones
        var notis = fachada.notificacionesDePropietario(cedulaActual);
        conexion.enviarJSON(Respuesta.lista(
                new Respuesta("notificaciones", notis)
        ));

        return conexion.getConexionSSE();
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        System.out.println("[ControladorNotificacionesPropietario] actualizar() evento=" 
                           + evento + " cedulaActual=" + cedulaActual);
        if (evento == Observador.Evento.NOTIFICACIONES_ACTUALIZADAS && cedulaActual != null) {
            var notis = fachada.notificacionesDePropietario(cedulaActual);
            conexion.enviarJSON(Respuesta.lista(
                    new Respuesta("notificaciones", notis)
            ));
        }
    }
}
