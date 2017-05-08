package Repository.Interfaces;

import Utils.Observable;

import java.util.List;

/**
 * Created by Alexandra Muresan on 3/10/2017.
 */
public interface CRUDRepository<E,ID> extends Observable<E>{

    void save(E entity);
    void delete(ID id);
    //void update(ID id,E entity);
    List<E> getAll();
}
