package ort.da.obligatorioDA.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;
import ort.da.obligatorioDA.modelo.Sesion;


@RestController
@RequestMapping("/acceso")
public class ControladorLogin {

    @PostMapping("/loginPropietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String contrasenia) throws PeajeException {

        Sesion sesion = FachadaServicios.getInstancia().loginPropietario(cedula, contrasenia);

        sesionHttp.setAttribute("usuarioLogueado", sesion.getProp());
        sesionHttp.setAttribute("sesionUsuario", sesion);

        return Respuesta.lista(
                new Respuesta("loginExitoso", "/html/menuProp.html")
        );
    }

    @PostMapping("/loginAdministrador")
    public List<Respuesta> loginAdministrador(
            HttpSession sesionHttp,
            @RequestParam String cedula,
            @RequestParam String contrasenia) throws PeajeException {

        UsuAdmin admin = FachadaServicios
                .getInstancia()
                .loginAdministrador(cedula, contrasenia);

        sesionHttp.setAttribute("usuarioLogueado", admin);

        return Respuesta.lista(
                new Respuesta("mensaje", "Bienvenido " + admin.getNombreCompleto()),
                new Respuesta("loginExitoso", "/html/menuAdmin.html")
        );
    }
}
