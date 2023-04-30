/**

La clase ListaDeMaterias es utilizada para crear una lista de materias predefinidas con sus correspondientes materias correlativas.
Contiene un método estático createList() que devuelve una lista de objetos de la clase Materia, que representan cada una de las materias con sus correlativas.
*/
package com.mycompany.integrador;

public class ListaDeMaterias {
    /**
    * El constructor por defecto de la clase ListaDeMaterias.
    */
   public ListaDeMaterias(){};
   public static final String[] listaMaterias = {
        "Matematica I",
        "Matematica II",
        "Fisica I",
        "Fisica II",
        "Quimica I",
        "Quimica II",
        "Algebra I",
        "Algebra II",
        "Analisis I",
        "Analisis II",
        "Programacion I",
        "Programacion II",
        "Sistemas Operativos",
        "Bases de Datos",
        "Redes de Computadoras",
        "Metodos Numericos",
        "Teoria de la Computacion",
        "Ingenieria de Software",
        "Desarrollo Web",
        "Desarrollo Backend"
    };
}
