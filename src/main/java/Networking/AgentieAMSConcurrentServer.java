package Networking;

import Networking.Protocols.AgentieClientAMSWorker;
import Services.AppService.IAgentieServerAMS;

import java.net.Socket;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class AgentieAMSConcurrentServer extends AbstractConcurrentServer {

    private IAgentieServerAMS server;

    public AgentieAMSConcurrentServer(int port,IAgentieServerAMS server){
        super(port);
        this.server = server;
    }
    @Override
    protected Thread createWorker(Socket socketClient) {
        AgentieClientAMSWorker worker = new AgentieClientAMSWorker(server, socketClient);
        Thread tw = new Thread(worker);
        return tw;
    }
}
