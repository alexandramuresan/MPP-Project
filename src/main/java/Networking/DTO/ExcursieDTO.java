package Networking.DTO;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class ExcursieDTO implements Serializable {

    private Integer id;
    private String obiectiv;
    private String firma;
    private Integer ora_plecare;
    private Double pret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObiectiv() {
        return obiectiv;
    }

    public void setObiectiv(String obiectiv) {
        this.obiectiv = obiectiv;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Integer getOra_plecare() {
        return ora_plecare;
    }

    public void setOra_plecare(Integer ora_plecare) {
        this.ora_plecare = ora_plecare;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public Integer getLocuri_disponibile() {
        return locuri_disponibile;
    }

    public void setLocuri_disponibile(Integer locuri_disponibile) {
        this.locuri_disponibile = locuri_disponibile;
    }

    private Integer locuri_disponibile;

    public ExcursieDTO(Integer id,String obiectiv,String firma,Integer ora_plecare,Double pret,Integer locuri_disponibile){
        this.id = id;
        this.obiectiv = obiectiv;
        this.firma = firma;
        this.ora_plecare = ora_plecare;
        this.pret = pret;
        this.locuri_disponibile = locuri_disponibile;
    }

    @Override
    public String toString() {
        return "ExcursieDTO{" +
                "id=" + id +
                ", obiectiv='" + obiectiv + '\'' +
                ", firma='" + firma + '\'' +
                ", ora_plecare=" + ora_plecare +
                ", pret=" + pret +
                ", locuri_disponibile=" + locuri_disponibile +
                '}';
    }
}
