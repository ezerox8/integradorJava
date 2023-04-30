/**

Clase Integrador que procesa las inscripciones de alumnos a materias
y genera un archivo CSV con los resultados.
*/
package com.mycompany.integrador;

import com.mycompany.ventanas.LogicaComun;
import com.mycompany.ventanas.ManejoDeDatos;
import com.mycompany.ventanas.Ventana;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Integrador {
    
    // Lista de materias y alumnos
    public static ArrayList<Materia> materias;
    public static Alumno alumno;
    public static Ventana ventana;

    /**
     * Método principal que comienza la ejecición del sistema./../java/com/mycompany/imagenes/bg.jpeg
     * @param args Argumentos de línea de comando (no se utilizan).
     * @throws IOException Si ocurre un error al escribir el archivo.
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {
        LogicaComun.initBD();
        if (ManejoDeDatos.verificarTabla("materias")) {
            try {
                Integrador.materias = new ArrayList<>(ManejoDeDatos.obtenerMaterias().values());
            } catch (SQLException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ventana = new Ventana();
        ventana.setVisible(true);
    }
   
}
