package Networking;

import Networking.Protocols.AgentieClientRpcWorker;
import Services.AppService.IAgentieServer;

import java.net.Socket;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class AgentieRpcConcurrentServer extends AbstractConcurrentServer {

    IAgentieServer server;

    public AgentieRpcConcurrentServer(Integer port,IAgentieServer server){
        super(port);
        this.server = server;
    }
    @Override
    protected Thread createWorker(Socket socketClient) {

        AgentieClientRpcWorker worker = new AgentieClientRpcWorker(server, socketClient);
        Thread tw = new Thread(worker);
        return tw;
    }
}
