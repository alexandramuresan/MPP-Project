package Repository;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
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
public class RezervariJdbcRepository implements IRezervariRepo {

    private  JdbcUtils dbUtils;
    List<Observer<Rezervare>> observers = new ArrayList<>();
    public RezervariJdbcRepository(Properties prop){
        dbUtils = new JdbcUtils(prop);
    }

    public void save(Rezervare r){
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into rezervari values (?,?,?,?,?)")){
            preStmt.setInt(1,r.getId());
            preStmt.setInt(2,r.getId_excursie());
            preStmt.setString(3,r.getNume_client());
            preStmt.setString(4,r.getTelefon());
            preStmt.setInt(5,r.getNumar_bilete());
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
        try (PreparedStatement preStmt = con.prepareStatement("delete from rezervari where id=?")) {
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
    public List<Rezervare> getAll(){
        Connection con = dbUtils.getConnection();
        List<Rezervare> all = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement("select * from rezervari")){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Integer id = rs.getInt("id");
                    Integer id_excursie = rs.getInt("id_excursie");
                    String nume_client = rs.getString("nume_client");
                    String telefon = rs.getString("telefon");
                    Integer numar_bilete = rs.getInt("numar_bilete");
                    Rezervare rez = new Rezervare(id,id_excursie,nume_client,telefon,numar_bilete);
                    all.add(rez);

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
    public void addObserver(Observer<Rezervare> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Rezervare> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.update(this);
        }
    }


}
