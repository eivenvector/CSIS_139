
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan
 */
public class CAActionPanel extends JPanel
{
    private JLabel prompt;
    private JRadioButton zero, one, two, three, four, five;
    public JFileChooser fileChooser = new JFileChooser();
    private boolean changesMade = true;
    
    public CAActionPanel()
    {
        prompt = new JLabel("Choose Action:\n");
        prompt.setFont(new Font ("Helvetica", Font.BOLD, 32));
        
        zero = new JRadioButton("Enter Transaction");
        zero.setFont(new Font ("Garamond", Font.PLAIN, 16));
        zero.setBackground(Color.red);
        
        one = new JRadioButton("List All Transactions");
        one.setFont(new Font ("Garamond", Font.PLAIN, 16));
        one.setBackground(Color.red);

        two = new JRadioButton("List All Checks");
        two.setFont(new Font ("Garamond", Font.PLAIN, 16));
        two.setBackground(Color.red);

        three = new JRadioButton("List All Deposits");
        three.setFont(new Font ("Garamond", Font.PLAIN, 16));
        three.setBackground(Color.red);
        
        four = new JRadioButton("Load An Account");
        four.setFont(new Font ("Garamond", Font.PLAIN, 16));
        four.setBackground(Color.red);
        
        five = new JRadioButton("Save Your Account");
        five.setFont(new Font ("Garamond", Font.PLAIN, 16));
        five.setBackground(Color.red);
        
        ButtonGroup group = new ButtonGroup();
        group.add(zero);
        group.add(one);
        group.add(two);
        group.add(three);
        group.add(four);
        group.add(five);
        
        CAActionListener listener = new CAActionListener();
        zero.addActionListener(listener);
        one.addActionListener(listener);
        two.addActionListener(listener);
        three.addActionListener(listener);
        four.addActionListener(listener);
        five.addActionListener(listener);
        
        add(prompt);
        add(zero);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        
        setBackground(Color.orange);
        setPreferredSize(new Dimension(400, 150));
        
    }
    
    public boolean getChangesMade() {
        return changesMade;
    }
    

    private class CAActionListener implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent event)
        {
            JTextArea textArea;
            Object source = event.getSource();
            
            Main.frame.setVisible(false);
            if (source == zero){
                int futureTransCode = Main.getTransCode();
                Transaction newTrans;
                if (futureTransCode == 1) {
                    newTrans = new Check(Main.userAccount.getTransCount(),
                        futureTransCode, Main.getCheckNum(), Main.getTransAmt());
                }
                else {
                newTrans = new Transaction(Main.userAccount.getTransCount(),
                        futureTransCode, Main.getTransAmt());
                }
                Main.userAccount.addTrans(newTrans);
                Main.displayBalanceMessage(newTrans, 
                        Main.userAccount.willBeChargedForLowBalance(),
                        Main.userAccount.NotifyAboutUltraLowBalance(), 
                        Main.userAccount.willBeChargedForNegativeBalance());
                if (newTrans.getTransId() == 1) {
                    Transaction serviceTrans = new Transaction(Main.userAccount.getTransCount(),
                            3, 0.15);
                    Main.userAccount.addTrans(serviceTrans);
                }
                else if (newTrans.getTransId() == 2) {
                    Transaction serviceTrans = new Transaction(Main.userAccount.getTransCount(),
                            3, 0.10);
                    Main.userAccount.addTrans(serviceTrans);
                }
                changesMade = true;
            }
                
            else if (source == one){
                String message = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nTransaction List for\nName: " + Main.userAccount.name 
                        +"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" + "ID     Type              Amount\n";
                
                int transCount = Main.userAccount.getTransCount();
                for (int i = 0; i < transCount; i++) {
                    Transaction trans = Main.userAccount.getTrans(i);
                    message += trans.toString();
                }
                textArea = new JTextArea(message);
                textArea.setFont(new Font("Monospaced",Font.PLAIN,15));
                JOptionPane.showMessageDialog(null, textArea);
            }
                
            else if (source == two){
                String message = "Listing all Checks for " 
                        + Main.userAccount.name 
                        + "\n\n" + 
                        "ID     Chk#              Amount\n"
                        + "-------------------------------\n";
                
                int transCount = Main.userAccount.getTransCount();                
                for (int i = 0; i < transCount; i++) {
                    Transaction trans = Main.userAccount.getTrans(i);
                    if  (trans.getTransId() == 1) {
                    message += trans.toShortString();
                    }
                }
                textArea = new JTextArea(message);
                textArea.setFont(new Font("Monospaced",Font.PLAIN,15));
                JOptionPane.showMessageDialog(null, textArea);
            }
                
            else if (source == three){
                String message = "Listing all Deposits for " + Main.userAccount.name
                        +  "\n\n" + "ID     Type              Amount\n"
                        + "-------------------------------\n";
                
                int transCount = Main.userAccount.getTransCount();                
                for (int i = 0; i < transCount; i++) {
                    Transaction trans = Main.userAccount.getTrans(i);
                    if  (trans.getTransId() == 2) {
                    message += trans.toShortString();
                    }
                }
                textArea = new JTextArea(message);
                textArea.setFont(new Font("Monospaced",Font.PLAIN,15));
                JOptionPane.showMessageDialog(null, textArea);
                
            }
            else if (source == four) {
                int fileChooserReturn = fileChooser.showOpenDialog(null);
                if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
                 File f = fileChooser.getSelectedFile();
                    try {
                        FileInputStream fIn = new FileInputStream(f);
                        ObjectInputStream oIn = new ObjectInputStream(fIn);
                        Main.userAccount = (CheckingAccount)oIn.readObject();
                        oIn.close();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        changesMade = false;
                    }
                    
                }
                
            }
            
            else if (source == five) {
                int fileChooserReturn = fileChooser.showSaveDialog(null);
                if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    try {
                        FileOutputStream fOut = new FileOutputStream(f);
                        ObjectOutputStream oOut = new ObjectOutputStream(fOut);
                        oOut.writeObject(Main.userAccount);
                        oOut.close();
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    } finally {
                        changesMade = false;
                    }
                    
                }
                else {
                    JOptionPane.showMessageDialog(null, "Did not save!");
                }
            }
            
            Main.frame.setVisible(true);
            
        }
    }
}
