package Networking.DTO;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class DTOUtils {

    public static Utilizator getUtilizatorFromDTO(UtilizatorDTO utilizator){

        String username = utilizator.getUsername();
        String password = utilizator.getPassword();
        return new Utilizator(username,password);
    }

    public static UtilizatorDTO getUtilizatorDTO(Utilizator utilizator){
        String username = utilizator.getUsername();
        String password = utilizator.getPassword();
        return new UtilizatorDTO(username,password);
    }

    public static Excursie getExcursieFromDTO(ExcursieDTO excursie){

        Integer id = excursie.getId();
        String obiectiv = excursie.getObiectiv();
        String firma = excursie.getFirma();
        Integer ora_plecare = excursie.getOra_plecare();
        Double pret = excursie.getPret();
        Integer locuri = excursie.getLocuri_disponibile();
        return new Excursie(id,obiectiv,firma,ora_plecare,pret,locuri);
    }

    public static ExcursieDTO getExcursieDTO(Excursie excursie){
        Integer id = excursie.getId();
        String obiectiv = excursie.getObiectiv();
        String firma = excursie.getFirma();
        Integer ora_plecare = excursie.getOra_plecare();
        Double pret = excursie.getPret();
        Integer locuri = excursie.getLocuri_disponibile();
        return new ExcursieDTO(id,obiectiv,firma,ora_plecare,pret,locuri);
    }

    public static List<Excursie> getExcursiiFromDTO(List<ExcursieDTO> excursii){
        List<Excursie> all = new ArrayList<>();
        for(ExcursieDTO ed : excursii){
            all.add(getExcursieFromDTO(ed));
        }
        return all;
    }

    public static List<ExcursieDTO> getExcursiiDTO(List<Excursie> excursii){
        List<ExcursieDTO> all = new ArrayList<>();
        for(Excursie e : excursii){
            all.add(getExcursieDTO(e));
        }

        return all;
    }
    public static Rezervare getRezervareFromDTO(RezervareDTO rezervare){

        Integer id = rezervare.getId();
        Integer id_excursie = rezervare.getId_excursie();
        String nume_client = rezervare.getNume_client();
        String telefon = rezervare.getTelefon();
        Integer numar_bilete = rezervare.getNumar_bilete();
        return new Rezervare(id,id_excursie,nume_client,telefon,numar_bilete);
    }

    public static RezervareDTO getRezervareDTO(Rezervare rezervare){
        Integer id = rezervare.getId();
        Integer id_excursie = rezervare.getId_excursie();
        String nume_client = rezervare.getNume_client();
        String telefon = rezervare.getTelefon();
        Integer numar_bilete = rezervare.getNumar_bilete();
        return new RezervareDTO(id,id_excursie,nume_client,telefon,numar_bilete);
    }

    public static List<Rezervare> getRezervariFromDTO(List<RezervareDTO> rezervari){
        List<Rezervare> all = new ArrayList<>();
        for(RezervareDTO ed : rezervari){
            all.add(getRezervareFromDTO(ed));
        }
        return all;
    }

    public static List<RezervareDTO> getRezervariDTO(List<Rezervare> rezervari){
        List<RezervareDTO> all = new ArrayList<>();
        for(Rezervare e : rezervari){
            all.add(getRezervareDTO(e));
        }

        return all;
    }

}
