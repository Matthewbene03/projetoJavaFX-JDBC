package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = root.load();
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			scene = new Scene(scrollPane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("AppTeste");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
