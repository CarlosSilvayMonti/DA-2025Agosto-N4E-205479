package ort.da.obligatorioDA.controladores;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@Scope("session")
@RequestMapping("/propietarios")
public class PropietarioController {

    private final FachadaServicios fachada;

    public PropietarioController() {
        this.fachada = FachadaServicios.getInstancia();
    }

    // ======================================================
    // 🔹 Inicialización de la vista del propietario
    // ======================================================
    @PostMapping("/vistaConectado")
    public List<Respuesta> inicializarVista(
        @SessionAttribute(name = "usuarioLogueado", required = false) UsuPorpietario propietario) {

        if (propietario == null) {
            // Si no hay sesión activa, redirige al login
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        PropietarioDto dto = new PropietarioDto(propietario);

        System.out.println(">>> vistaConectado - cedula: " + dto.getCedula()
        + " bonificacionesDto: " + dto.getBonificaciones().size());

        try {
            return Respuesta.lista(
                new Respuesta("estado", dto.getEstado()),
                new Respuesta("saldo", dto.getSaldoActual()),
                new Respuesta("vehiculos", FachadaServicios
                        .getInstancia()
                        .vehiculosResumen(propietario.getCedula())),
                new Respuesta("bonificaciones", dto.getBonificaciones()),
                new Respuesta("transitos", FachadaServicios
                        .getInstancia()
                        .transitosDePropietario(propietario.getCedula())),
                new Respuesta("notificaciones", dto.getNotificaciones()),
                new Respuesta("mensaje", "Bienvenido " + dto.getNombreCompleto())
            );
        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("error", e.getMessage())
            );
        }
    }

     @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(
            @SessionAttribute(name = "propietarioLogueado", required = false) UsuPorpietario propietario) {

        if (propietario == null) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        try {
            boolean borroAlgo = fachada.borrarNotificaciones(propietario.getCedula());

            if (!borroAlgo) {
                // No había nada para borrar
                return Respuesta.lista(
                    new Respuesta("mensaje", "No hay notificaciones para borrar.")
                );
            }

            // Si más adelante tenés notificaciones en el DTO, acá las podrías reenviar
            // PropietarioDto dtoActualizado = new PropietarioDto(propietario);

            return Respuesta.lista(
                // new Respuesta("notificaciones", dtoActualizado.getNotificaciones()),
                new Respuesta("mensaje", "Notificaciones eliminadas correctamente.")
            );

        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("error", e.getMessage())
            );
        }
    }
}
