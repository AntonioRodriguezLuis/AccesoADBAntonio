package aed.javafx.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import aed.javafx.modelosdb.Estancias;
import aed.javafx.modelosdb.ResidenciaUniversidad;
import aed.javafx.utils.Mensaje;

public class Consultas {
	private Connection conexion;
	private String tipoConexion;

	public Consultas(Connection conexion, String tipoConexion) {
		this.conexion = conexion;
		this.tipoConexion = tipoConexion;
	}

	public List<ResidenciaUniversidad> visualizarResidencias() {
		List<ResidenciaUniversidad> tablaResidencia = new ArrayList<>();
		try {
			String sql = "SELECT codResidencia, nomResidencia, r.codUniversidad, precioMensual, comedor, nomUniversidad FROM residencias r INNER JOIN universidades u ON r.codUniversidad = u.codUniversidad";
			PreparedStatement consulta = conexion.prepareStatement(sql);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				String comedor = resultado.getBoolean(5) ? "Si" : "No";
				tablaResidencia.add(new ResidenciaUniversidad(resultado.getInt(1), resultado.getString(2),
						resultado.getString(3), resultado.getInt(4), comedor, resultado.getString(6)));
			}
		} catch (SQLException e) {
			Mensaje.error("Error al rellenar la tabla", "", "No se pudo rellenar la tabla");
			e.printStackTrace();
		}
		return tablaResidencia;
	}

	public List<String> visualizarCombo() {
		List<String> tablaUniversidades = new ArrayList<>();
		try {
			String sql = "SELECT nomUniversidad FROM universidades";
			PreparedStatement consulta = conexion.prepareStatement(sql);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				tablaUniversidades.add(resultado.getString(1));
			}
		} catch (SQLException e) {
			Mensaje.error("Error al cargar el combo", "", "No se pudo rellenar el comboBox");
		}
		return tablaUniversidades;
	}

	public boolean insertarResidencia(String nombre, String codigoUniversidad, int precioMensual, boolean comedor) {
		boolean resultado = false;
		try {
			String sql = "INSERT INTO residencias(nomResidencia,codUniversidad,precioMensual,comedor) VALUES (?,?,?,?)";
			PreparedStatement consulta = conexion.prepareStatement(sql);
			consulta.setString(1, nombre);
			consulta.setString(2, codigoUniversidad);
			consulta.setInt(3, precioMensual);
			consulta.setBoolean(4, comedor);
			consulta.execute();
			resultado = true;
		} catch (SQLException e) {
			Mensaje.error("Error al insertar ", "", "No se pudo insertar");
		}
		return resultado;
	}

	public String getCodigoUniversidad(String nombreUniversidad) {
		String resultado = null;
		try {
			String sql = "SELECT codUniversidad FROM universidades WHERE nomUniversidad = ?";
			PreparedStatement consulta = conexion.prepareStatement(sql);
			consulta.setString(1, nombreUniversidad);
			ResultSet result = consulta.executeQuery();
			while (result.next()) {
				resultado = result.getString(1);
			}
		} catch (SQLException e) {
			Mensaje.error("Error", "", "No se pudo encontrar esa universidad");
			e.printStackTrace();
		}
		return resultado;
	}

	public void eliminarResidencia(int codigoResidencia) {
		try {
			String sql = "DELETE FROM residencias WHERE codResidencia = ?";
			PreparedStatement consulta = conexion.prepareStatement(sql);
			consulta.setInt(1, codigoResidencia);
			consulta.execute();
		} catch (Exception e) {
			Mensaje.error("Error al Borrar", "No se pudo eliminar el registro", e.getMessage());
		}
	}

	public boolean modificarResidencia(int codigoResidencia, String nombre, String codigoUniversidad, int precioMensual,
			boolean comedor) {
		boolean resultado = false;
		try {
			String sql = "UPDATE residencias SET nomResidencia = ?, codUniversidad = ?, precioMensual = ?, comedor = ? WHERE codResidencia = ?";
			PreparedStatement consulta = conexion.prepareStatement(sql);
			consulta.setString(1, nombre);
			consulta.setString(2, codigoUniversidad);
			consulta.setInt(3, precioMensual);
			consulta.setBoolean(4, comedor);
			consulta.setInt(5, codigoResidencia);
			consulta.execute();
			resultado = true;
		} catch (SQLException e) {
			Mensaje.error("Error al modificar", "", "No se pudo modificar el registro");
			e.printStackTrace();
		}
		return resultado;
	}

	public void cerrar() {
		Conexion.cerrar(conexion);
	}

	public List<Estancias> visualizarEstanciasDni(String dni) {
		List<Estancias> tablaEstancia = new ArrayList<>();
		try {
			String sql = null;
			if (tipoConexion.equals("MySQL")) {
				sql = "CALL SP_procedimiento1(?)";
			} else if (tipoConexion.equals("SQL")) {
				sql = "EXEC SP_procedimiento1 ?";
			}
			CallableStatement consulta = conexion.prepareCall(sql);
			consulta.setString(1, dni);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				tablaEstancia.add(new Estancias(resultado.getString(1), resultado.getString(2), resultado.getDate(3),
						resultado.getDate(4), resultado.getInt(5)));
			}
		} catch (SQLException e) {
			Mensaje.error("Error al rellenar la tabla", "", "No se pudo rellenar la tabla");
			e.printStackTrace();
		}
		return tablaEstancia;
	}

	public int fnMesesEstancia(String dni) {
		int totalMeses = 0;
		try {
			String sql = null;
			if (tipoConexion.equals("MySQL")) {
				sql = "SELECT FN_MesesEstancia(?)";
			} else if (tipoConexion.equals("SQL")) {
				sql = "SELECT dbo.FN_MesesEstancia(?)";
			}
			PreparedStatement consulta = conexion.prepareStatement(sql);
			consulta.setString(1, dni);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				totalMeses = resultado.getInt(1);
			}
		} catch (SQLException e) {
			Mensaje.error("Error meses estancia", "", "No se pudo calcular los meses de estancia");
		}
		return totalMeses;
	}

	public List<Integer> procUniversidadPrecio(String nombreUniversidad, int precio) {
		List<Integer> resultado = new ArrayList<>();
		try {
			String sql = null;
			if (tipoConexion.equals("MySQL")) {
				sql = "CALL SP_nombreUniversidadPrec(?,?,?,?)";
			} else if (tipoConexion.equals("SQL")) {
				sql = "exec SP_nombreUniversidadPrec ?,?,?,?";
			}
			CallableStatement consulta = conexion.prepareCall(sql);
			consulta.setString(1, nombreUniversidad);
			consulta.setInt(2, precio);
			consulta.registerOutParameter(3, Types.INTEGER);
			consulta.registerOutParameter(4, Types.INTEGER);
			consulta.execute();
			resultado.add(consulta.getInt(3));
			resultado.add(consulta.getInt(4));
		} catch (SQLException e) {
			Mensaje.error("Error Precio Universidad", "", "No se pudo calcular las cantidades residencia y precio");
			e.printStackTrace();
		}
		return resultado;
	}
	
	public List<Integer> insertarResidenciaProc(String nombre, String codigoUniversidad, int precioMensual, boolean comedor) {
		List<Integer> resultado = new ArrayList<>();
		try {
			String sql = null;
			if (tipoConexion.equals("MySQL")) {
				sql = "CALL SP_insertResidenciaEscolar(?,?,?,?,?,?)";
			} else if (tipoConexion.equals("SQL")) {
				sql = "exec SP_insertResidenciaEscolar ?,?,?,?,?,?";
			}
			CallableStatement consulta = conexion.prepareCall(sql);
			consulta.setString(1, nombre);
			consulta.setString(2, codigoUniversidad);
			consulta.setInt(3, precioMensual);
			consulta.setBoolean(4, comedor);
			consulta.registerOutParameter(5, Types.INTEGER);
			consulta.registerOutParameter(6, Types.INTEGER);
			consulta.execute();
			resultado.add(consulta.getInt(5));
			resultado.add(consulta.getInt(6));
		} catch (SQLException e) {
			Mensaje.error("Error al insertar ", "", "No se pudo insertar");
		}
		return resultado;
	}
}
