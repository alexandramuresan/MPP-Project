package Services.AppService;

import Domain.Excursie;
import Utils.Observable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public interface IAgentieClient extends Remote{

    void rezervareEfectuata(Excursie e) throws RemoteException;
}
