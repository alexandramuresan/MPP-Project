package Networking.DTO;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class RezervareDTO implements Serializable {

    private Integer id;
    private Integer id_excursie;
    private String nume_client;
    private String telefon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private Integer numar_bilete;

    public RezervareDTO(Integer id, Integer id_excursie, String nume_client, String telefon, Integer numar_bilete){
        this.id = id;
        this.id_excursie = id_excursie;

        this.nume_client = nume_client;
        this.telefon = telefon;
        this.numar_bilete = numar_bilete;
    }
}
