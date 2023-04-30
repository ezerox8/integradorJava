/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ventanas;

import com.mycompany.integrador.Alumno;
import com.mycompany.integrador.Integrador;
import com.mycompany.integrador.ListaDeMaterias;
import com.mycompany.integrador.Materia;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ezequ
 */
public class LogicaComun {
    
    public static Ventana ventana;
    
    public static void initBD() throws SQLException{
        if (!ManejoDeDatos.verificarBaseDeDatos()) {
            ManejoDeDatos.CrearBD();
        }
        if (!ManejoDeDatos.verificarTabla("listadomaterias")) {
            String[] columnas = {"materias TEXT"};
            ManejoDeDatos.crearTabla("listadomaterias", columnas);
            String[] columnasAEscribir = {"materias"};
            ArrayList datos = new ArrayList();
            for (String materia : ListaDeMaterias.listaMaterias) {
                String[] materiaArray = {materia};
                datos.add(materiaArray);
            }
            ManejoDeDatos.escribirTabla("listadomaterias", columnasAEscribir, datos);
        }
    }
    
    public static void login() throws SQLException{
        System.out.println("login");
        String user = ventana.getUser().getText();
        String userStr = String.valueOf(user);
        String passwordStr = String.valueOf(ventana.getPassword().getPassword());
       
        if(userStr.equals("profe") && passwordStr.equals("profe")){
            // TO DO mostrar interfaz profesor
            ventana.cleanError();
            ventana.getLogin().setVisible(false);
            ventana.getPanelAltaMateria().setVisible(false);
            ventana.getPanelAltaAlumno().setVisible(false);
            ventana.getPanelVerMaterias().setVisible(false);
            ventana.getPanelVerAlumnos().setVisible(false);
            ventana.getCerrarSesion().setVisible(true);
            ventana.getProfesor().setVisible(true);
        } else if(validarLegajo(userStr) && passwordStr.equals("alumno")){
            // TO DO mostrar interfaz alumno
            ventana.cleanError();
            ventana.getLogin().setVisible(false);
            ventana.getCerrarSesion().setVisible(true);
            ventana.getAlumno().setVisible(true);
        } else {
            ventana.loginError();
        }
        ventana.getUser().setText("");
        ventana.getPassword().setText("");
    }
    
    public static boolean validarLegajo(String userStr) throws SQLException{
        if (!ManejoDeDatos.verificarTabla("alumnos")) {
            return false;
        } else {
            int legajo;
            try {
                legajo = Integer.parseInt(userStr);
            } catch (NumberFormatException e) {
                legajo = 1;
            }
            ArrayList resultado = ManejoDeDatos.buscarLineaIntegerGeneral("alumnos", "legajo", legajo);
            if (!resultado.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static void logout(){
        System.out.println("logout");
        ventana.cleanError();
        ventana.getProfesor().setVisible(false);
        ventana.getAlumno().setVisible(false);
        ventana.getCerrarSesion().setVisible(false);
        ventana.getLogin().setVisible(true);
   }
    
    public static String getStringFormat(String string){
        if(!string.equals("")){
            return string + ", ";
        } else {
            return "";
        }
   }
    
    public static HashMap<String, String> getParametros() {
        HashMap<String, String> parametros = new HashMap<>();
        int indiCorrelativa1 = ventana.getListaCorrelativas1().getSelectedIndex();
        int indiCorrelativa2 = ventana.getListaCorrelativas2().getSelectedIndex();
        int indiCorrelativa3 = ventana.getListaCorrelativas3().getSelectedIndex();
        int indiCorrelativa4 = ventana.getListaCorrelativas4().getSelectedIndex();
        int indiCorrelativa5 = ventana.getListaCorrelativas5().getSelectedIndex();
        parametros.put("materiaIngresada", toCamelCase(ventana.getNombreMateria().getText()));
        String correlativa1Seleccionada = indiCorrelativa1!= -1 ? ventana.getListaCorrelativas1().getSelectedItem().toString() : "";
        String correlativa2Seleccionada = indiCorrelativa2!= -1 ? ventana.getListaCorrelativas2().getSelectedItem().toString() : "";
        String correlativa3Seleccionada = indiCorrelativa3!= -1 ? ventana.getListaCorrelativas3().getSelectedItem().toString() : "";
        String correlativa4Seleccionada = indiCorrelativa4!= -1 ? ventana.getListaCorrelativas4().getSelectedItem().toString() : "";
        String correlativa5Seleccionada = indiCorrelativa5!= -1 ? ventana.getListaCorrelativas5().getSelectedItem().toString() : "";
        StringBuilder sb = new StringBuilder();
        sb.append(getStringFormat(correlativa1Seleccionada));
        sb.append(getStringFormat(correlativa2Seleccionada));
        sb.append(getStringFormat(correlativa3Seleccionada));
        sb.append(getStringFormat(correlativa4Seleccionada));
        sb.append(getStringFormat(correlativa5Seleccionada));
        if(sb.length()>= 2){
            sb.setLength(sb.length() - 2);
        }
        String CorrelativasSeleccionaldas = sb.toString();
        parametros.put("CorrelativasSeleccionaldas", CorrelativasSeleccionaldas);
        return parametros;
    }
    
    public static void enviarAltaMateria() throws SQLException{
        HashMap<String, String> parametros = getParametros();
        ArrayList datos = new ArrayList();
        String[] columnas = {"materia", "correlativas"};
        int indiceMateria = ventana.getListaMaterias().getSelectedIndex();
        String CorrelativasSeleccionaldas = parametros.get("CorrelativasSeleccionaldas");
        String[] MateriasCorrelativas = !CorrelativasSeleccionaldas.equals("") ? CorrelativasSeleccionaldas.split(", ") : new String[0];
        if (Integrador.materias != null && MateriasCorrelativas.length != 0) {
            List<String> lista = new ArrayList(Integrador.materias.size());
            int i = 0;
            for(Materia materia : Integrador.materias){
                lista.add(materia.getNombre());
                i++;
            }
            if (!lista.containsAll(Arrays.asList(MateriasCorrelativas))) {
                String mensaje = "La materia no puede crearse ya que no se creo una o varias deeeeeee";
                String mensaje2 = "sus correlativas, por favor carguelas primero y reintente.";
                llamarModal(mensaje, mensaje2);
                return;
            }
        } else if(MateriasCorrelativas.length != 0){
            String mensaje = "La materia no puede crearse ya que no se creo unaaaaaa o varias de";
            String mensaje2 = "sus correlativas, por favor carguelas primero y reintente.";
            llamarModal(mensaje, mensaje2);
            return;
        }
        if(indiceMateria == -1){
            if(parametros.get("materiaIngresada").equals("")) {
                String mensaje = "Datos invalidos, por favor cargue una materia.";
                llamarModal(mensaje);
            } else {
                if (!ManejoDeDatos.existeElElementoEnLaTabla("listadomaterias", "materias", parametros.get("materiaIngresada"))) {
                    String[] subDatos = {parametros.get("materiaIngresada"), parametros.get("CorrelativasSeleccionaldas")};
                    datos.add(subDatos);
                    ManejoDeDatos.escribirTabla("materias",columnas , datos);
                    String[] col = {"materias"};
                    ArrayList dato = new ArrayList();
                    String[] subDato = {parametros.get("materiaIngresada")};
                    dato.add(subDato);
                    ManejoDeDatos.escribirTabla("listadomaterias",col , dato);
                    ventana.limpiarAltaMaterias();
                    String mensaje = "Materia guardada exitosamente.";
                    llamarModal(mensaje);    
                } else {
                    String mensaje = "La materia que desea crear ya existe.";
                    llamarModal(mensaje);
                }
            }
        } else {
            String materiaSeleccionada = ventana.getListaMaterias().getSelectedItem().toString();
            String[] subDatos = {materiaSeleccionada, parametros.get("CorrelativasSeleccionaldas")};
            datos.add(subDatos);
            ManejoDeDatos.escribirTabla("materias",columnas , datos);
            ventana.limpiarAltaMaterias();
            String mensaje = "Materia guardada exitosamente.";
            llamarModal(mensaje);
        }
    }
    
    public static void enviarAltaAlumno() throws SQLException {
        String nombreAlumno = toCamelCase(ventana.getCargarNombreAlumno().getText());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ventana.getTablaMateriasAprobadas().getRowCount(); i++) {
            if ((Boolean)ventana.getTablaMateriasAprobadas().getValueAt(i, 1)) { // Columna "Seleccionar"
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(ventana.getTablaMateriasAprobadas().getValueAt(i, 0)); // Columna "Materias"
            }
        }
        String resultado = sb.toString();
        ArrayList datos = new ArrayList();
        Integer legajo = ManejoDeDatos.consultarTablaInteger();
        Object[] subDatos = {legajo ,nombreAlumno, resultado};
        datos.add(subDatos);
        String[] columnas = {"legajo", "nombre", "aprobadas"};
        ManejoDeDatos.escribirTabla("alumnos",columnas , datos);
        String mensaje = "Alumno creado exitosamente bajo el legajo:";
        String legajoMostrar = String.format("%05d",legajo);
        LogicaComun.llamarModal(mensaje, legajoMostrar);
        
    }
    
    public static void enviarInscripcionAlumno() throws SQLException {
        ArrayList<Object[]> parametros = new ArrayList<>();
        String sePuedeInscribir = "";
        String noSePuedeInscribir = "";
        for (int i = 0; i < ventana.getTablaInscripcion().getRowCount(); i++) {
            if ((Boolean)ventana.getTablaInscripcion().getValueAt(i, 1)) {
                for(Materia mat : Integrador.materias){
                    if(mat.getNombre().equals(ventana.getTablaInscripcion().getValueAt(i, 0))){
                        if(mat.puedeCursar(Integrador.alumno)){
                            sePuedeInscribir+=mat.getNombre();
                        }else{
                            noSePuedeInscribir+=mat.getNombre();
                        }
                    }
                }
            }
        }
        String[] data = new String[]{Integrador.alumno.getNombre(), Integrador.alumno.getLegajo(),sePuedeInscribir};
        parametros.add(data);
        if (!ManejoDeDatos.verificarTabla("inscripciones")) {
            String[] columnasACrear = new String[]{"nombre TEXT","legajo INT","materias TEXT"};
            ManejoDeDatos.crearTabla("inscripciones", columnasACrear);
        }
        String[] columnas = {"nombre", "legajo", "materias"};
        ManejoDeDatos.escribirTabla("inscripciones",columnas , parametros);
        if(sePuedeInscribir.length() > 1){
            String mensaje = "Felicitaciones, pudiste inscribirte exitosamente a las siguientes materias:";
            String mensaje2 = sePuedeInscribir;
            LogicaComun.llamarModal(mensaje, mensaje2);
        }
        if(noSePuedeInscribir.length() > 1){
            String mensaje = "lo sentimos, no pudiste inscribirte a las siguientes materias:";
            String mensaje2 = noSePuedeInscribir;
            LogicaComun.llamarModal(mensaje, mensaje2);
        }
        
    }
    
    public static void llamarModal(String mensaje) {
        ejecutarModal(mensaje, "");
    }
    
    public static void llamarModal(String mensaje, String legajo) {
        ejecutarModal(mensaje, legajo);
    }
    
    private static void ejecutarModal(String mensaje, String legajo) {
            String msg = mensaje;
            Modal modal = new Modal(ventana, true, msg, legajo);
            ActionListener escuchador = (ActionEvent e) -> {
                modal.setVisible(false);
                modal.removeAll();
                ventana.borrarCamposAlumno();
            };
            modal.getButton().addActionListener(escuchador);
            modal.setVisible(true);
    }
    
    private static void ajustarTabla(javax.swing.JTable tabla){
        final TableColumnModel columnModel = tabla.getColumnModel();
        for (int column = 0; column < tabla.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < tabla.getRowCount(); row++) {
                TableCellRenderer renderer = tabla.getCellRenderer(row, column);
                Component comp = tabla.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public static void escribirTablaDeConsulta(ArrayList<Object[]> parametros, JTable tabla) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        int size = parametros.size();
        for (Object[] parametro : parametros) {
            model.addRow(parametro);
        }
    }
    
    public static void ConsultarMateria() throws SQLException {
        String materiaAConsultar = toCamelCase(ventana.getNombreMateriaAConsultar().getText());
        ArrayList resultado = ManejoDeDatos.buscarLineaStringGeneral("materias", "materia", materiaAConsultar);
        if (!resultado.isEmpty()) {
            escribirTablaDeConsulta(resultado, ventana.getTablaMaterias());
            ajustarTabla(ventana.getTablaMaterias());
        } else {
            String mensaje = "Materia no encontrada.";
            LogicaComun.llamarModal(mensaje);
        }
    }
    
    public static void consultarTodasLasMaterias() throws SQLException {
        escribirTablaDeConsulta(ManejoDeDatos.consultarTablaGeneral("materias", "*"), ventana.getTablaMaterias());
        ajustarTabla(ventana.getTablaMaterias());
    }
    
    public static void ConsultarAlumno() throws SQLException {
        int legajoAConsultar;
        legajoAConsultar = (int) ventana.getLegajoAlumnoAConsultar().getValue();
        ArrayList resultado = ManejoDeDatos.buscarLineaIntegerGeneral("alumnos", "legajo", legajoAConsultar);
        if (!resultado.isEmpty()) {
            escribirTablaDeConsulta(resultado, ventana.getTablaAlumnos());
            ajustarTabla(ventana.getTablaAlumnos());
        } else {
            String mensaje = "Alumno no encontrado.";
            LogicaComun.llamarModal(mensaje);
        }
        ajustarTabla(ventana.getTablaAlumnos());
    }
    
    public static void ConsultarAlumnoParaInscribir() throws SQLException {
        int legajoAConsultar;
        legajoAConsultar = (int) ventana.getLegajoAlumnoAInscribir().getValue();
        ArrayList<String[]> resultado = ManejoDeDatos.buscarLineaIntegerGeneral("alumnos", "legajo", legajoAConsultar);
        if (!resultado.isEmpty()) {
            System.out.println("verificando datos");
            String correlativasParaListar = resultado.get(0)[2];
            String nombreParaInstanciar = resultado.get(0)[1];
            String legajoParaInstanciar = resultado.get(0)[0];
            String[] correlativasParaBuscar = correlativasParaListar.split(", ");
            ArrayList<Materia> correlativasParaInstanciar = new ArrayList<>();
            for(String correlativa : correlativasParaBuscar) {
                for(Materia materia : Integrador.materias) {
                    if(materia.getNombre().equals(correlativa)){
                        correlativasParaInstanciar.add(materia);
                    }
                }
            }
            Integrador.alumno = new Alumno(nombreParaInstanciar, legajoParaInstanciar, correlativasParaInstanciar);
            List<Materia> materiasAListar = Integrador.materias;
            for(Materia materia : correlativasParaInstanciar) {
                materiasAListar.remove(materia);
            }
            ArrayList<Object[]> parametros = new ArrayList<>();
            for(Materia materia : materiasAListar) {
                Object[] row = new Object[]{materia.getNombre(), false};
                parametros.add(row);
            }
            escribirTablaDeConsulta(parametros, ventana.getTablaInscripcion());
            ajustarTabla(ventana.getTablaInscripcion());
        } else {
            String mensaje = "Alumno no encontrado.";
            LogicaComun.llamarModal(mensaje);
        }
        ajustarTabla(ventana.getTablaInscripcion());
    }
    
    public static void consultarTodosLosAlumnos() throws SQLException {
        escribirTablaDeConsulta(ManejoDeDatos.consultarTablaIntegerGeneral("alumnos", "*"), ventana.getTablaAlumnos());
        ajustarTabla(ventana.getTablaAlumnos());
    }
    
    public static String toCamelCase(String input) {
        String[] words = input.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.length() > 0) {
                if (word.equalsIgnoreCase("II") || word.equalsIgnoreCase("III") || word.equalsIgnoreCase("IIII")) {
                    sb.append(word.toUpperCase());
                } else {
                    sb.append(Character.toUpperCase(word.charAt(0)));
                    sb.append(word.substring(1).toLowerCase());
                }
                if (i < words.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString().trim();
    }
}
