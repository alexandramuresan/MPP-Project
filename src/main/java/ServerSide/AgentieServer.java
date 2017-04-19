package ServerSide;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
import Services.AppService.IAgentieClient;
import Services.AppService.IAgentieServer;
import Services.ModelService.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class AgentieServer implements IAgentieServer {

    private Service service;

    private final int defaultThreadsNumber = 5;

    private Map<String, IAgentieClient> loggedClients;

    public AgentieServer(Service service){
        this.service = service;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized List<Excursie> getAllExcursii() {
        return service.getAllExcursii();
    }

    @Override
    public synchronized  List<Rezervare> getAllRezervari(){ return service.getAllRezervari();}
    @Override
    public synchronized List<Excursie> cautaExcursie(String obiectiv, Integer ora1, Integer ora2) {
        return service.cautaExcursie(obiectiv,ora1,ora2);
    }

    @Override
    public synchronized void rezervaBilete(Integer id_excursie,Excursie new_excursie) {

       service.updateExcursie(id_excursie,new_excursie);
        notifyUsersTransaction(new_excursie);

    }
    @Override
    public synchronized void addRezervare(Rezervare r){
        service.addRezervare(r);
    }
    @Override
    public void login(Utilizator utilizator, IAgentieClient client) {

        Utilizator u = service.getUtilizator(utilizator.getUsername(),utilizator.getPassword());

        if(u == null){
            return;
        }
        if(loggedClients.get(u.getUsername())!=null){
            return;
        }

        loggedClients.put(u.getUsername(),client);
    }

    @Override
    public void logout(Utilizator utilizator, IAgentieClient client) {

        if(loggedClients.get(utilizator.getUsername())==null){
            return;
        }
        loggedClients.remove(utilizator.getUsername());
    }
    private void notifyUsersTransaction(Excursie excursie) {

        Iterable<String> loggedUsers = loggedClients.keySet();

        //create executor service
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNumber);

        //notifies all clients
        for (String username : loggedUsers){
            executorService.execute(() -> {
                try {

                        IAgentieClient client = loggedClients.get(username);
                        client.rezervareEfectuata(excursie);

                } catch (Exception e) {
                    System.err.println("Error notifying user");
                }
            });
        }
    }
}
