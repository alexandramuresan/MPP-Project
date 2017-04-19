package Networking.Protocols;

import Domain.Excursie;
import Networking.DTO.*;
import Domain.Rezervare;
import Domain.Utilizator;
import Services.AppService.IAgentieClient;
import Services.AppService.IAgentieServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class AgentieServerRpcProxy implements IAgentieServer {

    private String host;
    private Socket connection;
    private int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private BlockingQueue<Response> queueResponses;
    private volatile boolean finished;
    private IAgentieClient client;

    public AgentieServerRpcProxy(String host,int port){
        this.host = host;
        this.port = port;
        queueResponses = new LinkedBlockingQueue<>();
    }

    @Override
    public List<Excursie> getAllExcursii(){
       Request request = new Request.Builder().type(RequestType.GET_EXCURSII).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType()==ResponseType.ERROR){
            return null;
        }
        List<ExcursieDTO> excursiiDTO = (List<ExcursieDTO>)response.getData();
        return DTOUtils.getExcursiiFromDTO(excursiiDTO);
    }

    @Override
    public List<Rezervare> getAllRezervari() {
        Request request = new Request.Builder().type(RequestType.GET_REZERVARI).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType()==ResponseType.ERROR){
            return null;
        }
        List<RezervareDTO> rezervariDTO = (List<RezervareDTO>)response.getData();
        return DTOUtils.getRezervariFromDTO(rezervariDTO);
    }

    @Override
    public void addRezervare(Rezervare e) {

    }


    @Override
    public List<Excursie> cautaExcursie(String obiectiv, Integer ora1, Integer ora2) {

        IntermediarDTO i = new IntermediarDTO(obiectiv,ora1,ora2);
        Request request = new Request.Builder().type(RequestType.GET_EXCURSII_ORE).data(i).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR){
            System.out.println("not good");
            return null;
        }
        List<ExcursieDTO> excursiiDTO = (List<ExcursieDTO>)response.getData();
        return DTOUtils.getExcursiiFromDTO(excursiiDTO);
    }

    @Override
    public void rezervaBilete(Integer id_excursie,Excursie new_excursie) {
        System.out.println("rezerva bilete");
        RezervaBileteDTO rezervaBileteDTO = new RezervaBileteDTO(id_excursie,new_excursie);
        Request request = new Request.Builder().type(RequestType.REZERVA_BILETE).data(rezervaBileteDTO).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR){
            System.out.println("not good");
            return;
        }
    }

    @Override
    public void login(Utilizator utilizator, IAgentieClient client) {
        initializeConnection();
        UtilizatorDTO utilizatorDTO = DTOUtils.getUtilizatorDTO(utilizator);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(utilizatorDTO).build();

        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.OK){
            this.client = client;
            return;
        }
        if(response.getType()==ResponseType.ERROR){
            return;
        }
    }

    @Override
    public void logout(Utilizator utilizator, IAgentieClient client) {
        UtilizatorDTO utilizatorDTO = DTOUtils.getUtilizatorDTO(utilizator);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(utilizatorDTO).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if(response.getType() == ResponseType.ERROR){
            return;
        }
    }

    private void sendRequest(Request request){
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("www "+e);
        }
    }

    private Response readResponse(){
        Response response = null;
        try{
            response = queueResponses.take();
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        return response;
    }
    private void initializeConnection(){
        try {
            connection=new Socket(host,port);
            outputStream=new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeConnection(){
        finished = true;
        try{
            inputStream.close();
            outputStream.close();
            connection.close();
            client=null;
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }
    private boolean isUpdate(Response response){
        return response.getType().equals(ResponseType.SHOW_UPDATED);
    }

    private void handleUpdate(Response response){
        if(response.getType() == ResponseType.SHOW_UPDATED){
            try{

                ExcursieDTO excursieDTO = (ExcursieDTO)response.getData();
                System.out.println("wwww "+excursieDTO);
                Excursie excursie = DTOUtils.getExcursieFromDTO(excursieDTO);
                client.rezervareEfectuata(excursie);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable{

        @Override
        public void run() {
            while(!finished){
                try{
                    System.out.println("Tries to read..");
                    Object response = inputStream.readObject();
                    System.out.println("Response received " + ((Response)response).getType());
                    if (isUpdate((Response) response)){
                        handleUpdate((Response) response);
                    } else {
                        try{
                            queueResponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException ex){
                    System.out.println("Client reader stopped"+ex);

                    finished = true;
                }

            }
        }
    }
}
