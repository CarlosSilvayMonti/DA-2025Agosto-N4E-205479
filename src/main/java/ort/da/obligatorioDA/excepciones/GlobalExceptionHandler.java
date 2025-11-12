package ort.da.obligatorioDA.excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final int errorCodeStatus = 299; //cambiar si se quiere otro codigo para indicar excepcion de aplicacion
    @ExceptionHandler(PeajeException.class)
    public ResponseEntity<String> manejarException(PeajeException e){
        return ResponseEntity.status(errorCodeStatus).body(e.getMessage());
    }  
}
