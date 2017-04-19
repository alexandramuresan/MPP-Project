package ClientSide;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
import Services.AppService.IAgentieClient;
import Services.AppService.IAgentieServer;
import Utils.Observable;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class ClientController implements IAgentieClient, Observable<Excursie> {


    private IAgentieServer server;
    private Utilizator user;
    List<Observer<Excursie>> observers = new ArrayList<>();
    public ClientController(IAgentieServer server){
        this.server = server;
    }

    public void login(String username,String password){
        Utilizator utilizator = new Utilizator(username,password);
        server.login(utilizator, this);
        user = utilizator;

    }

    public void logout(){
        server.logout(user,this);
        user = null;
    }

    public List<Excursie> getAllExcursii(){
        return server.getAllExcursii();
    }

    public List<Excursie> cautaExcursie(String obiectiv,Integer ora1,Integer ora2){
        return server.cautaExcursie(obiectiv,ora1,ora2);
    }

    public List<Rezervare> getAllRezervari(){
        return server.getAllRezervari();
    }
    public void rezervaBilete(Integer id,Excursie new_excursie){
        server.rezervaBilete(id,new_excursie);
       // notifyObservers();
    }
    public void addRezervare(Rezervare rez){
        server.addRezervare(rez);
    }
    @Override
    public void rezervareEfectuata(Excursie e) {
        notifyObservers();
    }


    @Override
    public void addObserver(Observer<Excursie> o) {
        System.out.println("add obs");
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
