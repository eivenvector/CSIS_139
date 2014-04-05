/*
 *
 *
a * @author Ivan
 */


public class CheckingAccount
{
      public static final double LOW_BALANCE_FEE = 5.0;
      public static final double NEGATIVE_BALANCE_FEE = 10.0;
      public static final double CHECK_FEE = .15;
      public static final double DEPOSIT_FEE = .10;

      private double balance;
      private double totalServiceCharge;
      private static Boolean hasBeenUnder500ThisMonth = false;
      private static Boolean hasBeenUnder50ThisMonth = false;
 
      public CheckingAccount(double initialBalance)
      {
            balance = initialBalance;
            totalServiceCharge = 0.0;
      }
 
      public double getBalance()
      {
            return balance;
      }
 
      public void setBalance(double transAmt, int tCode)
      {
            if(tCode == 1){
                balance -= transAmt;
                setServiceCharge(CHECK_FEE);
            }
            else if(tCode == 2){
                balance += transAmt;
                setServiceCharge(DEPOSIT_FEE);
            }
            else if(tCode == 0){
                balance -= transAmt;
            }
      }
 
      public double getServiceCharge()
      {
            return totalServiceCharge;
      }
 
        public void setServiceCharge(double currentServiceCharge)
      {
            totalServiceCharge += currentServiceCharge;
      }
        
        public Boolean willBeChargedForLowBalance()
        {
            if (hasBeenUnder500ThisMonth) {
                                System.out.println("if called");
                return false;
            }
            else{
                if (balance < 500) {
                    System.out.println("else called");
                    setServiceCharge(LOW_BALANCE_FEE);
                    Transaction lowBalanaceTransaction = new Transaction(Main.getTransCount(),
                            3, 5.00);
                    Main.userAccount.addTrans(lowBalanaceTransaction);
                    hasBeenUnder500ThisMonth = true;
                    return true;
                }
            }
                return false;    
        }
        public Boolean NotifyAboutUltraLowBalance()
        {
            if (hasBeenUnder50ThisMonth) {
                if(balance > 50){
                	hasBeenUnder50ThisMonth = false;
                } 
                	return false;
                
            }
            else{
                if (balance < 50) {
                    hasBeenUnder50ThisMonth = true;
                    Transaction negBalanaceTransaction = new Transaction(Main.getTransCount(),
                            3, 10.00);
                    Main.userAccount.addTrans(negBalanaceTransaction);
                    return true;
                }
            }
                return false;    
        }
        public Boolean willBeChargedForNegativeBalance()
        {
        	if (balance < 0) {
                    setServiceCharge(NEGATIVE_BALANCE_FEE);
                    
                    return true;
                }
            
                return false;    
        }
        
        public void addTrans(Transaction transToAdd)
        {
            double transAmt = transToAdd.getTransAmount();
            int transType = transToAdd.getTransId();
            
            setBalance(transAmt, transType);
            Main.addTrans(transToAdd);
            
        }
            
}
