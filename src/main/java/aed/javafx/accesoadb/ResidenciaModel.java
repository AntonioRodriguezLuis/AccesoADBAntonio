package aed.javafx.accesoadb;

import aed.javafx.modelosdb.Estancias;
import aed.javafx.modelosdb.ResidenciaUniversidad;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ResidenciaModel {
	// BD
	private BooleanProperty sqlConexionRadio = new SimpleBooleanProperty();
	private BooleanProperty mysqlConexionRadio = new SimpleBooleanProperty();
	private BooleanProperty accessConexionRadio = new SimpleBooleanProperty();

	// Formulario
	private IntegerProperty idResidenciaText = new SimpleIntegerProperty();
	private StringProperty nombreResidenciaText = new SimpleStringProperty();
	private StringProperty precioMensualText = new SimpleStringProperty();
	private ListProperty<String> codigoUniversidadesCombo = new SimpleListProperty<String>(
			FXCollections.observableArrayList());
	private StringProperty selectedUniversidadCombo = new SimpleStringProperty();
	private BooleanProperty comedorCheck = new SimpleBooleanProperty();

	// Tabla
	private ListProperty<ResidenciaUniversidad> residenciasTablaView = new SimpleListProperty<ResidenciaUniversidad>(
			FXCollections.observableArrayList());
	private ObjectProperty<ResidenciaUniversidad> selectedResidenciaTabla = new SimpleObjectProperty<ResidenciaUniversidad>();

	// Segunda Pestaña
	private StringProperty dniText = new SimpleStringProperty();
	private IntegerProperty fnMesesEstanciaLabel = new SimpleIntegerProperty();
	private StringProperty precioMensualProcText = new SimpleStringProperty();
	private IntegerProperty cantidadResideLabel = new SimpleIntegerProperty();
	private IntegerProperty cantidadResiPrecioText = new SimpleIntegerProperty();

	// Segunda Tabla
	private ListProperty<Estancias> estanciasTablaView = new SimpleListProperty<Estancias>(
			FXCollections.observableArrayList());
	private ObjectProperty<Estancias> selectedEstaciaTabla = new SimpleObjectProperty<Estancias>();

	public final BooleanProperty sqlConexionRadioProperty() {
		return this.sqlConexionRadio;
	}

	public final boolean isSqlConexionRadio() {
		return this.sqlConexionRadioProperty().get();
	}

	public final void setSqlConexionRadio(final boolean sqlConexionRadio) {
		this.sqlConexionRadioProperty().set(sqlConexionRadio);
	}

	public final BooleanProperty mysqlConexionRadioProperty() {
		return this.mysqlConexionRadio;
	}

	public final boolean isMysqlConexionRadio() {
		return this.mysqlConexionRadioProperty().get();
	}

	public final void setMysqlConexionRadio(final boolean mysqlConexionRadio) {
		this.mysqlConexionRadioProperty().set(mysqlConexionRadio);
	}

	public final BooleanProperty accessConexionRadioProperty() {
		return this.accessConexionRadio;
	}

	public final boolean isAccessConexionRadio() {
		return this.accessConexionRadioProperty().get();
	}

	public final void setAccessConexionRadio(final boolean accessConexionRadio) {
		this.accessConexionRadioProperty().set(accessConexionRadio);
	}

	public final IntegerProperty idResidenciaTextProperty() {
		return this.idResidenciaText;
	}

	public final int getIdResidenciaText() {
		return this.idResidenciaTextProperty().get();
	}

	public final void setIdResidenciaText(final int idResidenciaText) {
		this.idResidenciaTextProperty().set(idResidenciaText);
	}

	public final StringProperty nombreResidenciaTextProperty() {
		return this.nombreResidenciaText;
	}

	public final String getNombreResidenciaText() {
		return this.nombreResidenciaTextProperty().get();
	}

	public final void setNombreResidenciaText(final String nombreResidenciaText) {
		this.nombreResidenciaTextProperty().set(nombreResidenciaText);
	}

	public final StringProperty precioMensualTextProperty() {
		return this.precioMensualText;
	}

	public final String getPrecioMensualText() {
		return this.precioMensualTextProperty().get();
	}

	public final void setPrecioMensualText(final String precioMensualText) {
		this.precioMensualTextProperty().set(precioMensualText);
	}

	public final ListProperty<String> codigoUniversidadesComboProperty() {
		return this.codigoUniversidadesCombo;
	}

	public final ObservableList<String> getCodigoUniversidadesCombo() {
		return this.codigoUniversidadesComboProperty().get();
	}

	public final void setCodigoUniversidadesCombo(final ObservableList<String> codigoUniversidadesCombo) {
		this.codigoUniversidadesComboProperty().set(codigoUniversidadesCombo);
	}

	public final ListProperty<ResidenciaUniversidad> residenciasTablaViewProperty() {
		return this.residenciasTablaView;
	}

	public final ObservableList<ResidenciaUniversidad> getResidenciasTablaView() {
		return this.residenciasTablaViewProperty().get();
	}

	public final void setResidenciasTablaView(final ObservableList<ResidenciaUniversidad> residenciasTablaView) {
		this.residenciasTablaViewProperty().set(residenciasTablaView);
	}

	public final StringProperty selectedUniversidadComboProperty() {
		return this.selectedUniversidadCombo;
	}

	public final String getSelectedUniversidadCombo() {
		return this.selectedUniversidadComboProperty().get();
	}

	public final void setSelectedUniversidadCombo(final String selectedUniversidadCombo) {
		this.selectedUniversidadComboProperty().set(selectedUniversidadCombo);
	}

	public final ObjectProperty<ResidenciaUniversidad> selectedResidenciaTablaProperty() {
		return this.selectedResidenciaTabla;
	}

	public final ResidenciaUniversidad getSelectedResidenciaTabla() {
		return this.selectedResidenciaTablaProperty().get();
	}

	public final void setSelectedResidenciaTabla(final ResidenciaUniversidad selectedResidenciaTabla) {
		this.selectedResidenciaTablaProperty().set(selectedResidenciaTabla);
	}

	public final BooleanProperty comedorCheckProperty() {
		return this.comedorCheck;
	}

	public final boolean isComedorCheck() {
		return this.comedorCheckProperty().get();
	}

	public final void setComedorCheck(final boolean comedorCheck) {
		this.comedorCheckProperty().set(comedorCheck);
	}

	public final StringProperty dniTextProperty() {
		return this.dniText;
	}
	

	public final String getDniText() {
		return this.dniTextProperty().get();
	}
	

	public final void setDniText(final String dniText) {
		this.dniTextProperty().set(dniText);
	}
	

	public final IntegerProperty fnMesesEstanciaLabelProperty() {
		return this.fnMesesEstanciaLabel;
	}
	

	public final int getFnMesesEstanciaLabel() {
		return this.fnMesesEstanciaLabelProperty().get();
	}
	

	public final void setFnMesesEstanciaLabel(final int fnMesesEstanciaLabel) {
		this.fnMesesEstanciaLabelProperty().set(fnMesesEstanciaLabel);
	}	

	public final StringProperty precioMensualProcTextProperty() {
		return this.precioMensualProcText;
	}
	

	public final String getPrecioMensualProcText() {
		return this.precioMensualProcTextProperty().get();
	}
	

	public final void setPrecioMensualProcText(final String precioMensualProcText) {
		this.precioMensualProcTextProperty().set(precioMensualProcText);
	}
	

	public final IntegerProperty cantidadResideLabelProperty() {
		return this.cantidadResideLabel;
	}
	

	public final int getCantidadResideLabel() {
		return this.cantidadResideLabelProperty().get();
	}
	

	public final void setCantidadResideLabel(final int cantidadResideLabel) {
		this.cantidadResideLabelProperty().set(cantidadResideLabel);
	}
	

	public final IntegerProperty cantidadResiPrecioTextProperty() {
		return this.cantidadResiPrecioText;
	}
	

	public final int getCantidadResiPrecioText() {
		return this.cantidadResiPrecioTextProperty().get();
	}
	

	public final void setCantidadResiPrecioText(final int cantidadResiPrecioText) {
		this.cantidadResiPrecioTextProperty().set(cantidadResiPrecioText);
	}
	

	public final ListProperty<Estancias> estanciasTablaViewProperty() {
		return this.estanciasTablaView;
	}
	

	public final ObservableList<Estancias> getEstanciasTablaView() {
		return this.estanciasTablaViewProperty().get();
	}
	

	public final void setEstanciasTablaView(final ObservableList<Estancias> estanciasTablaView) {
		this.estanciasTablaViewProperty().set(estanciasTablaView);
	}
	

	public final ObjectProperty<Estancias> selectedEstaciaTablaProperty() {
		return this.selectedEstaciaTabla;
	}
	

	public final Estancias getSelectedEstaciaTabla() {
		return this.selectedEstaciaTablaProperty().get();
	}
	

	public final void setSelectedEstaciaTabla(final Estancias selectedEstaciaTabla) {
		this.selectedEstaciaTablaProperty().set(selectedEstaciaTabla);
	}
	

}
