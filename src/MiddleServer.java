import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.InetAddress;
import java.rmi.server.UnicastRemoteObject;

public class MiddleServer {

    public static void main(String[] args) {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ipAddress);

            MiddleServerImp service = new MiddleServerImp();
            Registry registry = LocateRegistry.createRegistry(8888);
            registry.rebind("MiddleService" , service);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
