package Repository.Hibernate;

import Domain.Excursie;
import Domain.Utilizator;
import Repository.Interfaces.IUtilizatorRepo;
import Utils.Observer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra Muresan on 5/8/2017.
 */
public class UtilizatorHibernateRepository implements IUtilizatorRepo {

    List<Observer<Utilizator>> observers = new ArrayList<>();
    Configuration connection = null;
    ServiceRegistry serviceRegistry;
    SessionFactory sessionFactory;

    public UtilizatorHibernateRepository(){
        connection = new Configuration().configure("./hibernate.cfg.xml").addAnnotatedClass(Utilizator.class);
        serviceRegistry = new ServiceRegistryBuilder().applySettings(connection.getProperties()).buildServiceRegistry();
        sessionFactory = connection.buildSessionFactory(serviceRegistry);
    }
    @Override
    public void addObserver(Observer<Utilizator> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Utilizator> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.update(this);
        }
    }

    @Override
    public int validateUser(String username, String password) {
        return 0;
    }

    @Override
    public void save(Utilizator entity) {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            notifyObservers();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer integer) {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(integer);
            transaction.commit();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public List<Utilizator> getAll() {
        List<Utilizator> utilizatori = new ArrayList<>();

        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            utilizatori= (List<Utilizator>)session.createCriteria(Utilizator.class).list();
            transaction.commit();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }

        return utilizatori;
    }
}
