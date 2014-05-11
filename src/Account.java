
import java.io.Serializable;


/**
 *
 * @author Ivan
 */
public class Account implements Serializable {
    String name;
    double balance;
    
    
    public double getBalance()
    {
        return balance;
    }
    
    public String getName()
    {
        return name;
    }        
    
}
