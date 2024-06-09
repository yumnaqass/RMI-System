import org.opencv.core.Core;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.rmi.RemoteException;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       // System.loadLibrary("jniopenblas_nolapack");
    }
    public static void main(String[] args) throws RemoteException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        MonitorImp monitorImp = new MonitorImp();
        byte[] im = monitorImp.TakePhoto();
        Mat mat = Imgcodecs.imdecode(new MatOfByte(im) , Imgcodecs.IMREAD_COLOR);
        HighGui.imshow("Screenshot" , mat);
        HighGui.waitKey(1);
        HighGui.destroyAllWindows();

        return;
    }
}
