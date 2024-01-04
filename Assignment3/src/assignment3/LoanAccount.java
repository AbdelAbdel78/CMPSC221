/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package assignment3;

import java.lang.Math;

public class LoanAccount {
    
    private double annualInterestRate;
    private double principal;
    private int months;
    
    public LoanAccount(double principle, double annualInterestRate, int months) {
        this.principal = principle;
        this.annualInterestRate = annualInterestRate;
        this.months = months;
    }
    
    public double calculateMonthlyPayment() {
        double monthlyInterest = annualInterestRate / 1200;
        double monthlyPayment = principal * ( monthlyInterest / (1 - Math.pow(1 + monthlyInterest, - months)));
        return monthlyPayment;
    }
    
    public double getAnnualInterestRate() { return annualInterestRate; }
    
    public double getPrincipal() { return principal; }
    
    public int getMonths() { return months; }
    
    public String toString() {
        return String.format("\nPrincipal: $%.2f\nAnnual Interest Rate: %.2f%%\nTerm of Loan in Months: %d\nMonthly Payment: $%.2f"
                , getPrincipal(), getAnnualInterestRate(), getMonths(), calculateMonthlyPayment());
    }
}