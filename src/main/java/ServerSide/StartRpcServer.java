package ServerSide;

import Domain.Excursie;
import Networking.AbstractServer;
import Networking.AgentieRpcConcurrentServer;
import Repository.ExcursiiJdbcRepository;
import Repository.RezervariJdbcRepository;
import Repository.UtilizatoriJdbcRepository;
import Services.AppService.IAgentieServer;
import Services.ModelService.Service;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class StartRpcServer {

    private static int defaultPort = 262626;
    public static void main(String[] args){

        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("proj.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        ExcursiiJdbcRepository excursii_repo = new ExcursiiJdbcRepository(serverProps);
        RezervariJdbcRepository rezervari_repo = new RezervariJdbcRepository(serverProps);
        UtilizatoriJdbcRepository utilizatori_repo = new UtilizatoriJdbcRepository(serverProps);
        Service service = new Service(excursii_repo,rezervari_repo,utilizatori_repo);
        IAgentieServer agentieServer = new AgentieServer(service);

        AbstractServer server = new AgentieRpcConcurrentServer(serverPort,agentieServer);
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        }

    }
}
