package Interface;

import ClientSide.ClientController;
import ClientSide.ClientControllerAMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class LogInGUIControllerAMS {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button login;

    ClientControllerAMS ctrl;
    private Stage currentStage;

    @FXML
    public void initialize(ClientControllerAMS clientController,
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
            loader.setLocation(LogInGUIControllerAMS.class.getClassLoader().getResource("main.fxml"));
            BorderPane mainPane = loader.load();
            AgentieGUIControllerAMS controller = loader.getController();
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
