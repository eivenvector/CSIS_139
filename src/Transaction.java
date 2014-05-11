
import java.io.Serializable;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Ivan
 */
public class Transaction implements Serializable
{
 
    private static final long serialVersionUID = 2L;
    private final int transNumber;
    private final int transId;
    private final double transAmt;
   
    public Transaction(int number, int id, double amount)
    {
        transNumber = number;
        transId = id;
        transAmt = amount;
    }
   
    public int getTransNumber()
    {
        return transNumber;
    }
   
    public int getTransId()
    {
        return transId;
    }
    
    public String transNumberToString(int transNumber)
    {
        if (transNumber == 1) {
            return "Check";
        }
        else if (transNumber == 2) {
            return "Deposit";
        }
        return "Service";
        
    }
   
    public double getTransAmount()
    {
        return transAmt;
    }
    @Override
    public String toString()
    {

        String transAmtString = Main.currencyFormatter.format(transAmt);
        String formatString =  String.format("%-7d%-7s%17s" + "\n"  , transNumber, 
                transNumberToString(transId), 
                transAmtString);
                
        return formatString;
    }
    
    public String toShortString()
    {

        String formatString = String.format("%-7d%-7s%18s", getTransNumber(), 
                    transNumberToString(getTransId()),
                    Main.currencyFormatter.format(getTransAmount()) + "\n");
        
        return formatString;
    }
    
}
 