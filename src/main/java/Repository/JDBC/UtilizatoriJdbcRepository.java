package Repository.JDBC;

import Domain.Utilizator;
import Repository.Interfaces.IUtilizatorRepo;
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
 * Created by Alexandra Muresan on 3/18/2017.
 */
public class UtilizatoriJdbcRepository implements IUtilizatorRepo {

    private JdbcUtils dbUtils;
    public UtilizatoriJdbcRepository(Properties props){
        dbUtils = new JdbcUtils(props);
    }


    @Override
    public void save(Utilizator entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Utilizator> getAll() {

        List<Utilizator> all = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement("select * from utilizator")){
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    Integer id = result.getInt("id");
                    String username = result.getString("nume");
                    String password = result.getString("parola");
                    Utilizator u = new Utilizator(id,username,password);
                    all.add(u);
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

    public int validateUser(String username,String password){
        for(Utilizator u : getAll()){
            if(u.getUsername().compareTo(username)==0 && u.getPassword().compareTo(password)==0){
                return 1;
            }
        }
        return -1;
    }


    @Override
    public void addObserver(Observer<Utilizator> o) {

    }

    @Override
    public void removeObserver(Observer<Utilizator> o) {

    }

    @Override
    public void notifyObservers() {

    }
}
