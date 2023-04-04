/**

La clase ListaDeMaterias es utilizada para crear una lista de materias predefinidas con sus correspondientes materias correlativas.
Contiene un método estático createList() que devuelve una lista de objetos de la clase Materia, que representan cada una de las materias con sus correlativas.
*/
package com.mycompany.integrador;

import java.util.Arrays;

public class ListaDeMaterias {
    /**
    * El constructor por defecto de la clase ListaDeMaterias.
    */
   public ListaDeMaterias(){};

   /**
    * El método estático createList() crea una lista de objetos de la clase Materia con las correlativas correspondientes.
    * @return Una lista de objetos de la clase Materia con sus correlativas.
    */
    public static Materia[] createList(){
        
        // Lista de materias hardcodeadas con correlativas
        Materia[] materias = new Materia[20];

        materias[0] = new Materia("Matematica I", null);
        materias[1] = new Materia("Matematica II", Arrays.asList(materias[0]));
        materias[2] = new Materia("Fisica I", Arrays.asList(materias[0]));
        materias[3] = new Materia("Fisica II", Arrays.asList(materias[2], materias[1]));
        materias[4] = new Materia("Quimica I", null);
        materias[5] = new Materia("Quimica II", Arrays.asList(materias[4]));
        materias[6] = new Materia("Algebra I", null);
        materias[7] = new Materia("Algebra II", Arrays.asList(materias[6]));
        materias[8] = new Materia("Analisis I", Arrays.asList(materias[0]));
        materias[9] = new Materia("Analisis II", Arrays.asList(materias[8]));
        materias[10] = new Materia("Programacion I", null);
        materias[11] = new Materia("Programacion II", Arrays.asList(materias[10]));
        materias[12] = new Materia("Sistemas Operativos", Arrays.asList(materias[11]));
        materias[13] = new Materia("Bases de Datos", Arrays.asList(materias[11]));
        materias[14] = new Materia("Redes de Computadoras", Arrays.asList(materias[12], materias[13]));
        materias[15] = new Materia("Metodos Numericos", Arrays.asList(materias[8]));
        materias[16] = new Materia("Teoria de la Computacion", Arrays.asList(materias[15]));
        materias[17] = new Materia("Ingenieria de Software", Arrays.asList(materias[11]));
        materias[18] = new Materia("Desarrollo Web", Arrays.asList(materias[11]));
        materias[19] = new Materia("Desarrollo Backend", Arrays.asList(materias[13], materias[11], materias[16]));
        
        return materias;
    };
}
