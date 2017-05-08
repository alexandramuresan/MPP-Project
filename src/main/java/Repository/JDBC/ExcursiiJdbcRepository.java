package Repository.JDBC;

import Domain.Excursie;
import Repository.Interfaces.IExcursiiRepo;
import Repository.JDBC.JdbcUtils;
import Utils.Observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Alexandra Muresan on 3/10/2017.
 */
public class ExcursiiJdbcRepository implements IExcursiiRepo {

    private JdbcUtils dbUtils;
    List<Observer<Excursie>> observers = new ArrayList<>();
    public ExcursiiJdbcRepository(Properties prop){
        dbUtils = new JdbcUtils(prop);
    }

    public void save(Excursie e){
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into excursii values (?,?,?,?,?,?)")){
            preStmt.setInt(1,e.getId());
            preStmt.setString(2,e.getObiectiv());
            preStmt.setString(3,e.getFirma());
            preStmt.setInt(4,e.getOra_plecare());
            preStmt.setDouble(5,e.getPret());
            preStmt.setInt(6,e.getLocuri_disponibile());
            int result=preStmt.executeUpdate();
            notifyObservers();


        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        try{
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }
    public void delete(Integer id) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from excursii where id=?")) {
            preStmt.setInt(1,id);
            int result = preStmt.executeUpdate();
            notifyObservers();

        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        try{
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public List<Excursie> getAll(){
        List<Excursie> all = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement("select * from excursii")){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Integer id = rs.getInt("id");
                    String obiectiv = rs.getString("obiectiv");
                    String firma = rs.getString("firma");
                    Integer ora_plecare = rs.getInt("ora_plecare");
                    Double pret = rs.getDouble("pret");
                    Integer locuri_disponibile = rs.getInt("locuri_disponibile");
                    Excursie ex = new Excursie(id,obiectiv,firma,ora_plecare,pret,locuri_disponibile);
                    all.add(ex);

                }

            }


        }catch(SQLException ex){
            ex.printStackTrace();
        }

        try{
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return all;
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

    public List<Excursie> cautaExursie(String obiectiv,Integer ora1,Integer ora2){
        List<Excursie> all = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement("select * from excursii where obiectiv=? and ora_plecare between ? and ? ")){

            stmt.setString(1,obiectiv);
            stmt.setInt(2,ora1);
            stmt.setInt(3,ora2);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){

                    Integer id = rs.getInt("id");
                    String ob = rs.getString("obiectiv");
                    String firma = rs.getString("firma");
                    Integer ora_plecare = rs.getInt("ora_plecare");
                    Double pret = rs.getDouble("pret");
                    Integer locuri_disponibile = rs.getInt("locuri_disponibile");
                    Excursie ex = new Excursie(id,ob,firma,ora_plecare,pret,locuri_disponibile);
                    all.add(ex);
                }

            }


        }catch(SQLException ex){
            ex.printStackTrace();
        }
        try{
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return all;


    }

    public void updateExcursie(Integer id_excursie,Excursie e){
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update excursii set locuri_disponibile=? where id=?")) {


            preStmt.setInt(1,e.getLocuri_disponibile());
            preStmt.setInt(2,id_excursie);
            int result = preStmt.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        finally {
            try {
                con.close();
                notifyObservers();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }


}
