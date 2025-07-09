package proyectopolleria.controller;
import proyectopolleria.util.MenuPolleria;
import java.util.List;
import proyectopolleria.model.previos.Empleado;

public class RestaurantePolloBrasa {
    private String nombre;
    private String direccion;
    private int capacidad;
    private MenuPolleria menu;
    private List<Empleado> empleados;

    public RestaurantePolloBrasa(String nombre, String direccion, int capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public MenuPolleria getMenu() { return menu; }
    public void setMenu(MenuPolleria menu) { this.menu = menu; }

    public List<Empleado> getEmpleados() { return empleados; }
    public void setEmpleados(List<Empleado> empleados) { this.empleados = empleados; }

    public void mostrarDetalles() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Capacidad: " + capacidad);
    }
}
