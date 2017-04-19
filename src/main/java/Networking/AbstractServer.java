package Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public abstract class AbstractServer {

    private Integer port;

    private ServerSocket serverSocket = null;


    public AbstractServer(Integer port){
        this.port = port;
    }


    public void start(){
        try{
            serverSocket = new ServerSocket(port);
            while (true){
                System.out.println("Waiting for clients");


                Socket socketClient = serverSocket.accept();

                System.out.println("Client connected");


                processRequest(socketClient);
            }
        } catch (IOException e) {

        } finally {
            try{
                serverSocket.close();
            } catch (IOException e) {

            }
        }
    }


    protected abstract void processRequest(Socket socketClient);


    public void stop(){
        try{
            serverSocket.close();
        } catch (IOException e) {

        }
    }

}
