import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.*;
import java.net.Socket;
import java.rmi.Naming;
import java.util.Scanner;

public class Manager {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String MiddleServerIp = "127.0.0.1";
        String MiddleServerPort = "8888";
        String[] EmployeeIPs = new String[0];
        Scanner scanner = new Scanner(System.in);
        String IP;
        int input;
        System.out.println("manager side");
        try {
            MiddleServerInterface middle = (MiddleServerInterface) Naming.lookup("rmi://" + MiddleServerIp + ":" + MiddleServerPort + "/MiddleService");
            while (true) {
                System.out.println("if you want to exit enter 0 ,to continue enter 1 , or enter 2 to get employees list:");
                input = scanner.nextInt();
                if (input == 0){
                    break;
                } else if (input == 2) {
                    EmployeeIPs = middle.getAllActiveEmployees();
                    displayEmployeeList(EmployeeIPs);
                } else if (input == 1) {
                    System.out.println("please enter the employee IP that you want to access his device:");
                    int index = scanner.nextInt();
                    IP = EmployeeIPs[index-1];
                    MonitorInterface monitor = (MonitorInterface) Naming.lookup("rmi://" + IP + ":1900/employee" + IP);
                    System.out.println("if you want to capture a screenshot enter 1");
                    System.out.println("if you want to capture a photo enter 2");
                     System.out.println("if you want to start texting him enter 3");
                    System.out.println("or enter any other number to exit");
                    input = scanner.nextInt();
                    if (input == 1) {
                        byteArrayToImage(monitor.TakeScreenshot());
                    } else if (input == 2) {
                        byteArrayToImage(monitor.TakePhoto());
                    } else if (input == 3) {
                        System.out.println(IP);
                        Socket socket = new Socket(IP , 1111);
                        System.out.println("socket is working");
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        PrintWriter fromme = new PrintWriter(socket.getOutputStream() , true);
                        String message, employeeMessage;

                        while (true){
                            message = scanner.nextLine();
                            if(message.equals("end")){
                                out.writeUTF(message);
                                System.out.println("end of chat");
                                break;
                            }
                            out.writeUTF(message);
                            if ((employeeMessage = in.readUTF()) != null){
                                System.out.println("employee : " +employeeMessage);
                            }
                        }
                        in.close();
                        out.close();
                        socket.close();
                    } else {
                        break;
                    }
                }else {
                    System.out.println("please enter 0 or 1 only");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("manager exited");
        scanner.close();
    }
        public static void byteArrayToImage(byte[] data){
            Mat mat = Imgcodecs.imdecode(new MatOfByte(data) , Imgcodecs.IMREAD_COLOR);
            HighGui.imshow("Screenshot" , mat);
            HighGui.waitKey(0);
            HighGui.destroyAllWindows();
        }
        public static void displayEmployeeList(String[] employees){
            System.out.println("the active Employees are:");
            for (int i = 0; i < employees.length; i++) {
                System.out.println( (i+1) + " : " +employees[i]);
            }
        }
    }

