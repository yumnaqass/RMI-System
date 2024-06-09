import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MiddleServerImp extends UnicastRemoteObject implements MiddleServerInterface {

    private Map<String,Boolean> activeEmployees = new HashMap<>();
  //  private Map<String,String> employeeIPs = new HashMap<>();
    private HashSet<String> employeeIPs = new HashSet<>();

    public MiddleServerImp() throws RemoteException {
        super();
    }

//    @Override
//    public void reportIp(String employeeID, String IpAddress) throws RemoteException {
//        employeeIPs.put(employeeID , IpAddress);
//    }

  //  @Override
//    public void reportStatus(String employeeID, boolean isActive) throws RemoteException {
//        activeEmployees.put(employeeID , isActive );
//    }


    @Override
    public void registerEmployee(String employeeIP) throws RemoteException {
        employeeIPs.add(employeeIP);
    }

    @Override
    public void deregisterEmployee(String employeeIP) throws RemoteException {
        employeeIPs.remove(employeeIP);
    }

    @Override
    public String[] getAllActiveEmployees() throws RemoteException {
        return employeeIPs.toArray(new String[0]);
    }
}
