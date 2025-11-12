package ort.da.obligatorioDA.modelo;

public class UsuAdmin extends Usuario{
    public UsuAdmin(String cedula, String contrasenia, String nombreCompleto) {
        super(cedula, contrasenia, nombreCompleto);
    }

    @Override
    public String getTipoUsuario() {
        return "Administrador";
    }

    @Override
    public String toString() {
        return "Administrador: " + nombreCompleto + " (" + cedula + ")";
    }

}

