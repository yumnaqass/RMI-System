import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MiddleServerInterface extends Remote {
  //  public void reportStatus(String employeeID , boolean isActive) throws RemoteException;
   // public void reportIp(String employeeID , String IpAddress) throws RemoteException;
    public void registerEmployee(String employeeIP) throws RemoteException;
    public void deregisterEmployee(String employeeIP) throws RemoteException;
    public String[] getAllActiveEmployees() throws RemoteException;

}
