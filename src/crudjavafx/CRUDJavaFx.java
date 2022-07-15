/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudjavafx;

import java.net.URI;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class CRUDJavaFx extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        URI uri = Paths.get("src/crudjavafx/vistas/FXMLempleado.fxml").toAbsolutePath().toUri();
        
        System.out.println("URI + "+ uri.toString());
                
        Parent root = FXMLLoader.load(uri.toURL());
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
    
}
