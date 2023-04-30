/**

La clase Materia representa una materia que se puede cursar y que tiene ciertas correlatividades.
Extiende la clase abstracta Nombres, para poder devolver su nombre.
*/

package com.mycompany.integrador;

import java.util.List;

public class Materia extends Nombres {
    private final List<Materia> correlativas; // lista de materias correlativas para poder cursar la materia actual

    /**
     * Constructor de la clase Materia.
     * @param nombre El nombre de la materia.
     * @param correlativas La lista de materias correlativas necesarias para poder cursar la materia actual.
     */
    public Materia(String nombre, List<Materia> correlativas) {
        this.nombre = nombre;
        this.correlativas = correlativas;
    }

    /**
     * Método que indica si un alumno puede cursar la materia actual.
     * @param alumno El alumno que desea cursar la materia.
     * @return true si el alumno cumple con todas las correlatividades necesarias para cursar la materia, false de lo contrario.
     */
    public boolean puedeCursar(Alumno alumno) {
        if (correlativas == null) { // si no hay correlatividades necesarias, se puede cursar la materia
            return true;
        }

        for (Materia correlativa : correlativas) { // se recorre la lista de correlatividades necesarias
            if (!alumno.tieneMateriaAprobada(correlativa)) { // si el alumno no aprobó alguna correlativa, no puede cursar la materia
                return false;
            }
        }

        return true; // si llegó hasta acá, es porque el alumno aprobó todas las correlativas necesarias y puede cursar la materia
    }

    /**
     * Implementación del método abstracto de Nombres para poder devolver el nombre de la materia.
     * @return El nombre de la materia.
     */
    @Override
    public String getNombre() {
        return nombre;
    }
    
    public String[] getNombresCorrelativas() {
        int size = correlativas.size();
        String[] listaCorrelativas = new String[size];
        int count = 0;
        for (Materia correlativa : correlativas) {
            listaCorrelativas[count] = correlativa.getNombre();
            count++;
        }
        return listaCorrelativas;
    }
}