package proyectopolleria.util;
import java.util.ArrayList;
import java.util.List;

public class MenuPolleria {

    // Clase interna para representar un producto
    public class Producto {
        private String nombre;
        private String codigo;
        private double precio;

        public Producto(String nombre, String codigo, double precio) {
            this.nombre = nombre;
            this.codigo = codigo;
            this.precio = precio;
        }

        public String getNombre() { return nombre; }
        public String getCodigo() { return codigo; }
        public double getPrecio() { return precio; }

        @Override
        public String toString() { return nombre; }
    }

    public List<Producto> getAllItems() {
        List<Producto> allItems = new ArrayList<>();

        // Pollos
        allItems.add(new Producto("1 Pollo", "P01", 59.0));
        allItems.add(new Producto("1/2 Pollo", "P02", 34.0));
        allItems.add(new Producto("1/4 Pollo", "P03", 18.0));

        // Papas
        allItems.add(new Producto("1 Porcion de papas familiar", "PP01", 12.0));
        allItems.add(new Producto("1 Porcion de papas personal", "PP02", 8.0));

        // Bebidas
        allItems.add(new Producto("Inca Kola 1 1/2 Litro", "B01", 10.0));
        allItems.add(new Producto("Coca Cola 1 1/2 Litro", "B02", 10.0));
        allItems.add(new Producto("Chicha 1 Litro", "B03", 7.0));
        allItems.add(new Producto("Agua 500ml", "B04", 2.0));

        // Postres
        allItems.add(new Producto("1 porcion de helado", "PT01", 5.0));
        allItems.add(new Producto("1 porcion de pastel", "PT02", 5.0));
        allItems.add(new Producto("1 porcion de gelatina", "PT03", 4.0));

        return allItems;
    }
}