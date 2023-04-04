/**

La clase ListadoDeAlumnos representa una lista de alumnos que cursan o han cursado materias.
Esta clase tiene un método statico createList que retorna un arreglo de Alumnos con materias aprobadas.
@see Alumno
@see Materia
*/
package com.mycompany.integrador;

import java.util.Arrays;

public class ListadoDeAlumnos {
    /**
    * El constructor por defecto de la clase ListadoDeAlumnos.
    */
    public ListadoDeAlumnos(){};
    
    /**

    La clase ListadoDeAlumnos representa una lista de alumnos que cursan o han cursado materias.
    Esta clase tiene un método statico createList que retorna un arreglo de Alumnos con materias aprobadas.
    @see Alumno
    @see Materia
    */
    public static Alumno[] createList(Materia[] materias){
        
        // Lista de alumnos hardcodeados con materias aprobadas
        Alumno[] alumnos = new Alumno[20];

        alumnos[0] = new Alumno("Juan Perez", "12345", Arrays.asList(materias[0], materias[1], materias[4]));
        alumnos[1] = new Alumno("Maria Gomez", "23456", Arrays.asList(materias[2], materias[3], materias[4]));
        alumnos[2] = new Alumno("Pedro Rodriguez", "34567", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[3] = new Alumno("Ana Martinez", "45678", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[4] = new Alumno("Luisa Fernandez", "56789", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[5] = new Alumno("Carlos Ramirez", "67890", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[6] = new Alumno("Lucia Gonzalez", "78901", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[7] = new Alumno("Federico Torres", "89012", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[8] = new Alumno("Camila Herrera", "90123", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[9] = new Alumno("Gabriel Medina", "01234", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[10] = new Alumno("Paula Sanchez", "23451", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[11] = new Alumno("Matias Perez", "34512", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[12] = new Alumno("Sofia Gomez", "45123", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[13] = new Alumno("Esteban Rodriguez", "51234", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[14] = new Alumno("Julieta Martinez", "12345", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[15] = new Alumno("Mateo Fernandez", "23456", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[16] = new Alumno("Valentina Ramirez", "34567", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[17] = new Alumno("Diego Torres", "45678", Arrays.asList(materias[1], materias[3], materias[5]));
        alumnos[18] = new Alumno("Laura Rodriguez", "56789", Arrays.asList(materias[0], materias[2], materias[4]));
        alumnos[19] = new Alumno("Elena Gomez", "67890", Arrays.asList(materias[1], materias[3], materias[5]));
        
        return alumnos;
    };
    
}
