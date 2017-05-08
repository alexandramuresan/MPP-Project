package Repository.Interfaces;

import Domain.Excursie;

import java.util.List;

/**
 * Created by Alexandra Muresan on 4/1/2017.
 */
public interface IExcursiiRepo extends CRUDRepository<Excursie,Integer> {

    List<Excursie> cautaExursie(String obiectiv,Integer ora1,Integer ora2);
    void updateExcursie(Integer id_excursie,Excursie e);
}
