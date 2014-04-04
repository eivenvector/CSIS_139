/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csis_139;

/**
 *
 * @author Ivan
 */
public class Transaction
{
  
    private int transNumber;
    private int transId;
    private double transAmt;
   
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
   
    public double getTransAmount()
    {
        return transAmt;
    }
}
 