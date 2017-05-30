package Rest;

import Domain.Excursie;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Alexandra Muresan on 5/19/2017.
 */
public class ExcursiiClient {

    public static final String URL = "http://localhost:8080/agentie/excursii";
    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable){
        try {
            return callable.call();
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public Excursie[] getAllExcursii(){
        return execute(()-> restTemplate.getForObject(URL,Excursie[].class));
    }

    public void addExcursie(Excursie excursie) {
        execute(()->restTemplate.postForObject(URL,excursie,Excursie.class));
    }

    public void updateExcursie(Excursie excursie_noua){

        execute(() -> {
            restTemplate.put(String.format("%s/%d", URL, excursie_noua.getId()), excursie_noua);
            return null;

        });

    }

    public void deleteExcursie(Integer id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%d", URL, id));
            return null;
        });
    }

    public Excursie cautaExcursieId(Integer id){
        return execute(() -> restTemplate.getForObject(String.format("%s/%d", URL, id), Excursie.class));
    }

}
