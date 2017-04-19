package Networking.DTO;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class UtilizatorDTO implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UtilizatorDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

}
