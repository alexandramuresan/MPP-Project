package Utils;

/**
 * Created by Alexandra Muresan on 3/18/2017.
 */
public interface Observable<E> {
    void addObserver(Observer<E> o);
    void removeObserver(Observer<E> o);
    void notifyObservers();
}
