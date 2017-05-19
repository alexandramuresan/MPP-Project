package ClientSide;

import Domain.Excursie;
import Domain.Notification;
import Domain.Rezervare;
import Domain.Utilizator;
import Services.AppService.IAgentieServer;
import Services.AppService.IAgentieServerAMS;
import Services.AppService.NotificationReceiver;
import Services.AppService.NotificationSubscriber;
import Utils.Observable;
import Utils.Observer;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class ClientControllerAMS implements NotificationSubscriber, Observable<Excursie>,Serializable {


    private IAgentieServerAMS server;
    private Utilizator user;
    List<Observer<Excursie>> observers = new ArrayList<>();
    private NotificationReceiver receiver;
    public void setReceiver(NotificationReceiver receiver) {
        this.receiver = receiver;
    }
    public ClientControllerAMS(IAgentieServerAMS server) {
        this.server = server;
    }

    public void login(String username,String password){
        Utilizator utilizator = new Utilizator(username,password);
        server.login(utilizator);
        user = utilizator;
        receiver.start(this);

    }

    public void logout(){
        server.logout(user);
        user = null;
        receiver.stop();
    }

    public List<Excursie> getAllExcursii(){
        return server.getAllExcursii();
    }

    public List<Excursie> cautaExcursie(String obiectiv,Integer ora1,Integer ora2){
        return server.cautaExcursie(obiectiv,ora1,ora2);
    }

    public List<Rezervare> getAllRezervari(){
        System.out.println("Rezervari size:"+server.getAllRezervari().size());
        return server.getAllRezervari();
    }
    public void rezervaBilete(Integer id,Excursie new_excursie){
        server.rezervaBilete(id,new_excursie);
        //notifyObservers();
    }
    public void addRezervare(Rezervare rez){
        server.addRezervare(rez);
    }

    @Override
    public void notificationReceived(Notification notif) {
        System.out.println("NOTIF");
        try {

            SwingUtilities.invokeLater(()->{
                switch (notif.getType()) {
                    case SHOW_UPDATED: {
                        System.out.println("NOTIFICATION RECEIVED");
                        notifyObservers();
                        break;
                    }

                }});
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void addObserver(Observer<Excursie> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Excursie> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.update(this);
        }
    }
}
