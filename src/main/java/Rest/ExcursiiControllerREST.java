package Rest;

import Domain.Excursie;
import Repository.Hibernate.ExcursiiHibernateRepository;
import Repository.Interfaces.IExcursiiRepo;
import Repository.JDBC.ExcursiiJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Alexandra Muresan on 5/19/2017.
 */
@RestController
@RequestMapping("/agentie/excursii")
public class ExcursiiControllerREST {

    @Autowired
    private ExcursiiHibernateRepository excursiiRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Excursie[] getAllExcursii(){
        return excursiiRepository.getAll().toArray(new Excursie[excursiiRepository.getAll().size()]);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addExcursie(@RequestBody Excursie excursie){
        excursiiRepository.save(excursie);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public void updateExcursie(@RequestBody Excursie excursie_noua){
        excursiiRepository.updateExcursie(excursie_noua.getId(),excursie_noua);


    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteExcursie(@PathVariable Integer id){
        try{
            excursiiRepository.delete(id);
            return new ResponseEntity<Excursie>(HttpStatus.OK);

        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public ResponseEntity<?> cautaExcursieId(@PathVariable Integer id){
        Excursie excursie = excursiiRepository.getExcursie(id);
        if(excursie==null){
            return new ResponseEntity<String>("Not found",HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<Excursie>(excursie,HttpStatus.OK);
        }
    }


}
