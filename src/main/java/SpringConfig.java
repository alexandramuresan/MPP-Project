import Repository.Hibernate.ExcursiiHibernateRepository;
import Repository.Hibernate.UtilizatorHibernateRepository;
import Repository.JDBC.ExcursiiJdbcRepository;
import Repository.JDBC.RezervariJdbcRepository;
import Services.ModelService.Service;
import Repository.JDBC.UtilizatoriJdbcRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Alexandra Muresan on 3/14/2017.
 */
@Configuration
public class SpringConfig {
    @Bean
    @Primary
    public Properties jdbcProps(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(getClass().getResourceAsStream("/database_config.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);

        }

        return  serverProps;

    }
    @Bean(name="excursiiRepo")
    public ExcursiiJdbcRepository createExcursiiRepository(Properties jdbcProps){
        return new ExcursiiJdbcRepository(jdbcProps);

    }
    @Bean(name="rezervariRepo")
    public RezervariJdbcRepository createRezervariRepository(Properties jdbcProps){
        return new RezervariJdbcRepository(jdbcProps);
    }
    @Bean(name="utilizatorRepo")
    public UtilizatoriJdbcRepository createUtilizatorRepository(Properties jdbcProps){
        return new UtilizatoriJdbcRepository(jdbcProps);
    }
    @Bean(name="controller")
    public Service createController(ExcursiiHibernateRepository excursii_repo, RezervariJdbcRepository rezervari_repo, UtilizatorHibernateRepository utilizatori_repo){
        return new Service(excursii_repo,rezervari_repo,utilizatori_repo);
    }
}

