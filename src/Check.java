
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
public class Check extends Transaction implements Serializable
{ 
  
    private static final long serialVersionUID = 1L;
    
    private String checkNumber;
  
    
    public Check(int number, int id, String checkNum, double amount)
    {
        super(number, id, amount);
        
        checkNumber = checkNum;
    }
    
    public String getCheckNumber()
    {
        return checkNumber;
    }
    
    public String toShortString()
    {
        String formatString;

        formatString = String.format("%-7d%-7s%18s", getTransNumber(),
                 getCheckNumber(), Main.currencyFormatter.format(getTransAmount()) + "\n");

        return formatString;
    }
        
    
    
}
