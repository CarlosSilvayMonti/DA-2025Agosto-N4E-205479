package ort.da.obligatorioDA.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hola")
public class HolaMundoController {
    @GetMapping("/mundo")
    public String saludar() {
        return "¡Hola, Mundo!";
    }
}
