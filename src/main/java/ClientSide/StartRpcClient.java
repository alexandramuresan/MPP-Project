package ClientSide;

import Interface.LogInGUIController;
import Networking.Protocols.AgentieServerRpcProxy;
import Services.AppService.IAgentieServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class StartRpcClient extends Application {

    private static int defaultPort = 55555;

    //default server
    private static String defaultServer = "localhost";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
        Properties clientProperties = new Properties();

        try {
            clientProperties.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
        } catch (IOException e) {
            System.err.println("Can't find properties file");
            return;
        }

        String serverIp = clientProperties.getProperty("proj.server.host");
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(clientProperties.getProperty("proj.server.port"));
        } catch (NumberFormatException ex) {
            System.out.println("Invalid port. Using default instead.");
        }

        System.out.println("Using ip " + serverIp);
        System.out.println("Using port " + serverPort);
*/
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
        IAgentieServer server = (IAgentieServer)factory.getBean("agentieService");
        ClientController clientController = new ClientController(server);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartRpcClient.class.getClassLoader().getResource("login.fxml"));
        BorderPane loginPane = loader.load();
        LogInGUIController controller = loader.getController();
        controller.initialize(clientController, primaryStage);
        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
