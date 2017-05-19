package Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 3/14/2017.
 */
@Entity
@Table(name="utilizator")
public class Utilizator implements HasID<Integer>,Serializable{

    @Id
    @Column(name="id")
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Column(name="nume")
    private String username;
    @Column(name="parola")
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

    public Utilizator(){

    }

    public Utilizator(Integer id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;

    }

    public Utilizator(String username,String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return id+" "+username+" "+password+"\n";
    }
}
