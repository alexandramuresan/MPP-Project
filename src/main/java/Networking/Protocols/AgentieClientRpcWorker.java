package Networking.Protocols;

import Domain.Excursie;
import Domain.Rezervare;
import Networking.DTO.*;
import Domain.Utilizator;
import Services.AppService.IAgentieClient;
import Services.AppService.IAgentieServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class AgentieClientRpcWorker implements Runnable,IAgentieClient {

    private IAgentieServer server;

    private Socket connection;


    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    private volatile boolean connected;

    public AgentieClientRpcWorker(IAgentieServer server, Socket connection){
        this.server = server;
        this.connection = connection;

        try{
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try{
                //get a request
                Object request = inputStream.readObject();
                System.out.println("Got a request");

                //process it
                Response response = handleRequest((Request) request);
                System.out.println("Processed request");

                //send response if able
                if (response != null){
                    sendResponse(response);
                    System.out.println("Sent response");
                }


            } catch (IOException | ClassNotFoundException ex){
                System.out.println("Disconnected while trying to read");
                break;
            }
        }

        try{
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private Response handleRequest(Request request){

        Response response = null;

        if(request.getType() == RequestType.LOGIN){
            System.out.println("Log in request");
            UtilizatorDTO utilizatorDTO = (UtilizatorDTO)request.getData();
            Utilizator utilizator = DTOUtils.getUtilizatorFromDTO(utilizatorDTO);
            try{
                server.login(utilizator,this);
                System.out.println("Succes!");
                return new Response.Builder().type(ResponseType.OK).build();
            }catch(Exception ex){
                return new Response.Builder().type(ResponseType.ERROR).build();
            }
        }

        if(request.getType()== RequestType.LOGOUT){
            System.out.println("Log out request");
            UtilizatorDTO utilizatorDTO = (UtilizatorDTO)request.getData();
            Utilizator utilizator = DTOUtils.getUtilizatorFromDTO(utilizatorDTO);
            try{
                server.logout(utilizator,this);
                System.out.println("Succes");
                return new Response.Builder().type(ResponseType.OK).build();
            }catch(Exception ex){
                return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
            }
        }

        if(request.getType() == RequestType.GET_EXCURSII){
            System.out.println("get all request");

            try{
                List<Excursie> excursii = server.getAllExcursii();
                List<ExcursieDTO> excursieDTO = DTOUtils.getExcursiiDTO(excursii);
                return new Response.Builder().type(ResponseType.GET_EXCURSII).data(excursieDTO).build();
            }catch(Exception ex){
                return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
            }
        }
        if(request.getType() == RequestType.GET_REZERVARI){
            System.out.println("get all request");

            try{
                List<Rezervare> rezervari = server.getAllRezervari();
                List<RezervareDTO> rezervareDTO = DTOUtils.getRezervariDTO(rezervari);
                return new Response.Builder().type(ResponseType.GET_REZERVARI).data(rezervareDTO).build();
            }catch(Exception ex){
                return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
            }
        }

        if(request.getType() == RequestType.GET_EXCURSII_ORE){
            System.out.println("get all by hours");
            try{
                IntermediarDTO i = (IntermediarDTO)request.getData();
                List<Excursie> excursii = server.cautaExcursie(i.getObiectiv(),i.getOra1(),i.getOra2());
                List<ExcursieDTO> excursiiDTO = DTOUtils.getExcursiiDTO(excursii);
                return new Response.Builder().type(ResponseType.GET_EXCURSII_ORE).data(excursiiDTO).build();
            }catch(Exception ex){

                return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
            }
        }

        if(request.getType() == RequestType.REZERVA_BILETE){
            System.out.println("rezerva bilete");
            try{

                RezervaBileteDTO rezervaBileteDTO = (RezervaBileteDTO)request.getData();
                server.rezervaBilete(rezervaBileteDTO.getId(),rezervaBileteDTO.getNew_excursie());
                return new Response.Builder().type(ResponseType.OK).build();
            }catch(Exception ex){
                return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
            }
        }

        return response;

    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("send response");
        outputStream.writeObject(response);
        System.out.println("flush");
        outputStream.flush();
    }

    @Override
    public void rezervareEfectuata(Excursie e) {
        ExcursieDTO excursieDTO = DTOUtils.getExcursieDTO(e);
        Response response = new Response.Builder().type(ResponseType.SHOW_UPDATED).data(excursieDTO).build();
        try{
            System.out.println("Updated "+excursieDTO);
            sendResponse(response);
        }catch(IOException ex){
            System.out.println("eeee "+ex);
            return;
        }
    }
}
