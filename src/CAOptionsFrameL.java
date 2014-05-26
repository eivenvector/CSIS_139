
import javax.swing.*;
import java.awt.event.*;


public class CAOptionsFrameL extends JFrame {
    
    public CAOptionsFrameL(String title) {
        super(title);
        FrameListener listener = new FrameListener();
        addWindowListener(listener);
    }
    
   private class FrameListener extends WindowAdapter
   {

    @Override
    public void windowClosing(WindowEvent e) {
        int confirm;
        if (Main.getChangesMade())
        {
           String  message = "The data in the application is not saved.\n"+
               "Would you like to save it before exiting the application?";
           confirm = JOptionPane.showConfirmDialog (null, message);
           if (confirm == JOptionPane.YES_OPTION)
              Main.chooseSaveFile(); 
        }
       System.exit(0);
    }
   }
}
   
