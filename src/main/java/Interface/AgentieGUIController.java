package Interface;

import ClientSide.ClientController;
import Domain.Excursie;
import Domain.Rezervare;
import Utils.Observable;
import Utils.Observer;
import com.sun.deploy.util.SessionState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Services.ModelService.Service;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Created by Alexandra Muresan on 3/18/2017.
 */
public class AgentieGUIController implements Observer<Excursie> {

    @FXML
    TableView excursiiTable;
    @FXML
    TableColumn obiectivColumn;
    @FXML
    TableColumn firmaColumn;
    @FXML
    TableColumn ora_plecareColumn;
    @FXML
    TableColumn pretColumn;
    @FXML
    TableColumn locuri_disponibileColumn;

    @FXML
    TableView excursiiTable2;
    @FXML
    TableColumn obiectivColumn2;
    @FXML
    TableColumn firmaColumn2;
    @FXML
    TableColumn ora_plecareColumn2;
    @FXML
    TableColumn pretColumn2;
    @FXML
    TableColumn locuri_disponibileColumn2;

    @FXML
    TextField obiectivTxt;
    @FXML
    TextField ora1Txt;
    @FXML
    TextField ora2Txt;
    @FXML
    Button cautaBtn;

    @FXML
    TextField clientTxt;
    @FXML
    TextField telefonTxt;
    @FXML
    TextField nrBileteTxt;
    @FXML
    Button rezervaBtn;
    @FXML
    Button logOutBtn;
    ClientController ctrl;
    ObservableList<Excursie> model;
    Stage currentStage;
    public void setController(ClientController ctrl){
        this.ctrl = ctrl;

    }

    @Override
    public void update(Observable<Excursie> e) {
        System.out.println("Update ...");
        Platform.runLater(()->{
            System.out.println("Calling get Alll ");
            List<Excursie> le=ctrl.getAllExcursii();
            System.out.println("ads ");
        model.setAll(le);});
    }



    @FXML
    public void initialize(ClientController ctrl,Stage primaryStage){

        this.ctrl = ctrl;
        this.currentStage = primaryStage;
        obiectivColumn.setCellValueFactory(new PropertyValueFactory<Excursie, String>("obiectiv"));
        firmaColumn.setCellValueFactory(new PropertyValueFactory<Excursie,String>("firma"));
        ora_plecareColumn.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("ora_plecare"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<Excursie,Double>("pret"));
        locuri_disponibileColumn.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("locuri_disponibile"));

        obiectivColumn2.setCellValueFactory(new PropertyValueFactory<Excursie, String>("obiectiv"));
        firmaColumn2.setCellValueFactory(new PropertyValueFactory<Excursie,String>("firma"));
        ora_plecareColumn2.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("ora_plecare"));
        pretColumn2.setCellValueFactory(new PropertyValueFactory<Excursie,Double>("pret"));
        locuri_disponibileColumn2.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("locuri_disponibile"));
        model = FXCollections.observableArrayList(ctrl.getAllExcursii());
        excursiiTable.setItems(model);
    }

    @FXML
    public void cautaBtnHandler(){

        ObservableList<Excursie> result = FXCollections.observableList(ctrl.cautaExcursie(obiectivTxt.getText(),Integer.parseInt(ora1Txt.getText()),Integer.parseInt(ora2Txt.getText())));
        excursiiTable2.setItems(result);


    }

    @FXML
    public void rezervaBtnHandler(){

        int count = ctrl.getAllRezervari().size();
        Excursie ex = (Excursie)excursiiTable.getSelectionModel().getSelectedItem();
        int locuri_vechi = ex.getLocuri_disponibile();
        int locuri_noi = locuri_vechi - Integer.parseInt(nrBileteTxt.getText());
        if(locuri_noi>=0){
            Rezervare rez = new Rezervare(count+1,ex.getId(),clientTxt.getText(),telefonTxt.getText(),Integer.parseInt(nrBileteTxt.getText()));
            ctrl.addRezervare(rez);
            Excursie new_excursie = new Excursie(ex.getId(),ex.getObiectiv(),ex.getFirma(),ex.getOra_plecare(),ex.getPret(),locuri_noi);
            ctrl.rezervaBilete(ex.getId(),new_excursie);
        }








    }

    @FXML
    private void logOutHandler(){
        try{
            ctrl.logout();
            Stage stage = new Stage();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AgentieGUIController.class.getClassLoader().getResource("login.fxml"));
            BorderPane mainPane = loader.load();
            AgentieGUIController controller = loader.getController();
            controller.initialize(ctrl, stage);
            Scene scene = new Scene(mainPane);
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
