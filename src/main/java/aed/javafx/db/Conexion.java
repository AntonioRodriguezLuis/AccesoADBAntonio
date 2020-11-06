package aed.javafx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import aed.javafx.utils.Mensaje;

public class Conexion {

	public static Connection conexionMysql() {
		String usuario = "root";
		String password = null;
		String url = "jdbc:mysql://localhost:3306/bdresidenciasescolares";
		Connection conexion = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, usuario, password);
		} catch (ClassNotFoundException | SQLException e) {
			Mensaje.error("Error","No se pudo hacer la conexion con Mysql ","Este es el error: "+e);
		}
		return conexion;
	}
	
	public static Connection conexionSql() {
		String usuario = "sa";
		String password = "sa";
		String url = "jdbc:sqlserver://localhost;database=bdResidenciasEscolares";
		Connection conexion = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, usuario, password);
		} catch (ClassNotFoundException | SQLException e) {
			Mensaje.error("Error","No se pudo hacer la conexion con SQL ","Este es el error: "+ e);
		}
		return conexion;
	}

	public static Connection conexionAccess() {
		String usuario = "sa";
		String password = "sa";
		String url = "jdbc:ucanaccess://src//main//resources//DB//ResidenciasEscolares.accdb";
		Connection conexion = null;
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				conexion = DriverManager.getConnection(url, usuario, password);
			} catch (ClassNotFoundException | SQLException e) {
				Mensaje.error("Error","No se pudo hacer la conexion con Access ","Este es el error: "+e);
			}			
		
		return conexion;
	}

	// Utilizamos esta funcion para cerra la conexiones a cualquiera de las 3 base de datos
	public static void cerrar(Connection conexion) {
		try {
			if (conexion != null) {
				conexion.close();
			}
		} catch (SQLException e) {
			Mensaje.error("Error","No se pudo cerrar la conexion ","Este es el error: "+e);
		}
	} 
	
}
