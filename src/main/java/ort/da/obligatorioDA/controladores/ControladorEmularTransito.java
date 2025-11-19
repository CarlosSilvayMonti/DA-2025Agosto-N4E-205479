package ort.da.obligatorioDA.controladores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import ort.da.obligatorioDA.dtos.PuestoDto;
import ort.da.obligatorioDA.dtos.ResultadoTransitoDto;
import ort.da.obligatorioDA.dtos.TarifaDto;
import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Puesto;
import ort.da.obligatorioDA.modelo.Tarifa;
import ort.da.obligatorioDA.modelo.Transito;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.Usuario;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;
import ort.da.obligatorioDA.utils.Respuesta;

@RestController
@Scope("session")
@RequestMapping("/adminTransitos")
public class ControladorEmularTransito {

    private final FachadaServicios fachada;

    public ControladorEmularTransito() {
        this.fachada = FachadaServicios.getInstancia();
    }

    // 1) Inicializar vista: lista de puestos
    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        List<PuestoDto> puestos = fachada.getPuestos()
                .stream()
                .map(PuestoDto::new)
                .collect(Collectors.toList());

        return Respuesta.lista(
            new Respuesta("puestos", puestos)
        );
    }

    // 2) Cargar tarifas de un puesto seleccionado
    @PostMapping("/tarifasPuesto")
    public List<Respuesta> tarifasPuesto(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
        @RequestParam String nombrePuesto) throws PeajeException {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        Puesto p = fachada.buscarPuestoPorNombre(nombrePuesto);

        List<TarifaDto> tarifas = p.getTarifas()
                .stream()
                .map(TarifaDto::new)
                .collect(Collectors.toList());

        return Respuesta.lista(
            new Respuesta("tarifas", tarifas)
        );
    }

    // 3) Emular el tránsito
    @PostMapping("/emular")
    public List<Respuesta> emularTransito(
        @SessionAttribute(name = "usuarioLogueado", required = false) Usuario usuario,
        @RequestParam String nombrePuesto,
        @RequestParam String matricula,
        @RequestParam String fecha,  // formato: 2025-11-18
        @RequestParam String hora    // formato: 14:30
    ) {

        if (!(usuario instanceof UsuAdmin)) {
            return Respuesta.lista(
                new Respuesta("usuarioNoAutenticado", "/html/login.html")
            );
        }

        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fecha + "T" + hora);

            Transito t = fachada.emularTransito(matricula, nombrePuesto, fechaHora);

            ResultadoTransitoDto dto = new ResultadoTransitoDto(t);

            return Respuesta.lista(
                new Respuesta("resultadoTransito", dto),
                new Respuesta("mensaje", "Tránsito registrado correctamente")
            );

        } catch (PeajeException e) {
            // Mensajes de cursos alternativos (no existe vehículo, saldo insuficiente, etc.)
            return Respuesta.lista(
                new Respuesta("mensaje", e.getMessage())
            );
        }
    }
}
