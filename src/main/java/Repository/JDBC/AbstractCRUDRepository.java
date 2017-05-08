package Repository.JDBC;

import Domain.HasID;
import Repository.Interfaces.CRUDRepository;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra Muresan on 3/10/2017.
 */
public abstract class AbstractCRUDRepository<E extends HasID<ID>,ID> implements CRUDRepository<E,ID> {

    List<E> entities = new ArrayList<E>();
    List<Observer<E>> observers = new ArrayList<>();
    @Override
    public void save(E entity) {
        entities.add(entity);
    }

    @Override
    public void delete(ID id) {
        for(int i=0;i<entities.size();i++){
            if(entities.get(i).getId().equals(id)){
                entities.remove(i);
            }
        }
    }




    @Override
    public List<E> getAll() {
        return entities;
    }

}
