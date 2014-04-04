/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan
 */

import javax.swing.JOptionPane;
import java.text.NumberFormat;

public class Main
{
    //Constants 
    public static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    public static final double CHECK_FEE = .15;
    public static final double DEPOSIT_FEE = .10;
    
    //Checking account
    public static CheckingAccount userAccount;
  
     

    public static void main (String[] args)
   {
       //Method Variables
       String userInitialBalanceStr;
       double userInitialBalance, userTransAmt;
       int userTransCode;

       //Initial Balance setting - This only happens once; at the beginnning of 
       //the program
       userInitialBalanceStr = JOptionPane.showInputDialog ("Enter your Initial Balance: ");
       userInitialBalance= Double.parseDouble(userInitialBalanceStr);
       userAccount = new CheckingAccount(userInitialBalance);
       
       //Get the first transaction code for the first time, then it will enter the loop
       userTransCode = getTransCode();
       
       //Loop that allows multiple transactions until user intputs 0.
       do {

           userTransAmt = getTransAmt();
           userAccount.setBalance(userTransAmt, userTransCode);
           
           displayBalanceMessage(userTransAmt, userTransCode, userAccount.willBeChargedForLowBalance(),
                   userAccount.NotifyAboutUltraLowBalance(), userAccount.willBeChargedForNegativeBalance());
           
           userTransCode = getTransCode();
           
       } while (userTransCode != 0);
       

       displayFinalMessage();
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
 
   public static void displayBalanceMessage(double transAmt, int transCode, Boolean lowBalanceFee,
           Boolean ultraLowBalance, Boolean negativeBalanceFee)
   {
       String transType, balanceMessage, transactionString, currentBalanceString, 
       serviceChargeString, lowServiceChargeString, ultraServiceChargeString, 
               lowBalanceString, totalServiceChargeString;

       double serviceCharge;

       if(transCode == 1){
           transType = "Check";
           serviceCharge = CHECK_FEE;
       }
       else{
           transType = "Deposit";
           serviceCharge = DEPOSIT_FEE;
       }
       
       transactionString = ("Transaction: " + transType + " in the amount of " + 
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
       if(!lowBalanceFee && !ultraLowBalance && !negativeBalanceFee){
           balanceMessage = (transactionString + currentBalanceString + 
               serviceChargeString + totalServiceChargeString);
       }
       else if(!ultraLowBalance && !negativeBalanceFee){
           balanceMessage = (transactionString + currentBalanceString + 
               serviceChargeString + lowServiceChargeString  + totalServiceChargeString);
       }
       else if(ultraLowBalance && !negativeBalanceFee){
           balanceMessage = (transactionString + currentBalanceString + 
               serviceChargeString + lowBalanceString + totalServiceChargeString);
       }
       else if(ultraLowBalance && negativeBalanceFee){
           balanceMessage = (transactionString + currentBalanceString + 
               serviceChargeString + lowBalanceString + ultraServiceChargeString
                   + totalServiceChargeString);
       }
       else{
    	   balanceMessage = (transactionString + currentBalanceString + 
                   serviceChargeString  + ultraServiceChargeString
                       + totalServiceChargeString);
       }

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
 
