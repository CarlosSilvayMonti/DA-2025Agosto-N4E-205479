package ort.da.obligatorioDA.controladores;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;
import ort.da.obligatorioDA.excepciones.PeajeException;

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
            @SessionAttribute(name = "adminLogueado", required = false) UsuAdmin admin) {

        if (admin == null) {
            // Si no hay admin en sesión, redirigís al login
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        return Respuesta.lista(
            // estos ids deben matchear con tus funciones mostrar_* en adminBonificaciones.html
            new Respuesta("bonificaciones", fachada.getBonificacionesDto()),
            new Respuesta("puestos", fachada.getPuestosDto())
        );
    }
}
