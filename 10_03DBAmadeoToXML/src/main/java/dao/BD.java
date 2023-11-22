package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BD {

	/**
	 * Conector a la base de datos
	 */
	private static Connection conn = null;

	/**
	 * Tipo de base de datos [sqlite|mariadb|...]
	 */
	public static String typeDB = null;

	/**
	 * Constructor
	 * 
	 * Establece una conexión con la base de datos
	 */
	private BD() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("database.properties"));

			typeDB = prop.getProperty("db");
			String driver = prop.getProperty("driver");
			String dsn = prop.getProperty("dsn");
			String user = prop.getProperty("user", "");
			String pass = prop.getProperty("pass", "");
			Class.forName(driver);
			conn = DriverManager.getConnection(dsn, user, pass);

			createTables();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve una conexión a la base de datos
	 * 
	 * @return Conexión a la base de datos
	 */
	public static Connection getConnection() {
		if (conn == null) {
			new BD();
		}
		return conn;
	}

	/**
	 * Cierra la conexión
	 */
	public static void close() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Crea el esquema de la base de datos
	 * 
	 * Las tablas deben ser -respetando los nombres- departamento (#id, nombre,
	 * *jefe) empleado (#id, nombre, salario, nacido, *departamento) siendo # clave
	 * primaria y * clave ajena.
	 * 
	 * @throws SQLException
	 */
	private void createTables() {
		List<String> sqls = new ArrayList<String>();
		if (BD.typeDB.equals("sqlite")) {
			sqls.add("""
						CREATE TABLE IF NOT EXISTS departamento (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT UNIQUE NOT NULL,
							jefe INTEGER DEFAULT NULL
						)
					""");
			sqls.add("""
						CREATE TABLE IF NOT EXISTS empleado (
							id INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT UNIQUE NOT NULL,
							salario REAL DEFAULT 0.0,
							nacido TEXT DEFAULT NULL,
							departamento INTEGER DEFAULT NULL
						)
					""");
		}
		if (BD.typeDB.equals("mariadb")) {
			sqls.add("""
						CREATE TABLE IF NOT EXISTS departamento (
						  id INT PRIMARY KEY AUTO_INCREMENT,
						  nombre VARCHAR(255) UNIQUE NOT NULL,
						  jefe INT
						)
					""");
			sqls.add("""
						CREATE TABLE IF NOT EXISTS empleado (
						  id INT PRIMARY KEY AUTO_INCREMENT,
						  nombre VARCHAR(255) UNIQUE NOT NULL,
						  salario DECIMAL(10, 2) DEFAULT 0.0,
						  nacido DATE,
						  departamento INT
						)
					""");
		}
		try {
			Statement stmt = conn.createStatement();
			for (String sql : sqls) {
				stmt.execute(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}