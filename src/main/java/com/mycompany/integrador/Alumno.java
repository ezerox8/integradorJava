/**

La clase Alumno representa a un estudiante que tiene un nombre, un número de legajo y una lista de materias aprobadas.
Además, cuenta con un método que verifica si el alumno tiene una materia aprobada en base a la lista de materias aprobadas.
Esta clase hereda de la clase abstracta Nombres e implementa su método abstracto getNombre().
*/
package com.mycompany.integrador;

import java.util.List;

/**
 *
 * @author ezequiel.suarez
 */
public class Alumno extends Nombres {
    private final String legajo; // numero de legajo del alumno
    private final List<Materia> materiasAprobadas; // lista de materias aprobadas por el alumno poder cursar marerias correlativas.
    /**
    * Constructor de la clase Alumno.
    * @param nombre El nombre del alumno.
    * @param legajo El número de legajo del alumno.
    * @param materiasAprobadas La lista de materias aprobadas por el alumno.
    */
   public Alumno(String nombre, String legajo, List<Materia> materiasAprobadas) {
       this.nombre = nombre;
       this.legajo = legajo;
       this.materiasAprobadas = materiasAprobadas;
   }

   /**
    * Método que verifica si el alumno tiene una materia aprobada.
    * @param materia La materia que se quiere verificar si está aprobada por el alumno.
    * @return true si el alumno tiene aprobada la materia, false en caso contrario.
    */
    public boolean tieneMateriaAprobada(Materia materia) {
        return materiasAprobadas.contains(materia);
    }
    
    // Implementación del método abstracto de Nombres
    @Override
    public String getNombre() {
        return nombre;
    }
    
    public String getLegajo() {
        return legajo;
    }
}
