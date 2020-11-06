package aed.javafx.accesoadb;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import aed.javafx.db.Conexion;
import aed.javafx.db.Consultas;
import aed.javafx.modelosdb.Estancias;
import aed.javafx.modelosdb.ResidenciaUniversidad;
import aed.javafx.utils.Mensaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;

public class ResidenciaController implements Initializable {
	private ResidenciaModel model;

	@FXML
	private TabPane view;

	@FXML
	private Tab residenciaTab;

	@FXML
	private Button insertarButton;

	@FXML
	private Button modificarButton;

	@FXML
	private Button eliminarButton;

	@FXML
	private Button visualizarButton;

	@FXML
	private Button cerrarConexionButton;

	@FXML
	private RadioButton sqlConexionRadio;

	@FXML
	private ToggleGroup conexionToggle;

	@FXML
	private RadioButton mysqlConexionButton;

	@FXML
	private RadioButton accessConexionButton;

	@FXML
	private TextField nombreResidenciaText;

	@FXML
	private TextField precioMensualText;

	@FXML
	private ComboBox<String> codigoUniversidadComboBox;

	@FXML
	private CheckBox comedorCheckBox;

	@FXML
	private TableView<ResidenciaUniversidad> residenciastableView;

	@FXML
	private TableColumn<ResidenciaUniversidad, Integer> codigoResidenciaColumn;

	@FXML
	private TableColumn<ResidenciaUniversidad, String> nomResidenciaColumn;

	@FXML
	private TableColumn<ResidenciaUniversidad, String> codigoUniversidadColumn;

	@FXML
	private TableColumn<ResidenciaUniversidad, Integer> precioMensualColumn;

	@FXML
	private TableColumn<ResidenciaUniversidad, String> comedorColumn;

	@FXML
	private TableColumn<ResidenciaUniversidad, String> nomUniversidadColumn;

	@FXML
	private Tab proceYFuncionesTab;
	@FXML
	private TextField dniText;

	@FXML
	private Button buscarDniButton;

	@FXML
	private Label fnMesesEstanciaLabel;

	@FXML
	private ComboBox<String> nomUniversidad2ComboBox;

	@FXML
	private TextField precioMensualProcText;

	@FXML
	private Button calcularButton;

	@FXML
	private Button insertarProcButton;

	@FXML
	private Label cantidadResideLabel;

	@FXML
	private Label cantidadResiPrecioText;

	@FXML
	private TableView<Estancias> estanciasTableView;

	@FXML
	private TableColumn<Estancias, String> nomResidencia2Column;

	@FXML
	private TableColumn<Estancias, String> nomUniversidad2Column;

	@FXML
	private TableColumn<Estancias, Date> fechaInicioColumn;

	@FXML
	private TableColumn<Estancias, Date> fechaFinColumn;

	@FXML
	private TableColumn<Estancias, Integer> precioMesual2Column;

	private Consultas consultas;
	private static final int PRECIO_MIN = 900;
	private static final int NOMBRE_RESIDENCIA = 30;
	private static final int TAM_DNI = 9;

	public ResidenciaController() throws IOException {
		// lo hacemos antes porque sino da error
		model = new ResidenciaModel();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ResidenciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindeos tab 1

		// TextField
		model.nombreResidenciaTextProperty().bindBidirectional(nombreResidenciaText.textProperty());
		model.precioMensualTextProperty().bindBidirectional(precioMensualText.textProperty());

		// CheckBox
		model.comedorCheckProperty().bindBidirectional(comedorCheckBox.selectedProperty());

		// Radio Button
		model.sqlConexionRadioProperty().bind(sqlConexionRadio.selectedProperty());
		model.mysqlConexionRadioProperty().bind(mysqlConexionButton.selectedProperty());
		model.accessConexionRadioProperty().bind(accessConexionButton.selectedProperty());
		// Disable radio button
		mysqlConexionButton.disableProperty()
				.bind(model.sqlConexionRadioProperty().or(model.accessConexionRadioProperty()));
		sqlConexionRadio.disableProperty()
				.bind(model.mysqlConexionRadioProperty().or(model.accessConexionRadioProperty()));
		accessConexionButton.disableProperty()
				.bind(model.sqlConexionRadioProperty().or(model.mysqlConexionRadioProperty()));

		// ComboBox
		model.codigoUniversidadesComboProperty().bindBidirectional(codigoUniversidadComboBox.itemsProperty());
		model.selectedUniversidadComboProperty().bindBidirectional(codigoUniversidadComboBox.valueProperty());

		// desabilitar botones si no hay ninguna conexion
		nombreResidenciaText.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull());
		precioMensualText.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull());
		comedorCheckBox.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull());
		codigoUniversidadComboBox.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull());
		cerrarConexionButton.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull());
		eliminarButton.disableProperty().bind(model.selectedResidenciaTablaProperty().isNull());
		visualizarButton.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull());

		// TABLA
		codigoResidenciaColumn.setCellValueFactory(new PropertyValueFactory<ResidenciaUniversidad, Integer>("codigo"));
		nomResidenciaColumn.setCellValueFactory(new PropertyValueFactory<ResidenciaUniversidad, String>("nombre"));
		codigoUniversidadColumn
				.setCellValueFactory(new PropertyValueFactory<ResidenciaUniversidad, String>("codigoUniversidad"));
		precioMensualColumn
				.setCellValueFactory(new PropertyValueFactory<ResidenciaUniversidad, Integer>("precioMensual"));
		comedorColumn.setCellValueFactory(new PropertyValueFactory<ResidenciaUniversidad, String>("comedor"));
		nomUniversidadColumn
				.setCellValueFactory(new PropertyValueFactory<ResidenciaUniversidad, String>("nombreUniversidad"));

		residenciastableView.itemsProperty().bind(model.residenciasTablaViewProperty());

		residenciastableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			model.setSelectedResidenciaTabla(nv);
			modificarSeleccionado();
		});

		// Bindeos tab 2
		proceYFuncionesTab.disableProperty().bind(conexionToggle.selectedToggleProperty().isNull().or(accessConexionButton.selectedProperty()));

		// TextField
		model.dniTextProperty().bindBidirectional(dniText.textProperty());
		// TODO REVISAR
		model.precioMensualProcTextProperty().bindBidirectional(precioMensualProcText.textProperty());

		// ComboBox
		model.codigoUniversidadesComboProperty().bindBidirectional(nomUniversidad2ComboBox.itemsProperty());
		model.selectedUniversidadComboProperty().bindBidirectional(nomUniversidad2ComboBox.valueProperty());

		// Label
		fnMesesEstanciaLabel.textProperty().bindBidirectional(model.fnMesesEstanciaLabelProperty(),
				new NumberStringConverter());
		cantidadResideLabel.textProperty().bindBidirectional(model.cantidadResideLabelProperty(),
				new NumberStringConverter());
		cantidadResiPrecioText.textProperty().bindBidirectional(model.cantidadResiPrecioTextProperty(),
				new NumberStringConverter());

		// TABLA2
		nomResidencia2Column.setCellValueFactory(new PropertyValueFactory<Estancias, String>("nombreResidencia"));
		nomUniversidad2Column.setCellValueFactory(new PropertyValueFactory<Estancias, String>("nomUniversidad"));
		fechaInicioColumn.setCellValueFactory(new PropertyValueFactory<Estancias, Date>("fechaInicio"));
		fechaFinColumn.setCellValueFactory(new PropertyValueFactory<Estancias, Date>("fechaFin"));
		precioMesual2Column.setCellValueFactory(new PropertyValueFactory<Estancias, Integer>("precioMensual"));

		estanciasTableView.itemsProperty().bind(model.estanciasTablaViewProperty());

		// DEASBILITAR BOTONES vista 2
		// si estan vacios los campos desabilitamos los botones
		insertarButton.disableProperty()
				.bind(model.nombreResidenciaTextProperty().isEmpty().or(model.precioMensualTextProperty().isEmpty()));
		modificarButton.disableProperty().bind(model.nombreResidenciaTextProperty().isEmpty()
				.or(model.precioMensualTextProperty().isEmpty()).or(model.selectedResidenciaTablaProperty().isNull()));
		insertarProcButton.disableProperty()
				.bind(model.nombreResidenciaTextProperty().isEmpty()
				.or(model.precioMensualTextProperty().isEmpty())
				.or(accessConexionButton.selectedProperty()));
		// listener radiobutton conexion
		conexionToggle.selectedToggleProperty().addListener((o, ov, nv) -> onConexionAction(nv));
	}

	private void onConexionAction(Toggle nv) {
		if (nv != null) {
			RadioButton rb = (RadioButton) nv;
			Connection conexion = null;
			switch (rb.getText()) {
			case "SQL":
				conexion = Conexion.conexionSql();
				break;
			case "MySQL":
				conexion = Conexion.conexionMysql();
				break;
			case "Access":
				conexion = Conexion.conexionAccess();
				break;
			}
			if (conexion != null) {
				consultas = new Consultas(conexion, rb.getText());
				cargarUniversidades();
			} else {
				conexionToggle.getSelectedToggle().setSelected(false);
			}
		}
	}

	/*
	 * insertarResidenciaProc nunca devolvera 0, 0 porque controlo en un combobox el
	 * nombre de la universidad y controlo tambien si la residencia tine el precio
	 * mayor a 900 y el nombre no sobrepasa los 30 caracteres
	 */
	@FXML
	void onInsertarProcAction(ActionEvent event) {
		try {
			int precio = Integer.parseInt(model.getPrecioMensualText());
			int nombreResidencia = model.getNombreResidenciaText().length();
			if (nombreResidencia <= NOMBRE_RESIDENCIA) {
				if (precio >= PRECIO_MIN) {
					String codigoUniversidad = consultas.getCodigoUniversidad(model.getSelectedUniversidadCombo());
					List<Integer> resul = consultas.insertarResidenciaProc(model.getNombreResidenciaText(),
							codigoUniversidad, precio, model.isComedorCheck());
					if (resul.get(0) == 1 && resul.get(1) == 1) {
						Mensaje.information("", "", "Se ha insertado correctamente");
						cargarTablaResidencias();
						limpiarCampos();
					}
				} else {
					Mensaje.error("Error Precio", "Error al insertar precio",
							"El precio tiene que ser igual o superior a 900");
				}
			} else {
				Mensaje.error("Error nombre residencia", "Error al insertar nombre residencia",
						"El nombre residencia tiene que tener una logitud de 30 caracteres como maximo");
			}
		} catch (NumberFormatException e) {
			Mensaje.error("Error de conversion", "", "Introduzca un precio en formato numerico.");
		}
	}

	@FXML
	void onBuscarDniAction(ActionEvent event) {
		String dni = model.getDniText();
		if (dni.length() == TAM_DNI) {
			model.estanciasTablaViewProperty().setAll(consultas.visualizarEstanciasDni(dni));
			model.setFnMesesEstanciaLabel(consultas.fnMesesEstancia(dni));
		} else {
			Mensaje.error("Error al leer DNI", "", "El tamaño del dni no es correcto");
		}
	}

	@FXML
	void onCalcularAction(ActionEvent event) {
		List<Integer> resultado = consultas.procUniversidadPrecio(model.getSelectedUniversidadCombo(),
				Integer.parseInt(model.getPrecioMensualProcText()));
		model.setCantidadResideLabel(resultado.get(0));
		model.setCantidadResiPrecioText(resultado.get(1));
	}

	private void cargarUniversidades() {
		model.codigoUniversidadesComboProperty().setAll(consultas.visualizarCombo());
		codigoUniversidadComboBox.getSelectionModel().select(0);
	}

	@FXML
	public void onCerrarConexionAction(ActionEvent event) {
		limpiarCampos();
		model.setSelectedUniversidadCombo("");
		conexionToggle.getSelectedToggle().setSelected(false);
		model.getResidenciasTablaView().clear();
		consultas.cerrar();
	}

	@FXML
	public void onEliminarAction(ActionEvent event) {
		Optional<ButtonType> result = Mensaje.confirmation("", "¿Esta seguro de que desea eliminar este registro?",
				model.getSelectedResidenciaTabla().toString());
		if (result.get() == ButtonType.OK) {
			consultas.eliminarResidencia(model.getSelectedResidenciaTabla().getCodigo());
			cargarTablaResidencias();
			limpiarCampos();
		}
	}

	@FXML
	public void onInsertarAction(ActionEvent event) {
		try {
			int precio = Integer.parseInt(model.getPrecioMensualText());
			int nombreResidencia = model.getNombreResidenciaText().length();
			if (nombreResidencia <= NOMBRE_RESIDENCIA) {
				if (precio >= PRECIO_MIN) {
					String codigoUniversidad = consultas.getCodigoUniversidad(model.getSelectedUniversidadCombo());
					boolean resul = consultas.insertarResidencia(model.getNombreResidenciaText(), codigoUniversidad,
							precio, model.isComedorCheck());
					if (resul) {
						Mensaje.information("", "", "Se ha insertado correctamente");
						cargarTablaResidencias();
						limpiarCampos();
					}
				} else {
					Mensaje.error("Error Precio", "Error al insertar precio",
							"El precio tiene que ser igual o superior a 900");
				}
			} else {
				Mensaje.error("Error nombre residencia", "Error al insertar nombre residencia",
						"El nombre residencia tiene que tener una logitud de 30 caracteres como maximo");
			}
		} catch (NumberFormatException e) {
			Mensaje.error("Error de conversion", "", "Introduzca un precio en formato numerico.");
		}
	}

	@FXML
	public void onModificarAction(ActionEvent event) {
		try {
			int precio = Integer.parseInt(model.getPrecioMensualText());
			int nombreResidencia = model.getNombreResidenciaText().length();
			if (nombreResidencia <= NOMBRE_RESIDENCIA) {
				if (precio >= PRECIO_MIN) {
					boolean resul = consultas.modificarResidencia(model.getSelectedResidenciaTabla().getCodigo(),
							model.getNombreResidenciaText(),
							consultas.getCodigoUniversidad(model.getSelectedUniversidadCombo()), precio,
							model.isComedorCheck());
					if (resul) {
						Mensaje.information("", "", "Se ha modificado correctamente");
						cargarTablaResidencias();
						limpiarCampos();
					}
				} else {
					Mensaje.error("Error Precio", "Error al modificar precio",
							"El precio tiene que ser igual o superior a 900");
				}
			} else {
				Mensaje.error("Error nombre residencia", "Error al insertar nombre residencia",
						"El nombre residencia tiene que tener una logitud de 30 caracteres como maximo");
			}
		} catch (NumberFormatException e) {
			Mensaje.error("Error de conversion", "", "Introduzca un precio en formato numerico.");
		}
	}

	@FXML
	public void onVisualizarAction(ActionEvent event) {
		cargarTablaResidencias();
	}

	private void modificarSeleccionado() {
		ResidenciaUniversidad ru = model.getSelectedResidenciaTabla();
		if (ru != null) {
			model.setNombreResidenciaText(ru.getNombre());
			model.setPrecioMensualText(ru.getPrecioMensual().toString());
			boolean comedor = ru.getComedor().equals("Sí") ? true : false;
			model.setComedorCheck(comedor);
			model.setSelectedUniversidadCombo(ru.getNombreUniversidad());
		}
	}

	private void cargarTablaResidencias() {
		model.residenciasTablaViewProperty().setAll(consultas.visualizarResidencias());
	}

	private void limpiarCampos() {
		model.setNombreResidenciaText("");
		model.setPrecioMensualText("");
		model.setComedorCheck(false);
		model.setSelectedUniversidadCombo(model.getCodigoUniversidadesCombo().get(0));
	}

	public TabPane getView() {
		return view;
	}
}
