
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
    private JRadioButton zero, one, two, three;
    
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
        
        ButtonGroup group = new ButtonGroup();
        group.add(zero);
        group.add(one);
        group.add(two);
        group.add(three);
        
        CAActionListener listener = new CAActionListener();
        zero.addActionListener(listener);
        one.addActionListener(listener);
        two.addActionListener(listener);
        three.addActionListener(listener);
        
        add(prompt);
        add(zero);
        add(one);
        add(two);
        add(three);
        
        setBackground(Color.orange);
        setPreferredSize(new Dimension(400, 110));
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
            }
                
            else if (source == one){
                String message = "Transaction List for\nName: " + Main.userAccount.name 
                        +": \n\n" + "ID     Type              Amount\n";
                
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
                String message = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nListing all Checks for " 
                        + Main.userAccount.name 
                        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" + 
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
                        + "-------------------------------";
                
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
            
            Main.frame.setVisible(true);
            
        }
    }
}
