package proyectopolleria.util;

import proyectopolleria.model.Mozo;
import java.util.ArrayList;
import java.util.List;

public class ArbolBusquedaBinaria {
    private Nodo raiz;

    private class Nodo {
        Mozo mozo;
        Nodo izquierda;
        Nodo derecha;

        public Nodo(Mozo mozo) {
            this.mozo = mozo;
            this.izquierda = null;
            this.derecha = null;
        }
    }

    public ArbolBusquedaBinaria() {
        raiz = null;
    }

    public void agregarMozo(Mozo mozo) {
        raiz = agregarRec(raiz, mozo);
    }

    private Nodo agregarRec(Nodo nodo, Mozo mozo) {
        if (nodo == null) {
            return new Nodo(mozo);
        }

        // Insertar en el subárbol izquierdo o derecho según el orden
        if (mozo.getApellidos().compareToIgnoreCase(nodo.mozo.getApellidos()) < 0) {
            nodo.izquierda = agregarRec(nodo.izquierda, mozo);
        } else {
            nodo.derecha = agregarRec(nodo.derecha, mozo);
        }

        return nodo;
    }

    public Mozo buscarMozo(String apellidos) {
        return buscarRec(raiz, apellidos);
    }

    private Mozo buscarRec(Nodo nodo, String apellidos) {
        if (nodo == null || nodo.mozo.getApellidos().equalsIgnoreCase(apellidos)) {
            return nodo != null ? nodo.mozo : null;
        }

        if (apellidos.compareToIgnoreCase(nodo.mozo.getApellidos()) < 0) {
            return buscarRec(nodo.izquierda, apellidos);
        } else {
            return buscarRec(nodo.derecha, apellidos);
        }
    }

    public void eliminarMozo(String apellidos) {
        raiz = eliminarRec(raiz, apellidos);
    }

    private Nodo eliminarRec(Nodo nodo, String apellidos) {
        if (nodo == null) {
            return null;
        }

        // Buscar en el subárbol izquierdo o derecho
        if (apellidos.compareToIgnoreCase(nodo.mozo.getApellidos()) < 0) {
            nodo.izquierda = eliminarRec(nodo.izquierda, apellidos);
        } else if (apellidos.compareToIgnoreCase(nodo.mozo.getApellidos()) > 0) {
            nodo.derecha = eliminarRec(nodo.derecha, apellidos);
        } else {
            // Caso: nodo a eliminar encontrado
            if (nodo.izquierda == null) {
                return nodo.derecha;
            } else if (nodo.derecha == null) {
                return nodo.izquierda;
            }

            // Nodo con dos hijos: reemplazar con sucesor (nodo más pequeño del subárbol derecho)
            nodo.mozo = encontrarMenor(nodo.derecha);
            nodo.derecha = eliminarRec(nodo.derecha, nodo.mozo.getApellidos());
        }

        return nodo;
    }

    private Mozo encontrarMenor(Nodo nodo) {
        Mozo min = nodo.mozo;
        while (nodo.izquierda != null) {
            min = nodo.izquierda.mozo;
            nodo = nodo.izquierda;
        }
        return min;
    }
    public List<Mozo> obtenerTodosMozos() {
        List<Mozo> listaMozos = new ArrayList<>();
        obtenerTodosRec(raiz, listaMozos);
        return listaMozos;
    }

    private void obtenerTodosRec(Nodo nodo, List<Mozo> listaMozos) {
        if (nodo != null) {
            obtenerTodosRec(nodo.izquierda, listaMozos);
            listaMozos.add(nodo.mozo);
            obtenerTodosRec(nodo.derecha, listaMozos);
        }
    }
}