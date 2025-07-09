package proyectopolleria.seguridad;

import proyectopolleria.model.previos.Usuario;
import java.util.HashMap;
import java.util.Map;

public class Autentificador {
    // HashMap para almacenar los usuarios (clave: id, valor: objeto Usuario)
    private Map<String, Usuario> usuarios = new HashMap<>();
     // Método para agregar un nuevo usuario al HashMap
    public void agregarUsuario(String id, String password) {
        //Crea un nuevo usurio y lo guarda en el HashMap
        usuarios.put(id, new Usuario(id, password));
    }
     // Método para autenticar un usuario dado su id y password
    public boolean autenticar(String id, String password) {
        Usuario usuario = usuarios.get(id);
        //Comprueba que la contraseña sea correcta
        return usuario != null && usuario.getPassword().equals(password);
    }
}
