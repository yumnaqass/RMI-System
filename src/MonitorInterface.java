import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MonitorInterface extends Remote {

    public  byte[] TakeScreenshot() throws RemoteException;
    public byte[] TakePhoto() throws RemoteException;
}
