package ClientSide;

import Interface.LogInGUIController;
import Interface.LogInGUIControllerAMS;
import Services.AppService.IAgentieServer;
import Services.AppService.IAgentieServerAMS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class StartAMSClient extends Application{

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
        ClientControllerAMS clientController = (ClientControllerAMS)factory.getBean("agentieCtrl");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartAMSClient.class.getClassLoader().getResource("login.fxml"));
        BorderPane loginPane = loader.load();
        LogInGUIControllerAMS controller = loader.getController();
        controller.initialize(clientController, primaryStage);
        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
