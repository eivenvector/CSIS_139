
/**
 *
 * @author Ivan
 */

import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Main
{
    //Constants 
    public static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    public static final double CHECK_FEE = .15;
    public static final double DEPOSIT_FEE = .10;
    
    //Transactions
      private static ArrayList<Transaction> transList;
      private static int transCount;

    //GUI
    public static JFrame frame;
    
    //Checking account
    public static CheckingAccount userAccount;
  
     

    public static void main (String[] args)
   {
       transList = new ArrayList<>();
       //Method Variables
       String userInitialBalanceStr, name;
       double userInitialBalance;

       //Initial Balance setting - This only happens once; at the beginnning of 
       //the program
       name = JOptionPane.showInputDialog ("Enter the Account Name: ");
       userInitialBalanceStr = JOptionPane.showInputDialog ("Enter your Initial Balance: ");
       userInitialBalance= Double.parseDouble(userInitialBalanceStr);
       userAccount = new CheckingAccount(userInitialBalance, name);
       
       //GUI
       frame = new JFrame("Checking Account Actions");
       frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
       
       
       CAActionPanel panel = new CAActionPanel();
       frame.getContentPane().add(panel);
       
       frame.pack();

       frame.setVisible(true);
       
       //Get the first transaction code for the first time, then it will enter the loop
   }
    public static void addTrans( Transaction newTrans)
    {
        transList.add(newTrans);
        transCount += 1;
    }
        
    public static int getTransCount()
    {
        return transCount;
    }
    
    public static Transaction getTrans(int i)
    {
        return transList.get(i);
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
       
       transactionString = (userAccount.name + "'s Account\n" + "Transaction: "
               + transType + "#" + (Check)trans.getCheckNumber() + " in the amount of " + 
               currencyFormatter.format(transAmt)  + "\n");
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
 
