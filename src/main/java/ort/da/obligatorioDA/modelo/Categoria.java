package ort.da.obligatorioDA.modelo;

public class Categoria {
    private String tipo;

    public Categoria(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Categoria: " + tipo;
    }
}
