import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Employee {

    public static void main(String[] args) {
        String MiddleServerIp = "127.0.0.1";
        String MiddleServerPort = "8888";
        Scanner scanner = new Scanner(System.in);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        DataInputStream in ;
        DataOutputStream out ;
        String managerMessage;
        String employeeMessage = "";
        try {
            String MyIP = InetAddress.getLocalHost().getHostAddress();
            int x;
           // System.out.println("employee "+ MyIP +" is working , if you want to stop enter 0");
            MiddleServerInterface middle = (MiddleServerInterface) Naming.lookup("rmi://"+MiddleServerIp+":"+MiddleServerPort+"/MiddleService");
            MonitorImp monitor = new MonitorImp();
            Registry registry = LocateRegistry.createRegistry(1900);
            executor.scheduleAtFixedRate(() -> Employee.getMyIp(middle,registry,monitor) , 0 , 5 , TimeUnit.SECONDS);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    middle.deregisterEmployee(MyIP);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }));

            ServerSocket serverSocket = new ServerSocket(1111);
            System.out.println("Socket is working");
           while (true){
               System.out.println("employee "+ MyIP +" is working , if you want to stop enter 0 , or 1 to continue :");
               x = scanner.nextInt();
               if (x == 0){
                   System.out.println("stopping ...");
                middle.deregisterEmployee(MyIP);
                executor.shutdownNow();
                break;
            }
                 Socket clientSocket = serverSocket.accept();
                  in = new DataInputStream(clientSocket.getInputStream());
                 out = new DataOutputStream(clientSocket.getOutputStream());
                 System.out.println("waiting for manager message");


               while (true){
                   managerMessage = in.readUTF();
                   if (managerMessage.equals("end")){
                       System.out.println("the manager end the chat");
                       break;
                   }
                   System.out.println("manager : " +managerMessage);
                   if((employeeMessage = scanner.nextLine()) !=null){
                       out.writeUTF(employeeMessage);
                   }
               }
//               in.close();
//               out.close();
//               clientSocket.close();
           }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return;
    }

    public static void getMyIp(MiddleServerInterface mid , Registry reg , MonitorImp mon){
        try {
          String MyIP = InetAddress.getLocalHost().getHostAddress();
            mid.registerEmployee(MyIP);
            reg.rebind("employee"+MyIP, mon);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
