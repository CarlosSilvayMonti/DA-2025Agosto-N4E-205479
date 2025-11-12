package ort.da.obligatorioDA.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import ort.da.obligatorioDA.modelo.Sesion;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.modelo.Usuario;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@RequestMapping("/menu")
public class ControladorMenu {

    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(
        @SessionAttribute(name = "propietarioLogueado", required = false) UsuPorpietario porpietario) {

        if (porpietario == null) {
            // Redirige al login si no hay usuario en sesión
            return Respuesta.lista(new Respuesta("usuarioNoLogueado", "/html/login.html"));
        }

        // Retorna el nombre del usuario para mostrarlo en el menú
        return Respuesta.lista(
            new Respuesta("nombreCompleto", porpietario.getNombreCompleto()));
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sesionHttp) {
        Sesion sesion = (Sesion) sesionHttp.getAttribute("sesionUsuario");
        if (sesion != null) {
            FachadaServicios.getInstancia().logout(sesion);
            sesionHttp.removeAttribute("usuarioLogueado");
            sesionHttp.removeAttribute("sesionUsuario");
            sesionHttp.invalidate();
        }
        return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "/html/login.html"));
    }
}

