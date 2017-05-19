package ServerSide;

import Networking.AbstractServer;
import Networking.AgentieAMSConcurrentServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.ServerException;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class StartAMSServer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:server-spring.xml");
        AbstractServer server=context.getBean("agentieTCPServer", AgentieAMSConcurrentServer.class);
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
