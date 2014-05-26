
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CAOptionsFrame extends CAOptionsFrameL {
    
    private static JMenuBar menuBar;
    private static JMenu fileMenu, accountMenu, transactionMenu;
    private static JMenuItem accountAddNew, accountListAllTrans, 
            accountListChecks, accountListDepos, accountFind, fileWrite, 
            fileRead, transactionNew;
    private static JTextArea mainTextArea;
   

    public CAOptionsFrame (String title) {
        
        super(title);

        MenuListener listener = new MenuListener();
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        accountMenu = new JMenu("Accounts");
        transactionMenu = new JMenu("Transaction");
        
        fileWrite = new JMenuItem("Write File");
        fileRead = new JMenuItem("Read File");
        
        transactionNew = new JMenuItem("Add Transaction");
        
        accountAddNew = new JMenuItem("Add new Account");
        accountListAllTrans = new JMenuItem("List Account's Transactions");
        accountListChecks = new JMenuItem("List Checks");
        accountListDepos = new JMenuItem("List Deposits");
        accountFind = new JMenuItem("Find an Account");
        
        fileMenu.add(fileWrite);
        fileMenu.add(fileRead);
        
        transactionMenu.add(transactionNew);
        
        accountMenu.add(accountAddNew);
        accountMenu.add(accountListAllTrans);
        accountMenu.add(accountListChecks);
        accountMenu.add(accountListDepos);
        accountMenu.add(accountFind);
        
        menuBar.add(fileMenu);
        menuBar.add(accountMenu);
        menuBar.add(transactionMenu);
        
        fileWrite.addActionListener(listener);
        fileRead.addActionListener(listener);
        transactionNew.addActionListener(listener);
        accountAddNew.addActionListener(listener);
        accountListAllTrans.addActionListener(listener);
        accountListChecks.addActionListener(listener);
        accountListDepos.addActionListener(listener);
        accountFind.addActionListener(listener);
        
        mainTextArea = new JTextArea(15, 20);
        mainTextArea.setEditable(false);
        
        mainTextArea.setFont(new Font("Monospaced",Font.PLAIN, 12));
        
        getContentPane().add(mainTextArea);
        
        setJMenuBar(menuBar);
    }
    
    private class MenuListener implements ActionListener {
        private JTextArea textArea;

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();
            
            if (source.equals("Write File")) {
               
                Main.chooseSaveFile();
            } else if (source.equals("Read File")) {
               
                Main.chooseLoadFile();
            } else if (source.equals("Add Transaction")) {
                if (Main.userAccount == null) {
                    mainTextArea.setText("Please Add an Account or \nLoad a File.");
                }
                else {
                    int futureTransCode = Main.getTransCode();
                    Transaction newTrans;
                    
                    if (futureTransCode == 1) {
                        newTrans = new Check(Main.userAccount.getTransCount(),
                                futureTransCode, Main.getCheckNum(), Main.getTransAmt());
                    } else {
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
                    Main.setChangesMade(true);
                }
            } else if (source.equals("Add new Account")) {
                
                String userInitialBalanceStr, name;
                double userInitialBalance;
                
                name = JOptionPane.showInputDialog ("Enter the Account Name: ");
                userInitialBalanceStr = JOptionPane.showInputDialog ("Enter your Initial Balance: ");
                userInitialBalance= Double.parseDouble(userInitialBalanceStr);
                Main.userAccount = new CheckingAccount(userInitialBalance, name);
                
                Main.accountsVector.addElement(Main.userAccount);
                
            } else if (source.equals("List Account's Transactions")) {
                
                String message = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+
                        "Transaction List for\nName: " + Main.userAccount.name 
                        + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" +
                        "ID     Type              Amount\n";
                
                int transCount = Main.userAccount.getTransCount();
                for (int i = 0; i < transCount; i++) {
                    Transaction trans = Main.userAccount.getTrans(i);
                    message += trans.toString();
                }

                mainTextArea.setText(message);
            } else if (source.equals("List Checks")) {
                String message = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+ 
                        "Check List for\nName: " + Main.userAccount.name
                        + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" +
                        "ID     Chk#              Amount\n"
                        + "-------------------------------\n";
                
                int transCount = Main.userAccount.getTransCount();
                for (int i = 0; i < transCount; i++) {
                    Transaction trans = Main.userAccount.getTrans(i);
                    if  (trans.getTransId() == 1) {
                        message += trans.toShortString();
                    }
                }

                mainTextArea.setText(message);
            } else if (source.equals("List Deposits")) {
                String message = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+ 
                        "Deposit List for\nName: " + Main.userAccount.name
                        + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" +
                        "ID     Type              Amount\n"
                        + "-------------------------------\n";
                
                int transCount = Main.userAccount.getTransCount();
                for (int i = 0; i < transCount; i++) {
                    Transaction trans = Main.userAccount.getTrans(i);
                    if  (trans.getTransId() == 2) {
                        message += trans.toShortString();
                    }
                }
                mainTextArea.setText(message);
            } else {
                boolean match = false;
                int indexMatched = -1;
                        String searchName = JOptionPane.showInputDialog("Enter the Account Name");
                        for (int i = 0; i < Main.accountsVector.size() - 1; i++) {
                            if (searchName.equalsIgnoreCase(Main.accountsVector.elementAt(i).getName())) {
                                match = true;
                                indexMatched = i;
                            }
                        }
                        if (match) {
                            Main.userAccount = Main.accountsVector.elementAt(indexMatched);
                            mainTextArea.setText(searchName + " found and loaded.");
                        }
                        else {
                            mainTextArea.setText(searchName + " not found.");
                        }
            }
 
        }
        
    }
    
}
