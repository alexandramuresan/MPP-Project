package Networking.DTO;

import Domain.Excursie;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 4/3/2017.
 */
public class RezervaBileteDTO implements Serializable {

    private Integer id;
    private Excursie new_excursie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Excursie getNew_excursie() {
        return new_excursie;
    }

    public void setNew_excursie(Excursie new_excursie) {
        this.new_excursie = new_excursie;
    }

    public RezervaBileteDTO(Integer id, Excursie new_excursie){
        this.id = id;
        this.new_excursie = new_excursie;
    }
}
