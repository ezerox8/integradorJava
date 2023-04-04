/**

Clase Integrador que procesa las inscripciones de alumnos a materias
y genera un archivo CSV con los resultados.
*/
package com.mycompany.integrador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class Integrador {
    
    // Lista de materias y alumnos
    public static final Materia[] materias = ListaDeMaterias.createList();
    public static final Alumno[] alumnos = ListadoDeAlumnos.createList(materias);
    public static final String[] responses = {"S", "SI", "YES", "Y"};

    /**
     * Método principal que comienza la ejecición del sistema.
     * 
     * @param args Argumentos de línea de comando (no se utilizan).
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void main(String[] args) throws IOException {
        excecution();
    }
    
    /**
     * Método que lee los datos de entrada, procesa las inscripciones
     * genera un archivo CSV con los resultados y consulta si desea
     * realizar una nueva ejecución.
     * 
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void excecution() throws IOException {
    // Lee los datos de entrada
        List<String[]> rows = DataInput.requestData();
        if (rows != null) {
            // Crea el archivo de salida
            BufferedWriter writer = new BufferedWriter(new FileWriter("resultado_inscripciones_" + getDate() + ".csv"));
            writer.write("Alumno,Materia,Estado de la inscripcion,\n");
            // Procesa cada inscripción
            for( String[] row : rows) {
                writer.write(inscriptionManagement(row));
            }
            writer.close();
        }
        System.out.print("Desea verificar otra lista? S/N: ");
        String response = DataInput.readUserInput();
        if (Arrays.asList(responses).contains(response.toUpperCase())) {
            excecution();
        } else {
            System.err.println("Proceso finalizado.");
        }
    }

    /**
     * Procesa una inscripción y devuelve el resultado como una cadena CSV.
     * 
     * @param row Arreglo de cadenas que contiene los datos de la inscripción (nombre del alumno y de la materia).
     * @return Cadena CSV con el resultado de la inscripción.
     */
    public static String inscriptionManagement(String[] row){
        // Filtra el alumno y la materia correspondientes
        Alumno student = filterElements(row[0], alumnos);
        Materia matter = filterElements(row[1], materias);
        boolean studentExist = student != null;
        boolean matterExist = matter != null;
        Inscripcion inscription;
        String enrollmentState;
        String valueToReturn;
        // Comprueba si el alumno y la materia existen
        if (!studentExist) {
            enrollmentState = "No existe el/la alumno/a";
            valueToReturn = row[0] + "," + row[1] + "," + enrollmentState + "\n";
        } else if(!matterExist) {
            enrollmentState = "No existe la materia";
            valueToReturn = student.getNombre() + "," + row[1] + "," + enrollmentState + "\n";
        } else {
            // Crea la inscripción y determina si fue aprobada o rechazada
            inscription = new Inscripcion(student, matter, LocalDateTime.now());
            enrollmentState = inscription.aprobada() ? "Aprobada" : "Rechazada";
            valueToReturn = inscription.getAlumno().getNombre() + "," + inscription.getMateria().getNombre() + "," + enrollmentState + "\n";
        }
        return valueToReturn;
    }

    /**
    * Busca un elemento en un arreglo de objetos que implementan la interfaz Nombres.
    * @param inscription el nombre del elemento a buscar.
    * @param listado el arreglo en el que se buscará el elemento.
    * @param <T> un tipo genérico que implementa la interfaz Nombres.
    * @return el objeto encontrado o null si no se encontró.
    */
   public static <T extends Nombres> T filterElements(String inscription, T[] listado) {
       T found = null;
       for(T element : listado){
           if (element.getNombre().equalsIgnoreCase(inscription)) {
               found = element;
               break;
           }
       }
       return found;
   }

   /**
    * Obtiene la fecha y hora actual en un formato específico.
    * @return una cadena de texto con la fecha y hora actual en el formato "'A'yyyy'M'MM'D'DD'H'HH'M'mm'S'SS".
    */
   public static String getDate() {
       LocalDateTime now = LocalDateTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'A'yyyy'M'MM'D'DD'H'HH'M'mm'S'SS");
       String formattedDate = now.format(formatter);
       return formattedDate;
   }
}
