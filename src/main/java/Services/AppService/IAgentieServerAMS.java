package Services.AppService;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;

import java.util.List;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public interface IAgentieServerAMS {

    List<Excursie> getAllExcursii();

    List<Rezervare> getAllRezervari();

    void addRezervare(Rezervare e);

    List<Excursie> cautaExcursie(String obiectiv,Integer ora1,Integer ora2);

    void rezervaBilete(Integer id_excursie,Excursie new_excursie);

    void login(Utilizator utilizator);

    void logout(Utilizator utilizator);

}
