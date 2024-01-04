/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment3;

/**
 *
 * @author abdel
 */
public class PrimaryMortgage extends LoanAccount {
    
    private double PMIMonthlyAmount;
    private Address address;
    
    public PrimaryMortgage(double principle, double annualInterestRate, int months, double PMI, Address addy) {
        super(principle, annualInterestRate, months);
        PMIMonthlyAmount = PMI;
        address = addy;
    }
    
    public String toString() {
        return super.toString() + String.format("\nPMI Monthly Amount: $%.2f\nProperty Address: \n%s\n", PMIMonthlyAmount, address);
    }
}