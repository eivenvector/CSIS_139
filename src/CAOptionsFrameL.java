
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
           String  message = "Data has not been saved.\n"+
               "Save before exiting?";
           confirm = JOptionPane.showConfirmDialog (null, message);
           if (confirm == JOptionPane.YES_OPTION)
              Main.chooseSaveFile(); 
        }
       System.exit(0);
    }
   }
}
   
