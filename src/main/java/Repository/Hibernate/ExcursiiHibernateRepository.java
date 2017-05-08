package Repository.Hibernate;

import Domain.Excursie;
import Repository.Interfaces.IExcursiiRepo;
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
public class ExcursiiHibernateRepository implements IExcursiiRepo {

    List<Observer<Excursie>> observers = new ArrayList<>();
    Configuration connection = null;
    ServiceRegistry serviceRegistry;
    SessionFactory sessionFactory;

    public ExcursiiHibernateRepository(){

        connection = new Configuration().configure("./hibernate.cfg.xml").addAnnotatedClass(Excursie.class);
        serviceRegistry = new ServiceRegistryBuilder().applySettings(connection.getProperties()).buildServiceRegistry();
        sessionFactory = connection.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void addObserver(Observer<Excursie> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Excursie> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.update(this);
        }
    }

    @Override
    public void save(Excursie entity) {

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
    public List<Excursie> getAll() {
        List<Excursie> excursii = new ArrayList<>();

        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            excursii= (List<Excursie>)session.createCriteria(Excursie.class).list();
            transaction.commit();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }

        return excursii;
    }

    @Override
    public List<Excursie> cautaExursie(String obiectiv, Integer ora1, Integer ora2) {
        List<Excursie> result = new ArrayList<>();
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("FROM Excursie WHERE obiectiv=:obiectiv AND ora_plecare BETWEEN :ora1 AND :ora2").setParameter("obiectiv",obiectiv).setParameter("ora1",ora1).setParameter("ora2",ora2).list();
            transaction.commit();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateExcursie(Integer id_excursie, Excursie e) {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createQuery("UPDATE Excursie SET locuri_disponibile=:locuri WHERE id=:id").setParameter("locuri",e.getLocuri_disponibile()).setParameter("id",id_excursie).executeUpdate();
            transaction.commit();
            notifyObservers();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }
    }
}
