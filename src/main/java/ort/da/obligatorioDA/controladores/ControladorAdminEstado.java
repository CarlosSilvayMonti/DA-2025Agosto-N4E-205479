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

import ort.da.obligatorioDA.dtos.PropietarioDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.Usuario;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

// DTO muy simple para los estados
class EstadoPropietarioDto {
    public String nombre;

    public EstadoPropietarioDto(EstadoPropietario e) {
        this.nombre = e.toString();
    }
}

@RestController
@Scope("session")
@RequestMapping("/adminEstado")
public class ControladorAdminEstado {

    private final FachadaServicios fachada;

    public ControladorAdminEstado() {
        this.fachada = FachadaServicios.getInstancia();
    }

    // 1) Inicializar vista: devolver lista de estados
    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        List<EstadoPropietarioDto> estados = Arrays.stream(EstadoPropietario.values())
                .map(EstadoPropietarioDto::new)
                .collect(Collectors.toList());

        return Respuesta.lista(
            new Respuesta("estados", estados)
        );
    }

    // 2) Buscar propietario por cédula
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
            UsuPorpietario p = fachada.buscarPorCedula(cedula);
            PropietarioDto dto = new PropietarioDto(p);

            return Respuesta.lista(
                new Respuesta("propietario", dto)
            );

        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("mensaje", e.getMessage())
            );
        }
    }

    // 3) Cambiar el estado
    @PostMapping("/cambiarEstado")
    public List<Respuesta> cambiarEstado(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
        @RequestParam String cedula,
        @RequestParam String nuevoEstado) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        try {
            EstadoPropietario estadoNuevo = EstadoPropietario.valueOf(nuevoEstado);

            PropietarioDto dtoActualizado = fachada.cambiarEstadoPropietario(cedula, estadoNuevo);

            return Respuesta.lista(
                new Respuesta("propietario", dtoActualizado),
                new Respuesta("mensaje", "Estado cambiado correctamente a " + estadoNuevo)
            );

        } catch (IllegalArgumentException ex) {
            // valueOf falló
            return Respuesta.lista(
                new Respuesta("mensaje", "Estado inválido: " + nuevoEstado)
            );
        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("mensaje", e.getMessage())
            );
        }
    }
}
