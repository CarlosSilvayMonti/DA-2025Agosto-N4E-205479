package ort.da.obligatorioDA.modelo;

import java.util.ArrayList;
import java.util.List;

public class PrecargaDatos {
    private static List<UsuAdmin> administradores = new ArrayList<>();
    private static List<UsuPorpietario> propietarios = new ArrayList<>();
    private static List<Puesto> puestos = new ArrayList<>();
    private static List<Categoria> categorias = new ArrayList<>();
    private static List<Bonificacion> bonificaciones = new ArrayList<>();

    public static void precargar() {
        // Administradores
        administradores.add(new UsuAdmin("12345678", "administradores.123", "Usuario Administrador"));
        administradores.add(new UsuAdmin("87654321", "admin.456", "Admin Secundario"));

        // Propietarios
        UsuPorpietario p1 = new UsuPorpietario("234", "prop", "Carlos Silva", 2000, 500, EstadoPropietario.HABILITADO);
        UsuPorpietario p2 = new UsuPorpietario("98765432", "prop.456", "Laura Gómez", 1200, 300, EstadoPropietario.SUSPENDIDO);

        // Categorías
        Categoria auto = new Categoria("Automóvil");
        Categoria camion = new Categoria("Camión");
        Categoria moto = new Categoria("Moto");
        categorias.addAll(List.of(auto, camion, moto));

        // Vehículos
        p1.getVehiculos().add(new Vehiculo("SBC1234", "Toyota", "Corolla", "Gris", auto));
        p1.getVehiculos().add(new Vehiculo("SCB5678", "Honda", "Civic", "Negro", auto));
        p2.getVehiculos().add(new Vehiculo("AAA1111", "Yamaha", "MT07", "Roja", moto));

        // Puestos y Tarifas
        Puesto pto1 = new Puesto("Peaje Pando", "Ruta Interbalnearia km 33");
        Puesto pto2 = new Puesto("Peaje Colonia", "Ruta 1 km 120");

        pto1.getTarifas().add(new Tarifa(auto, 120));
        pto1.getTarifas().add(new Tarifa(camion, 200));
        pto2.getTarifas().add(new Tarifa(auto, 100));
        pto2.getTarifas().add(new Tarifa(moto, 60));

        puestos.addAll(List.of(pto1, pto2));

        // Bonificaciones
        bonificaciones.add(new Bonificacion("Exonerados"));
        bonificaciones.add(new Bonificacion("Frecuentes"));
        bonificaciones.add(new Bonificacion("Trabajadores"));

        propietarios.addAll(List.of(p1, p2));

        System.out.println("✅ Precarga completada correctamente.");
    }

    public static List<UsuAdmin> getAdministradores() { return administradores; }
    public static List<UsuPorpietario> getPropietarios() { return propietarios; }
    public static List<Puesto> getPuestos() { return puestos; }
    public static List<Categoria> getCategorias() { return categorias; }
    public static List<Bonificacion> getBonificaciones() { return bonificaciones; }
}
