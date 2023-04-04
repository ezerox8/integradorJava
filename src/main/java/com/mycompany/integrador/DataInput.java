/**

 El siguiente código permite al usuario ingresar una ruta de archivo y nombre de archivo,
 luego lo lee y verifica si el mismo existe, si su formato es csv, tiene las columnas correctas y devuelve
 una lista de filas de datos que contienen información sobre peticion de inscripciones.
 **/

package com.mycompany.integrador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class DataInput {
    private static final String ALUMNO_HEADER = "ALUMNO";
    private static final String MATERIA_HEADER = "MATERIA";
    private static final String EXTENSION = ".csv";

    public static void DataInput() {}
    
    /**
    * Método que solicita al usuario la ruta del archivo a evaluar y retorna una lista de arreglos de Strings con los datos del archivo.
    * @return Lista de arreglos de Strings con los datos del archivo.
    */
   public static List<String[]> requestData() {
       System.out.println("Hola, bienvenido al sistema de evaluación de correlatividades.");
       System.out.println("Recuerde que para el correcto funcionamiento del sistema debe cumplir los siguientes requerimientos:");
       System.out.println("- Ingresar una ruta válida donde este su archivo.");
       System.out.println("- El archivo debe ser de formato .csv.");
       System.out.println("- La tabla debe tener exactamente dos columnas correspondientes al Alumno y a la Materia respectivamente.");
       System.out.println("- La primera fila de la tabla debe traer los identificadores de las columnas 'Alumno' y 'Materia'.\n");
       System.out.print("Por favor ingrese la ruta donde se encuentra el archivo: ");
       String path = readUserInput();
       System.out.println("");

       System.out.print("Por favor ingrese el nombre del archivo sin su extención: ");
       String fileName = readUserInput();
       System.out.println("");

       // Se crea un objeto File con la ruta y nombre del archivo ingresados por el usuario.
       File file = new File(path + fileName + EXTENSION);
       // Se retorna una lista de arreglos de Strings con los datos del archivo.
       return evaluateFile(file, DataInput::readDataFromFile);
   }

   /**
    * Método que lee la entrada del usuario por consola y la retorna como un String.
    * @return String con la entrada del usuario por consola.
    */
   public static String readUserInput() {
       // Se crea un objeto BufferedReader para leer la entrada del usuario por consola.
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       try {
           // Se lee la entrada del usuario por consola y se retorna como un String.
           return reader.readLine();
       } catch (IOException e) {
           // En caso de error al leer la entrada del usuario, se retorna null.
           return null;
       }
   }

   /**
    * Método que verifica que el archivo a evaluar cumpla con los requerimientos necesarios y lo evalúa utilizando la función action.
    * @param file Archivo a evaluar.
    * @param action Función a utilizar para evaluar el archivo.
    * @return Lista de arreglos de Strings con los datos del archivo, si el archivo cumple con los requerimientos necesarios. Null en caso contrario.
    */
   private static List<String[]> evaluateFile(File file, Function<File, List<String[]>> action) {
       // Se verifica que el archivo exista.
       if (!file.exists()) {
           System.out.println("Error: El archivo no existe");
           return null;
       }
       // Se verifica que la ruta ingresada corresponda a un archivo y no a un directorio.
       if (!file.isFile()) {
           System.out.println("Error: La ruta ingresada no es un archivo");
           return null;
       }
       // Se verifica que el archivo tenga extensión .csv.
       if (!file.getName().endsWith(".csv")) {
           System.out.println("Error: El archivo no es un archivo csv");
           return null;
       }

       // Se utiliza la función action para evaluar el archivo y se retorna una lista de arreglos de Strings con los datos del archivo.
       return action.apply(file);
   }

    /**
    * Lee el archivo y devuelve una lista de matrices de cadenas que representan cada fila del archivo.
    *
    * @param file el archivo que se leerá
    * @return una lista de matrices de cadenas que representan cada fila del archivo
    * @throws FileNotFoundException si el archivo no se encuentra en la ruta especificada
    * @throws InvalidFileFormatException si el archivo no tiene el formato esperado
    * @throws IOException si hay un error al leer el archivo
    */
   private static List<String[]> readDataFromFile(File file) {
       try (FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader)) {
           List<String[]> rows = new ArrayList<>();
           String linea;
           int lineCount = 0;
           while ((linea = br.readLine()) != null) {
               lineCount++;
               String[] split = linea.split(",");
               if (lineCount == 1) {
                   validateHeaderRow(split, lineCount);
                   continue; // Saltar la primera línea (encabezados)
               }
               validateDataRow(split, lineCount);
               normalizeData(split);
               rows.add(split);
           }
           return rows;
       } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo no fue encontrado, por favor revise la ruta y el nombre del archivo, y vuelva a intentar.");
        } catch (IOException | InvalidFileFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
       return null;
   }

   /**
    * Valida la fila de encabezado del archivo.
    *
    * @param headerRow la fila de encabezado del archivo
    * @param lineCount el número de línea que se está validando
    * @throws InvalidFileFormatException si la fila de encabezado no tiene el formato esperado
    */
   private static void validateHeaderRow(String[] headerRow, int lineCount) throws InvalidFileFormatException {
       if (headerRow.length != 2
               || !headerRow[0].trim().toUpperCase().equals(ALUMNO_HEADER)
               || !headerRow[1].trim().toUpperCase().equals(MATERIA_HEADER)) {
           throw new InvalidFileFormatException("El archivo no tiene el formato esperado en la línea " + lineCount);
       }
   }

   /**
    * Valida una fila de datos del archivo.
    *
    * @param dataRow la fila de datos del archivo
    * @param lineCount el número de línea que se está validando
    * @throws InvalidFileFormatException si la fila de datos no tiene el formato esperado
    */
   private static void validateDataRow(String[] dataRow, int lineCount) throws InvalidFileFormatException {
       if (dataRow.length != 2) {
           throw new InvalidFileFormatException("El archivo no tiene el formato esperado en la línea " + lineCount);
       }
   }

   /**
    * Normaliza los datos de una fila del archivo.
    *
    * @param dataRow la fila de datos del archivo
    */
   private static void normalizeData(String[] dataRow) {
       dataRow[0] = dataRow[0].trim();
       dataRow[1] = dataRow[1].trim();
   }

    private static class InvalidFileFormatException extends RuntimeException {
        public InvalidFileFormatException(String message) {
            super(message);
        }
    }
}
