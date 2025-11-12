package ort.da.obligatorioDA.modelo;

public abstract class Usuario {
    protected String cedula;
    protected String contrasenia;
    protected String nombreCompleto;

    public Usuario(String nombre, String contrasenia, String nombreCompleto) {
        this.cedula = nombre;
        this.contrasenia = contrasenia;
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    public String getNombreCompleto() {
        return nombreCompleto;  
    }

    public boolean autenticar(String cedula, String contrasenia) {
        return this.cedula.equals(cedula) && this.contrasenia.equals(contrasenia);
    }

    public abstract String getTipoUsuario();
}
