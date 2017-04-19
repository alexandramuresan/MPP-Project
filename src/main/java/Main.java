import Interface.AgentieGUIController;
import Interface.LogInGUIController;
import Services.ModelService.Service;
import Repository.UtilizatoriJdbcRepository;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static javafx.application.Application.launch;

/**
 * Created by Alexandra Muresan on 3/10/2017.
 */
public class Main extends Application {

    private Button loginBtn;
    private Button logoutBtn;
    private LogInGUIController logInGUIController;
    private Scene mainScene;
    private Scene loginScene;
    private Stage theStage;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*

        theStage = primaryStage;
        ApplicationContext fact = new AnnotationConfigApplicationContext(SpringConfig.class);

        UtilizatoriJdbcRepository login_repo = fact.getBean(UtilizatoriJdbcRepository.class);
        Service ctrl = fact.getBean(Service.class);

        //Login Scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getClassLoader().getResource("login.fxml"));
        BorderPane borderPane = loader.load();
        loginScene = new Scene(borderPane,600,500);
        logInGUIController = loader.getController();
        logInGUIController.setRepo(login_repo);
        loginBtn = logInGUIController.getLogin();
        loginBtn.setOnAction(e->loginClicked(e));
        primaryStage.setScene(loginScene);
        primaryStage.show();

        //Main Scene
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getClassLoader().getResource("main.fxml"));
        BorderPane borderPane1 = loader1.load();
        AgentieGUIController agentieGUIController = loader1.getController();
        agentieGUIController.setController(ctrl);
        ctrl.addObserverExcursie(agentieGUIController);
        logoutBtn = agentieGUIController.getLogOutBtn();
        logoutBtn.setOnAction(e->loginClicked(e));

        mainScene = new Scene(borderPane1,760,500);

    }

    public void loginClicked(ActionEvent e){
        if(e.getSource()==loginBtn && logInGUIController.validateUser()==1){
            theStage.setScene(mainScene);
        }
        if(e.getSource()==logoutBtn){
            theStage.setScene(loginScene);
        }
    }
    */
    }
}
