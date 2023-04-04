/**

La clase Inscripcion representa una inscripción de un alumno en una materia y la fecha en la que se realizó.
@author ezequiel.suarez
*/
package com.mycompany.integrador;

import java.time.LocalDateTime;

public class Inscripcion {
    private Alumno alumno; // alumno que desea realizar la inscripción.
    private Materia materia; // materia a la que el alumno desea inscribirse.
    private LocalDateTime fecha; // fecha de alta del pedido de inscripción.
    
    /**
    * Crea una nueva inscripción con el alumno, la materia y la fecha especificados.
    * 
    * @param alumno el alumno que se inscribe en la materia.
    * @param materia la materia en la que se inscribe el alumno.
    * @param fecha la fecha en la que se realiza la inscripción.
    */
   public Inscripcion(Alumno alumno, Materia materia, LocalDateTime fecha) {
       this.alumno = alumno;
       this.materia = materia;
       this.fecha = fecha;
   }

   /**
    * Devuelve true si el alumno aprobó la materia de esta inscripción, según las materias aprobadas que tiene.
    * 
    * @return true si el alumno aprobó la materia, false en caso contrario.
    */
   public boolean aprobada() {
       return materia.puedeCursar(alumno);
   }

   /**
    * Devuelve el alumno de la inscripción.
    * 
    * @return el alumno de la inscripción.
    */
   public Alumno getAlumno() {
       return alumno;
   }

   /**
    * Devuelve la materia de la inscripción.
    * 
    * @return la materia de la inscripción.
    */
   public Materia getMateria() {
       return materia;
   }

   /**
    * Devuelve la fecha de la inscripción.
    * 
    * @return la fecha de la inscripción.
    */
   public LocalDateTime getFecha() {
       return fecha;
   }

}
