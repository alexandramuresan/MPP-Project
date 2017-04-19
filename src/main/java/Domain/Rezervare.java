package Domain;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 3/10/2017.
 */
public class Rezervare implements HasID<Integer>,Serializable{

    private Integer id;
    private Integer id_excursie;
    private String nume_client;
    private String telefon;
    private Integer numar_bilete;

    @Override
    public Integer getId() {
        return id;
    }

    public Integer getId_excursie() {
        return id_excursie;
    }

    public void setId_excursie(Integer id_excursie) {
        this.id_excursie = id_excursie;
    }

    public String getNume_client() {
        return nume_client;
    }

    public void setNume_client(String nume_client) {
        this.nume_client = nume_client;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Integer getNumar_bilete() {
        return numar_bilete;
    }

    public void setNumar_bilete(Integer numar_bilete) {
        this.numar_bilete = numar_bilete;
    }

    public Rezervare(Integer id, Integer id_excursie, String nume_client, String telefon, Integer numar_bilete){
        this.id = id;
        this.id_excursie = id_excursie;

        this.nume_client = nume_client;
        this.telefon = telefon;
        this.numar_bilete = numar_bilete;
    }
    @Override
    public String toString(){
        return id+" "+id_excursie+" "+nume_client+" "+telefon+" "+numar_bilete+'\n';
    }
}
