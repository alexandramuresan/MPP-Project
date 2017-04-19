package Interface;

import ClientSide.ClientController;
import Domain.Utilizator;
import Repository.UtilizatoriJdbcRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


/**
 * Created by Alexandra Muresan on 3/18/2017.
 */
public class LogInGUIController {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button login;

    ClientController ctrl;
    private Stage currentStage;

    @FXML
    public void initialize(ClientController clientController,
                           Stage primaryStage){
        this.ctrl = clientController;
        this.currentStage = primaryStage;
    }

    @FXML
    public void loginHandler(){
        try {
            ctrl.login(username.getText().toString(),password.getText().toString());

            //if successful
            Stage stage = new Stage();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LogInGUIController.class.getClassLoader().getResource("main.fxml"));
            BorderPane mainPane = loader.load();
            AgentieGUIController controller = loader.getController();
            controller.initialize(ctrl, stage);
            ctrl.addObserver(controller);
            Scene scene = new Scene(mainPane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
