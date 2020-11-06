package aed.javafx.main;

import aed.javafx.accesoadb.ResidenciaController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResidenciasApp extends Application{

	
	private ResidenciaController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new ResidenciaController();
		Scene scene = new Scene(controller.getView(),800,650);
		
		primaryStage.setTitle("Acceso a Base de Datos");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
