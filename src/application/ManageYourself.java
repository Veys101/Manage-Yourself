package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movescreen.MoveScreen;

public class ManageYourself extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        MoveScreen move = new MoveScreen(root, primaryStage);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        
        Image icon = new Image(getClass().getResourceAsStream("/image/logo128x128.png"));
        primaryStage.getIcons().add(icon);
        
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
