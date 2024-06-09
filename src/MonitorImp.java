//import org.bytedeco.javacv.Frame;
//import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Core;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.github.sarxos.webcam.Webcam;


public class MonitorImp extends UnicastRemoteObject implements MonitorInterface
{
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public MonitorImp() throws RemoteException {
        super();
    }

    @Override
    public byte[] TakeScreenshot() throws RemoteException {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Rectangle screen = new Rectangle(toolkit.getScreenSize());
            Robot robot = new Robot();
            BufferedImage img = robot.createScreenCapture(screen);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img , "png" , baos);
            byte[] image = baos.toByteArray();
            baos.close();
            return image;
        }
      catch (AWTException exception){
          System.out.println(exception.getMessage());
      }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return new byte[0];
    }

    @Override
    public byte[] TakePhoto() throws RemoteException {
      try {
          Webcam webcam = Webcam.getDefault();
          webcam.open();
          BufferedImage image = webcam.getImage();
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ImageIO.write(image, "png", baos);
          byte[] data = baos.toByteArray();
          baos.close();
          webcam.close();
          return data;
      }
      catch (IOException e)
      {
          System.out.println(e.getMessage());
      }
        return new byte[0];
    }
}
