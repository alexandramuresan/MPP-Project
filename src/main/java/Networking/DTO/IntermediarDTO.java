package Networking.DTO;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 4/3/2017.
 */
public class IntermediarDTO implements Serializable {
    private String obiectiv;
    private Integer ora1;
    private Integer ora2;

    public String getObiectiv() {
        return obiectiv;
    }

    public void setObiectiv(String obiectiv) {
        this.obiectiv = obiectiv;
    }

    public Integer getOra1() {
        return ora1;
    }

    public void setOra1(Integer ora1) {
        this.ora1 = ora1;
    }

    public Integer getOra2() {
        return ora2;
    }

    public void setOra2(Integer ora2) {
        this.ora2 = ora2;
    }

    public IntermediarDTO(String obiectiv, Integer ora1, Integer ora2){
        this.obiectiv = obiectiv;
        this.ora1 = ora1;
        this.ora2 = ora2;
    }
}
