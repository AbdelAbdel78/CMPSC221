/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment3;

import java.util.ArrayList;

/**
 *
 * @author abdel
 */
public class Customer {
    
    private final String firstName;
    private final String lastName;
    private final String SSN;
    private ArrayList<LoanAccount> loanAccounts;
    
    public Customer(String firstName, String lastName, String SSN) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.loanAccounts = new ArrayList<LoanAccount>();
    }
    
    public String getFirstName() { return firstName; }
    
    public String getLastName() { return lastName; }
    
    public String getSSN() { return SSN; }
    
//    public ArrayList<LoanAccount> getAccounts() { return loanAccounts; }
    
    public void addLoanAccount(LoanAccount account) {
        loanAccounts.add(account);
    }
    
    public String printMonthlyReport() {
        String out;
        out = String.format("Account Report for Customer: %s %s with SSN %s\n", getFirstName(), getLastName(), getSSN());
        
        for (LoanAccount loan : loanAccounts) {
            out = out + String.format("%s\n", loan);
        }
        
        return out;
    }
}