package aed.javafx.modelosdb;

public class ResidenciaUniversidad {
	private Integer codigo;
	private String nombre;
	private String codigoUniversidad;
	private String nombreUniversidad;
	private Integer precioMensual;
	private String comedor;

	public ResidenciaUniversidad(Integer codigo, String nombre, String codigoUniversidad, Integer precioMensual,
			String comedor, String nombreUniversidad) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.codigoUniversidad = codigoUniversidad;
		this.nombreUniversidad = nombreUniversidad;
		this.precioMensual = precioMensual;
		this.comedor = comedor;
	}

	public ResidenciaUniversidad() {
		this(null, null, null, null, null, null);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoUniversidad() {
		return codigoUniversidad;
	}

	public void setCodigoUniversidad(String codigoUniversidad) {
		this.codigoUniversidad = codigoUniversidad;
	}

	public String getNombreUniversidad() {
		return nombreUniversidad;
	}

	public void setNombreUniversidad(String nombreUniversidad) {
		this.nombreUniversidad = nombreUniversidad;
	}

	public Integer getPrecioMensual() {
		return precioMensual;
	}

	public void setPrecioMensual(Integer precioMensual) {
		this.precioMensual = precioMensual;
	}

	public String getComedor() {
		return comedor;
	}

	public void setComedor(String comedor) {
		this.comedor = comedor;
	}

	@Override
	public String toString() {
		return "Residencia: " + codigo + ", " + nombre + ", " + codigoUniversidad + ", " + nombreUniversidad + ", "
				+ precioMensual + ", " + comedor + ".";
	}
}
