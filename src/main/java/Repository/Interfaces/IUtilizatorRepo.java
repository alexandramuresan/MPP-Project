package Repository.Interfaces;

import Domain.Utilizator;

/**
 * Created by Alexandra Muresan on 4/1/2017.
 */
public interface IUtilizatorRepo extends CRUDRepository<Utilizator,Integer> {

    int validateUser(String username,String password);
}
