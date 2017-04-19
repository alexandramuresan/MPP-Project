package Services.ModelService;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
import Repository.ExcursiiJdbcRepository;
import Repository.RezervariJdbcRepository;
import Repository.UtilizatoriJdbcRepository;
import Utils.Observer;

import java.util.List;

/**
 * Created by Alexandra Muresan on 3/14/2017.
 */
public class Service {

    private ExcursiiJdbcRepository excursii_repo;
    private RezervariJdbcRepository rezervari_repo;
    private UtilizatoriJdbcRepository utilizatori_repo;

    public Service(ExcursiiJdbcRepository excursii_repo, RezervariJdbcRepository rezervari_repo,UtilizatoriJdbcRepository utilizatori_repo){
        this.excursii_repo = excursii_repo;
        this.rezervari_repo = rezervari_repo;
        this.utilizatori_repo = utilizatori_repo;
    }

    public List<Excursie> getAllExcursii(){
        return excursii_repo.getAll();
    }

    public void addObserverExcursie(Observer<Excursie> o){
        excursii_repo.addObserver(o);
    }

    public List<Excursie> cautaExcursie(String obiectiv,Integer ora1,Integer ora2){
        return excursii_repo.cautaExursie(obiectiv,ora1,ora2);
    }

    public List<Rezervare> getAllRezervari(){
        return rezervari_repo.getAll();
    }

    public void updateExcursie(Integer id_excursie,Excursie e){
        excursii_repo.updateExcursie(id_excursie,e);
    }

    public void addRezervare(Rezervare r){
        rezervari_repo.save(r);
    }

    public Utilizator getUtilizator(String username, String password){
        for(Utilizator u : utilizatori_repo.getAll()){
            if(u.getUsername().compareTo(username)==0 && u.getPassword().compareTo(password)==0){
                return u;
            }
        }

        return null;
    }

    public Excursie getExcursie(Integer id){
        for(Excursie e : excursii_repo.getAll()){
            if(e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }
}
