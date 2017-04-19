package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Alexandra Muresan on 3/10/2017.
 */
public class JdbcUtils {

    private Properties properties;

    public JdbcUtils(Properties properties){
        this.properties = properties;
    }
    private Connection instance=null;
    private Connection getNewConnection(){
        String driver=properties.getProperty("proj.jdbc.driver");
        String url=properties.getProperty("proj.jdbc.url");
        Connection con=null;
        try {
            Class.forName(driver);
            con= DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver "+e);
        } catch (SQLException e) {
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }
}
