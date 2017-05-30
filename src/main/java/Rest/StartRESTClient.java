package Rest;

import Domain.Excursie;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Alexandra Muresan on 5/19/2017.
 */
public class StartRESTClient {

    private final static ExcursiiClient excursiiClient = new ExcursiiClient();

    public static void main(String[] args){
        RestTemplate restTemplate = new RestTemplate();
        Excursie excursie = new Excursie(6,"Roma","EuroTrans",4,625.0,80);
        try{
            show(()-> excursiiClient.addExcursie(excursie));
            show(()->excursiiClient.deleteExcursie(4));

            show(()-> {
                Excursie ex = excursiiClient.cautaExcursieId(3);
                System.out.println(ex);
            });

            show(()->excursiiClient.updateExcursie(new Excursie(1,"Turnul din Pisa","Christian Tour",10,150.0,180)));
            show(()-> {
                Excursie[] excursii = excursiiClient.getAllExcursii();
                for(Excursie e : excursii){
                    System.out.println(e);
                }
            });
        }catch(RestClientException ex){
            ex.printStackTrace();
        }

    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            System.out.println("Service exception"+ e);
        }
    }
}
