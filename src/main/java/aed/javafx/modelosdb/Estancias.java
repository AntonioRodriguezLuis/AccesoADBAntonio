package aed.javafx.modelosdb;

import java.util.Date;

public class Estancias {
	private String nombreResidencia;
	private String nomUniversidad;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer precioMensual;
	
	public Estancias(String nombreResidencia, String nomUniversidad, Date fechaInicio, Date fechaFin, Integer precioMensual){
		super();
		this.nombreResidencia = nombreResidencia;
		this.nomUniversidad = nomUniversidad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.precioMensual = precioMensual;
	}
	
	public Estancias() {
		this(null,null,null,null,null);
	}

	public String getNombreResidencia() {
		return nombreResidencia;
	}

	public void setNombreResidencia(String nombreResidencia) {
		this.nombreResidencia = nombreResidencia;
	}

	public String getNomUniversidad() {
		return nomUniversidad;
	}

	public void setNomUniversidad(String nomUniversidad) {
		this.nomUniversidad = nomUniversidad;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getPrecioMensual() {
		return precioMensual;
	}

	public void setPrecioMensual(Integer precioMensual) {
		this.precioMensual = precioMensual;
	}
}
