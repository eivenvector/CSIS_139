
import java.text.DecimalFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Ivan
 */
public class Transaction
{
  
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
    
    private String transNumberToString(int transNumber)
    {
        if (transNumber == 1) {
            return "check";
        }
        else if (transNumber == 2) {
            return "deposit";
        }
        return "svc.chrg.";
        
    }
   
    public double getTransAmount()
    {
        return transAmt;
    }
    @Override
    public String toString()
    {
        
        String transAmtString = Main.currencyFormatter.format(transAmt);
        String formatString =  String.format("%-9d%-10s%14s" + "\n"  , transNumber, 
                transNumberToString(transId), 
                transAmtString);
                
        return formatString;
    }
    
    public String toShortString()
    {
        String formatString = String.format("%-6d%14s", transNumber,
                Main.currencyFormatter.format(transAmt) + "\n");
        
        return formatString;
    }
    
}
 