package ort.da.obligatorioDA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ort.da.obligatorioDA.excepciones.PeajeException;
import ort.da.obligatorioDA.modelo.Bonificacion;
import ort.da.obligatorioDA.modelo.Categoria;
import ort.da.obligatorioDA.modelo.EstadoPropietario;
import ort.da.obligatorioDA.modelo.Puesto;
import ort.da.obligatorioDA.modelo.Tarifa;
import ort.da.obligatorioDA.modelo.UsuAdmin;
import ort.da.obligatorioDA.modelo.UsuPorpietario;
import ort.da.obligatorioDA.modelo.Vehiculo;
import ort.da.obligatorioDA.servicios.ServicioBonificaciones;
import ort.da.obligatorioDA.servicios.fachada.FachadaServicios;


@SpringBootApplication
public class ObligatorioDaApplication {

	public static void main(String[] args) throws PeajeException {
		cargarDatosDePrueba();
		SpringApplication.run(ObligatorioDaApplication.class, args);
	}

	private static void cargarDatosDePrueba() throws PeajeException {
        try {
            FachadaServicios fachada = FachadaServicios.getInstancia();

            // ================================================================
            //ADMINISTRADORES
            
            UsuAdmin admin1 = new UsuAdmin("123", "admin", "Usuario Administrador");
            UsuAdmin admin2 = new UsuAdmin("87654321", "admin.456", "Admin Secundario");
            fachada.agregar(admin1);
            fachada.agregar(admin2);

            // ================================================================
            //CATEGORÍAS
            Categoria auto = new Categoria("Automóvil");
            Categoria camion = new Categoria("Camión");
            Categoria moto = new Categoria("Moto");
            fachada.agregar(auto);
            fachada.agregar(camion);
            fachada.agregar(moto);

            // ================================================================
            //PROPIETARIOS Y VEHÍCULOS
            UsuPorpietario p1 = new UsuPorpietario("234", "prop", "Carlos Silva",
                    2000, 500, EstadoPropietario.HABILITADO);
            UsuPorpietario p2 = new UsuPorpietario("98765432", "prop.456", "Laura Gómez",
                    1200, 300, EstadoPropietario.SUSPENDIDO);

            // Vehículos
            p1.getVehiculos().add(new Vehiculo("SBC1234", "Toyota", "Corolla", "Gris", auto));
            p1.getVehiculos().add(new Vehiculo("SCB5678", "Honda", "Civic", "Negro", auto));
            p2.getVehiculos().add(new Vehiculo("AAA1111", "Yamaha", "MT07", "Roja", moto));

            fachada.agregar(p1);
            fachada.agregar(p2);


			/*p1.acreditarVehiculo("SBC1234", 1200);
			p1.acreditarVehiculo("SCB5678", 800);*/

            // ================================================================
            //PUESTOS Y TARIFAS
            Puesto pto1 = new Puesto("Peaje Pando", "Ruta Interbalnearia km 33");
            Puesto pto2 = new Puesto("Peaje Colonia", "Ruta 1 km 120");

            pto1.getTarifas().add(new Tarifa(auto, 120));
            pto1.getTarifas().add(new Tarifa(camion, 200));

            pto2.getTarifas().add(new Tarifa(auto, 100));
            pto2.getTarifas().add(new Tarifa(moto, 60));

            fachada.agregar(pto1);
            fachada.agregar(pto2);

			fachada.agregar(new Bonificacion("Exonerados"));
			fachada.agregar(new Bonificacion("Frecuentes"));
			fachada.agregar(new Bonificacion("Trabajadores"));

            // ================================================================
			//ASIGNACIONES DE BONIFICACIONES A PROPIETARIOS
			try {
				fachada.asignarBonificacion("234", "Frecuentes",   "Peaje Pando");
				fachada.asignarBonificacion("98765432", "Trabajadores", "Peaje Colonia");
			} catch (PeajeException e) {
				System.out.println("⚠️ Error asignando bonificaciones: " + e.getMessage());
			}

			UsuPorpietario p111 = fachada.buscarPorCedula("234");
			System.out.println("Bonificaciones de p1 luego de precarga: " 
					+ p111.getBonificacionesAsignadas().size());

				
			// ===============================================================
			//PRECARGA DE TRÁNSITOS (Frecuentes en Pando) 
			LocalDate hoy = LocalDate.now();

			fachada.registrarTransito("234", "SBC1234", "Peaje Pando",
					LocalDateTime.of(hoy, LocalTime.of(8, 0)));

			fachada.registrarTransito("234", "SBC1234", "Peaje Pando",
					LocalDateTime.of(hoy, LocalTime.of(9, 30)));

			fachada.registrarTransito("234", "SBC1234", "Peaje Colonia",
					LocalDateTime.of(hoy, LocalTime.of(11, 0)));

            System.out.println("Precarga completada correctamente.");

			System.out.println("Bonificaciones cargadas: " + fachada.nombresBonificaciones());


        } catch (PeajeException e) {
            System.out.println("Error cargando datos de prueba: " + e.getMessage());
        }
    }
}
