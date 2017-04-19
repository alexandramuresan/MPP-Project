package Services.AppService;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;

import java.util.List;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public interface IAgentieServer {

    List<Excursie> getAllExcursii();

    List<Rezervare> getAllRezervari();

    void addRezervare(Rezervare e);

    List<Excursie> cautaExcursie(String obiectiv,Integer ora1,Integer ora2);

    void rezervaBilete(Integer id_excursie,Excursie new_excursie);

    void login(Utilizator utilizator, IAgentieClient client);

    void logout(Utilizator utilizator,IAgentieClient client);



}
