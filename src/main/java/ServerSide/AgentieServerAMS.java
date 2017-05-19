package ServerSide;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
import Services.AppService.IAgentieClient;
import Services.AppService.IAgentieNotificationService;
import Services.AppService.IAgentieServerAMS;
import Services.ModelService.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class AgentieServerAMS implements IAgentieServerAMS {

    private Service service;
    private IAgentieNotificationService notificationService;
    private Map<String, Utilizator> loggedClients;

    public AgentieServerAMS(Service service,IAgentieNotificationService notificationService){
        this.service = service;
        this.notificationService = notificationService;
        loggedClients = new ConcurrentHashMap<>();
    }
    @Override
    public List<Excursie> getAllExcursii() {
        return service.getAllExcursii();
    }

    @Override
    public List<Rezervare> getAllRezervari() {
        return service.getAllRezervari();
    }

    @Override
    public void addRezervare(Rezervare e) {
        service.addRezervare(e);
    }

    @Override
    public List<Excursie> cautaExcursie(String obiectiv, Integer ora1, Integer ora2) {
        return service.cautaExcursie(obiectiv,ora1,ora2);
    }

    @Override
    public void rezervaBilete(Integer id_excursie, Excursie new_excursie) {
        service.updateExcursie(id_excursie,new_excursie);
        notificationService.rezervareEfectuata(new_excursie);
    }

    @Override
    public void login(Utilizator utilizator) {
        Utilizator u = service.getUtilizator(utilizator.getUsername(),utilizator.getPassword());

        if(u == null){
            return;
        }
        if(loggedClients.get(u.getUsername())!=null){
            return;
        }

        loggedClients.put(u.getUsername(),u);
    }

    @Override
    public void logout(Utilizator utilizator) {
        if(loggedClients.get(utilizator.getUsername())==null){
            return;
        }
        loggedClients.remove(utilizator.getUsername());
    }
}
