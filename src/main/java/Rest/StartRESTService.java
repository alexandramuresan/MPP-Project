package Rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Alexandra Muresan on 5/19/2017.
 */
@ComponentScan({"Rest","Repository"})
@SpringBootApplication
public class StartRESTService {

    public static void main(String[] args){
        SpringApplication.run(StartRESTService.class,args);
    }
}
