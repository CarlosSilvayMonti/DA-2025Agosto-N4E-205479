package ort.da.obligatorioDA.observador;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observador> observadores = new ArrayList<>();

    public void suscribir(Observador o){
        if (o != null && !observadores.contains(o)){
            observadores.add(o);
        }
    }

    public void descubrir(Observador o){
        observadores.add(o);
    }

    public void desuscribir(Observador o) {
        observadores.remove(o);
    }

    protected void notificar(Object evento) {
        for (Observador o : observadores) {
            o.actualizar(this, evento);
        }
    }

}
