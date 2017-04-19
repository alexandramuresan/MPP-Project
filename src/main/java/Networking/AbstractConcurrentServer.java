package Networking;

import java.net.Socket;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public abstract class AbstractConcurrentServer extends AbstractServer {


    public AbstractConcurrentServer(Integer port) {
        super(port);
    }

    @Override
    protected void processRequest(Socket socketClient){
        Thread tw = createWorker(socketClient);
        tw.start();
    }


    protected abstract Thread createWorker(Socket socketClient);
}
