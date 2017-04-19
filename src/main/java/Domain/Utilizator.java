package Domain;

/**
 * Created by Alexandra Muresan on 3/14/2017.
 */
public class Utilizator implements HasID<Integer>{

    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

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
