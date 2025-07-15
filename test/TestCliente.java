package proyectopolleria.test;

import proyectopolleria.controller.ClienteController;
import proyectopolleria.model.Cliente;
import proyectopolleria.util.Conexion;

import java.sql.Connection;
import java.util.List;

public class TestCliente {

    public static void main(String[] args) {
        try {
            // Obtener conexión a la base de datos
            Connection conn = Conexion.getInstancia().getConexion();

            // Crear instancia del controlador
            ClienteController controller = new ClienteController(conn);

            // Crear nuevo cliente de prueba
            Cliente nuevo = new Cliente();
            nuevo.setDni("");
            nuevo.setNombre("Carlos Ramírez");
            nuevo.setTelefono("912345678");
            nuevo.setDireccion("Av. Siempre Viva 123");
            nuevo.setReferencia("Cerca al parque");

            // Registrar cliente
            controller.registrarCliente(nuevo);

            // Listar todos los clientes
            List<Cliente> lista = controller.listarClientes();
            System.out.println(" Lista de clientes:");
            for (Cliente c : lista) {
                System.out.println(" ID: " + c.getId() + ", Nombre: " + c.getNombre() + ", DNI: " + c.getDni());
            }

            conn.close();

        } catch (Exception e) {
            System.err.println("? Error general en prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
