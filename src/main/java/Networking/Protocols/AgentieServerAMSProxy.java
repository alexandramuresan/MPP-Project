package Networking.Protocols;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
import Networking.DTO.*;
import Services.AppService.IAgentieClient;
import Services.AppService.IAgentieServerAMS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class AgentieServerAMSProxy implements IAgentieServerAMS {


    private String host;
    private int port;

    private IAgentieClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public AgentieServerAMSProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }
    @Override
    public List<Excursie> getAllExcursii() {
        Request req=new Request.Builder().type(RequestType.GET_EXCURSII).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getData().toString();
            return null;
        }
        List<ExcursieDTO> excursiiDTO=(List<ExcursieDTO>)response.getData();
        List<Excursie> excursii=DTOUtils.getExcursiiFromDTO(excursiiDTO);
        return excursii;
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
    public void rezervaBilete(Integer id_excursie, Excursie new_excursie) {
        System.out.println("rezerva bilete");
        RezervaBileteDTO rezervaBileteDTO = new RezervaBileteDTO(id_excursie,new_excursie);
        Request request = new Request.Builder().type(RequestType.SHOW_UPDATED).data(rezervaBileteDTO).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR){
            System.out.println("not good");
            return;
        }
    }

    @Override
    public void login(Utilizator utilizator) {
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
    public void logout(Utilizator utilizator) {
        UtilizatorDTO utilizatorDTO = DTOUtils.getUtilizatorDTO(utilizator);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(utilizatorDTO).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if(response.getType() == ResponseType.ERROR){
            return;
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Response readResponse()  {
        Response response=null;
        try{
            /*synchronized (responses){
                responses.wait();
            }
            response = responses.remove(0);    */
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection()  {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);


                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                } catch (IOException|ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.getType()==ResponseType.GET_EXCURSII_ORE || response.getType()==ResponseType.GET_REZERVARI || response.getType()==ResponseType.SHOW_UPDATED;
    }



}
