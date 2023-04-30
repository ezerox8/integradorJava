/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ventanas;
import com.mycompany.integrador.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author ezequ
 */
public class ManejoDeDatos {
    private static final String nombreBaseDeDatos = "integrador";
    private static final String usuario = "root";
    private static final String password = "root";
    
    public static boolean verificarBaseDeDatos() {
        System.out.println("Verificando BD.");

        boolean existeBaseDeDatos = false;

        try {
            // Establecer conexión con el servidor de base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/", usuario, password);

            // Obtener objeto DatabaseMetaData
            DatabaseMetaData metaDatos = conexion.getMetaData();

            // Obtener resultados de la consulta de las bases de datos existentes
            ResultSet resultado = metaDatos.getCatalogs();

            // Iterar a través de los resultados de la consulta para encontrar la base de datos buscada
            while (resultado.next()) {
                String nombre = resultado.getString("TABLE_CAT");
                if (nombre.equals(nombreBaseDeDatos)) {
                    existeBaseDeDatos = true;
                    break;
                }
            }

            // Cerrar la conexión y liberar recursos
            resultado.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Verificando BD resultado: " + existeBaseDeDatos);
        return existeBaseDeDatos;
    }
    
    public static boolean verificarTabla(String nombreTabla) {
        System.out.println("Verificando Tabla.");

        boolean existeTabla = false;

        try {
            // Establecer conexión con la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);

            // Obtener objeto DatabaseMetaData
            DatabaseMetaData metaDatos = conexion.getMetaData();

            // Obtener resultados de la consulta de las tablas existentes
            ResultSet resultado = metaDatos.getTables(null, null, null, new String[] {"TABLE"});

            // Iterar a través de los resultados de la consulta para encontrar la tabla buscada
            while (resultado.next()) {
                String nombre = resultado.getString("TABLE_NAME");
                if (nombre.equals(nombreTabla)) {
                    existeTabla = true;
                    break;
                }
            }

            // Cerrar la conexión y liberar recursos
            resultado.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Verificando Tabla resultado: " + existeTabla);
        return existeTabla;
    }
    
    public static void CrearBD() {
        System.out.println("Creando BD.");

        String nombreBaseDeDatos = "Integrador";

        try {
            // Establecer conexión con el servidor de base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/", usuario, password);

            // Crear la base de datos
            Statement statement = conexion.createStatement();
            String query = "CREATE DATABASE " + nombreBaseDeDatos;
            statement.executeUpdate(query);

            // Cerrar la conexión y liberar recursos
            statement.close();
            conexion.close();

            System.out.println("La base de datos " + nombreBaseDeDatos + " ha sido creada exitosamente.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void crearTabla(String nombreTabla, String[] columnas) {
        System.out.println("Creando Tabla.");

        try {
            // Establecer conexión con la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);

            // Crear la tabla
            Statement statement = conexion.createStatement();
            StringBuilder query = new StringBuilder("CREATE TABLE ");
            query.append(nombreTabla).append(" (");
            for (String columna : columnas) {
                query.append(LogicaComun.toCamelCase(columna)).append(",");
            }
            query.setLength(query.length() - 1);
            query.append(")");
            statement.executeUpdate(query.toString());

            // Cerrar la conexión y liberar recursos
            statement.close();
            conexion.close();

            System.out.println("La tabla " + nombreTabla + " ha sido creada exitosamente.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getQuestionMarks(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("?, ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
    
    public static String[] consultarTabla(String nombreTabla, String columna) throws SQLException{
        System.out.println("consultando tablas.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        String sql = "SELECT "+columna+" FROM "+nombreTabla;
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery(sql);
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String value = resultSet.getString(1);
            list.add(value);
        }
        stmt.close();
        resultSet.close();
        conexion.close();
        return list.toArray(String[]::new);
    }
    
    public static Integer consultarTablaInteger() throws SQLException{
        System.out.println("consultando tablas integer.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        Statement stmt = conexion.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT MAX(legajo) FROM alumnos");
        resultSet.next();
        Integer maxLegajo = resultSet.getInt(1);
        Integer legajo = maxLegajo != 0 ? maxLegajo + 1 : 10000;
        stmt.close();
        resultSet.close();
        conexion.close();
        return legajo;
    }
    
    public static ArrayList consultarTablaGeneral(String nombreTabla, String columna) throws SQLException{
        System.out.println("consultando tablas general.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        String sql = "SELECT "+columna+" FROM "+nombreTabla;
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList parametros = new ArrayList<>();
        while (resultSet.next()) {
            String[] linea = {resultSet.getString("materia"), resultSet.getString("correlativas")};
            parametros.add(linea);
        }
        stmt.close();
        conexion.close();
        resultSet.close();
        return parametros;
    }
    
    public static ArrayList consultarTablaIntegerGeneral(String nombreTabla, String columna) throws SQLException{
        System.out.println("consultando tablas general.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        String sql = "SELECT "+columna+" FROM "+nombreTabla;
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList parametros = new ArrayList<>();
        while (resultSet.next()) {
            String[] linea = {resultSet.getString("legajo"), resultSet.getString("nombre"), resultSet.getString("aprobadas")};
            parametros.add(linea);
        }
        stmt.close();
        conexion.close();
        resultSet.close();
        return parametros;
    }
    
    public static boolean existeElElementoEnLaTabla(String nombreTabla, String columna,String parametroAConsultar) throws SQLException{
        System.out.println("consultando tablas general.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        String sql = "SELECT * FROM "+nombreTabla;
        Statement stmt = conexion.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        boolean encontrada = false;
        while (resultSet.next()) {
            String materia = resultSet.getString(columna);
            if (materia.equals(parametroAConsultar)) {
                encontrada = true;
                break;
            }
        }
        stmt.close();
        conexion.close();
        resultSet.close();
        return encontrada;
    }
    
    public static ArrayList buscarLineaStringGeneral(String nombreTabla, String columna, String parametroAConsultar) throws SQLException{
        System.out.println("consultando tablas general.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        String sql = "SELECT * FROM "+nombreTabla;
        Statement stmt = conexion.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList parametros = new ArrayList<>();
        while (resultSet.next()) {
            String materia = resultSet.getString(columna);
            if (materia.equals(parametroAConsultar)) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] colums = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    colums[i-1] = resultSet.getString(i);
                }
                parametros.add(colums);
            }
        }
        stmt.close();
        conexion.close();
        resultSet.close();
        return parametros;
    }
    
    public static ArrayList buscarLineaIntegerGeneral(String nombreTabla, String columna, Integer parametroAConsultar) throws SQLException{
        System.out.println("consultando tablas general.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombreBaseDeDatos, usuario, password);
        String sql = "SELECT * FROM "+nombreTabla;
        Statement stmt = conexion.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList parametros = new ArrayList<>();
        while (resultSet.next()) {
            int legajo = resultSet.getInt(columna);
            if (legajo == parametroAConsultar) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] colums = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    colums[i-1] = resultSet.getString(i);
                }
                parametros.add(colums);
            }
        }
        stmt.close();
        conexion.close();
        resultSet.close();
        return parametros;
    }
    
    public static void escribirTabla(String nombreTabla, String[] columnas, ArrayList<Object[]> datos) throws SQLException{
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBaseDeDatos, usuario, password);
        conexion.setAutoCommit(false); // Deshabilitar el modo de confirmación automática
        String stringColumnas = String.join(",", columnas);
        String sql = "INSERT INTO " + nombreTabla + " (" + stringColumnas + ") VALUES (" + getQuestionMarks(columnas.length) + ")";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        for (Object[] dato : datos) {
            int indice = 1;
            for (Object subDato : dato) {
                String datoFormateado;
                if (subDato instanceof String[] strings) {
                    datoFormateado = String.join(",", strings);
                } else {
                    datoFormateado = subDato.toString();
                }
                stmt.setString(indice, datoFormateado);
                indice++;
            }
            stmt.addBatch(); // Agregar la fila a la transacción
        }
        stmt.executeBatch(); // Ejecutar la transacción
        conexion.commit(); // Confirmar la transacción
        stmt.close();
        conexion.close();
    }
    
    public static Map<String, Materia> obtenerMaterias() throws SQLException {
        System.out.println("obtenerMaterias.");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBaseDeDatos, usuario, password);
        // Consulta para obtener todas las materias con sus correlativas
        String consulta = "SELECT materia, correlativas FROM materias";

        try (Statement stmt = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(consulta)) {
            
            // Mapa para almacenar las materias que ya han sido instanciadas
            Map<String, Materia> mapaMaterias = new HashMap<>();

            // Lista para almacenar las materias que no tienen correlativas
            List<String> materiasInstanciadas = new ArrayList<>();
            
            // Lista para almacenar las materias que tienen correlativas
            List<String[]> materiasConCorrelativas = new ArrayList<>();
            
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            
            // Se recorre el ResultSet con las filas de la tabla materias
            while (rs.next()) {
                // Se obtienen los valores de las columnas de la fila actual
                String nombreMateria = rs.getString("materia").trim();
                String correlativasStr = rs.getString("correlativas").trim();
                if (!correlativasStr.equals("")) {
                    String[] arregloMateriasConCorrelativas = new String[]{nombreMateria.trim(),correlativasStr};
                    materiasConCorrelativas.add(arregloMateriasConCorrelativas);
                } else {
                    materiasInstanciadas.add(nombreMateria.trim());
                    Materia materia = new Materia(nombreMateria, new ArrayList());
                    mapaMaterias.put(nombreMateria, materia);
                }
            }
            return crearInstanciaMateria(materiasInstanciadas, mapaMaterias, materiasConCorrelativas, size);   
        }
    }
        
    private static  Map<String, Materia> crearInstanciaMateria(List<String> materiasInstanciadas, Map<String, Materia> mapaMaterias, List<String[]> materiasConCorrelativas, int size){
        int contador = 0; 
        StringJoiner joiner = new StringJoiner(", ");
        for (String i : materiasInstanciadas) {
            joiner.add(i);
        }
        while (materiasInstanciadas.size() < size) {
            List<String[]> paraRemover = new ArrayList();
            for (String[] materia : materiasConCorrelativas) {
                if(materiasInstanciadas.containsAll(Arrays.asList(materia[1].split(", ")))){
                    materiasInstanciadas.add(materia[0]);
                    String[] listaCorrelativas = materia[1].split(",");
                    List lista = new ArrayList<>();
                    for (String mat : listaCorrelativas) {
                        mat = mat.trim();
                        lista.add(mapaMaterias.get(mat));
                    }
                    Materia instancia = new Materia(materia[0], lista);
                    mapaMaterias.put(materia[0], instancia);
                    paraRemover.add(materia);
                }
            }
            for(String[] materia : paraRemover){
                materiasConCorrelativas.remove(materia);
            }
            if(contador > size*materiasConCorrelativas.size()){
                break;
            }
            contador++;
        }
        return mapaMaterias;
    }
}
