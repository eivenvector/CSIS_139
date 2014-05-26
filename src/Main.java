
/**
 *
 * @author Ivan
 */

import java.io.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Vector;
import java.io.Serializable.*;

public class Main
{
    //Constants
    public static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    public static final double CHECK_FEE = .15;
    public static final double DEPOSIT_FEE = .10;
    
    public static JFrame frame;
    public static JFileChooser fileChooser = new JFileChooser();
    
    //GUI Account
    public static Vector<CheckingAccount> accountsVector;
    
    public static CheckingAccount userAccount;
    
    private static boolean changesMade = true;
    
    public static void main (String[] args)
    {
        accountsVector = new Vector<CheckingAccount>();
        
        frame = new CAOptionsFrame("Checking Account Actions");

        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        frame.pack();
        setChangesMade(false);
        frame.setVisible(true);
    }
    
//    public void addNewAccount()  {
//        int size = accountsVector.size();
//        System.out.println(size);
//        if (size > 0) {
//            accountsVector.removeElementAt(size - 1);
//            accountsVector.addElement(Main.userAccount);
//        }
//        
//        String userInitialBalanceStr, name;
//        double userInitialBalance;
//        
//        name = JOptionPane.showInputDialog ("Enter the Account Name: ");
//        userInitialBalanceStr = JOptionPane.showInputDialog ("Enter your Initial Balance: ");
//        userInitialBalance= Double.parseDouble(userInitialBalanceStr);
//        userAccount = new CheckingAccount(userInitialBalance, name);
//        
//        accountsVector.addElement(Main.userAccount);
//    }
    
    public static boolean getChangesMade() {
        return changesMade;
    }
    
    public static void setChangesMade(boolean b) {
        changesMade = b;
    }
    
    public static void chooseSaveFile() {
        int fileChooserReturn = fileChooser.showSaveDialog(null);
        if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            try {
                FileOutputStream fOut = new FileOutputStream(f);
                ObjectOutputStream oOut = new ObjectOutputStream(fOut);
                oOut.writeObject(Main.accountsVector);
                oOut.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            } finally {
                changesMade = false;
            }
            
        }
    }
    public static void chooseLoadFile() {
        int fileChooserReturn = fileChooser.showOpenDialog(null);
        if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            try {
                FileInputStream fIn = new FileInputStream(f);
                ObjectInputStream oIn = new ObjectInputStream(fIn);
                Main.accountsVector = (Vector<CheckingAccount>)oIn.readObject();
                oIn.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                changesMade = false;
                Main.userAccount = Main.accountsVector.lastElement();
            }
            
        }
        
    }
    
    public static int getTransCode()
    {
        String userTransCodeStr;
        int userTransCode;

        userTransCodeStr = JOptionPane.showInputDialog ("Enter Transaction Code: ");
        userTransCode = Integer.parseInt(userTransCodeStr);
        
        return userTransCode;
    }
    
    public static double getTransAmt()
    {
        String userBalanceModificationStr;
        double userBalanceModification;
        
        userBalanceModificationStr = JOptionPane.showInputDialog ("Enter Transaction Amount: ");
        userBalanceModification = Double.parseDouble(userBalanceModificationStr);
        
        return userBalanceModification;
    }
    
    public static void processCheck(double transAmt)
    {
        userAccount.setBalance(transAmt, 1);
    }
    
    public static void processDeposit(double transAmt)
    {
        userAccount.setBalance(transAmt, 2);
        
    }
    
    public static String getCheckNum()
    {
        return JOptionPane.showInputDialog("Enter the check number");
    }
    
    public static void displayBalanceMessage(Transaction trans, Boolean lowBalanceFee,
            Boolean ultraLowBalance, Boolean negativeBalanceFee)
    {
        String transType, balanceMessage, transactionString, currentBalanceString,
                serviceChargeString, lowServiceChargeString, ultraServiceChargeString,
                lowBalanceString, totalServiceChargeString;
        
        double serviceCharge;
        
        if(trans.getTransId() == 1){
            transType = "Check";
            serviceCharge = CHECK_FEE;
        }
        else{
            transType = "Deposit";
            serviceCharge = DEPOSIT_FEE;
        }
        
        double transAmt = trans.getTransAmount();
        
        if (transType.equals("Check")) {
            Check check = (Check)trans;
            transactionString = (userAccount.name + "'s Account\n" + "Transaction: "
                    + transType + " # " + check.getCheckNumber() + " in the amount of " +
                    currencyFormatter.format(transAmt)  + "\n");
        }
        else {
            transactionString = (userAccount.name + "'s Account\n" + "Transaction: "
                    + transType + " in the amount of " +
                    currencyFormatter.format(transAmt)  + "\n");
        }
        currentBalanceString = ("Current Balance: " +
                currencyFormatter.format(userAccount.getBalance()) + "\n");
        serviceChargeString = ("Service Charge: " + transType + " --- charge " +
                currencyFormatter.format(serviceCharge) + "\n");
        lowServiceChargeString = ("Service Charge: Below $500 --- charge $5.00 \n");
        ultraServiceChargeString = ("Service Charge: Below $0 --- charge $10.00 \n");
        lowBalanceString = ("Warning: Balance below $50.\n");
        totalServiceChargeString = ("Total Service Charge: " +
                currencyFormatter.format(userAccount.getServiceCharge()) + "\n");
        
        //Chooses what warnings and fees will be displayed.
        balanceMessage = transactionString + currentBalanceString +
                serviceChargeString;
        if (lowBalanceFee) {
            balanceMessage += lowServiceChargeString;
        }
        if (ultraLowBalance) {
            balanceMessage += ultraServiceChargeString;
        }
        if (negativeBalanceFee) {
            balanceMessage += lowBalanceString;
        }
        balanceMessage += totalServiceChargeString;
        
        JOptionPane.showMessageDialog(null, balanceMessage);
        
        
    }
    
    public static void displayFinalMessage()
    {
        String finalMessage;
        userAccount.setBalance(userAccount.getServiceCharge(), 0);
        //Formatting the Final Message
        finalMessage = ("Transaction: End\n"
                + "Current Balance: " + currencyFormatter.format(userAccount.getBalance() + userAccount.getServiceCharge()) + "\n"
                + "Total Service Charge: " + currencyFormatter.format(userAccount.getServiceCharge()) + "\n"
                + "Final Balance: " + currencyFormatter.format(userAccount.getBalance()));
        
        JOptionPane.showMessageDialog(null, finalMessage);
        
    }
}
