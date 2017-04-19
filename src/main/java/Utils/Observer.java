package Utils;

/**
 * Created by Alexandra Muresan on 3/18/2017.
 */
public interface Observer<E> {
    void update(Observable<E> e);
}
