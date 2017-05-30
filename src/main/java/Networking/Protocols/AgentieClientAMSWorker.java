package Networking.Protocols;

import Domain.Excursie;
import Domain.Rezervare;
import Domain.Utilizator;
import Networking.DTO.*;
import Services.AppService.IAgentieServerAMS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class AgentieClientAMSWorker implements Runnable {

    private IAgentieServerAMS server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public AgentieClientAMSWorker(IAgentieServerAMS server,Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                System.out.println("Received request");
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).getType();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException|InvocationTargetException |IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.getType());
        UtilizatorDTO udto=(UtilizatorDTO)request.getData();
        Utilizator user= DTOUtils.getUtilizatorFromDTO(udto);
        try {
            server.login(user);
            return okResponse;
        } catch (Exception e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_EXCURSII(Request request){
        try{
            List<Excursie> excursii = server.getAllExcursii();
            List<ExcursieDTO> excursieDTO = DTOUtils.getExcursiiDTO(excursii);
            return new Response.Builder().type(ResponseType.GET_EXCURSII).data(excursieDTO).build();
        }catch(Exception ex){
            return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
        }
    }

    private Response handleGET_EXCURSII_ORE(Request request){
        try{
            IntermediarDTO i = (IntermediarDTO)request.getData();
            List<Excursie> excursii = server.cautaExcursie(i.getObiectiv(),i.getOra1(),i.getOra2());
            List<ExcursieDTO> excursiiDTO = DTOUtils.getExcursiiDTO(excursii);
            return new Response.Builder().type(ResponseType.GET_EXCURSII_ORE).data(excursiiDTO).build();
        }catch(Exception ex){

            return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
        }
    }

    private Response handleGET_REZERVARI(Request request){
        try{
            List<Rezervare> rezervari = server.getAllRezervari();
            List<RezervareDTO> rezervareDTO = DTOUtils.getRezervariDTO(rezervari);
            return new Response.Builder().type(ResponseType.GET_REZERVARI).data(rezervareDTO).build();
        }catch(Exception ex){
            return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
        }
    }

    private Response handleSHOW_UPDATED(Request request){
        try{

            RezervaBileteDTO rezervaBileteDTO = (RezervaBileteDTO)request.getData();
            server.rezervaBilete(rezervaBileteDTO.getId(),rezervaBileteDTO.getNew_excursie());
            return new Response.Builder().type(ResponseType.SHOW_UPDATED).build();
        }catch(Exception ex){
            return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
        }
    }

}
