/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package loanaccount;

import java.lang.Math;

public class LoanAccount {
    
    private static double annualInterestRate;
    private double principle;
    
    public LoanAccount(double principle) {
        this.principle = principle;
    }
    
    public static void setAnnualInterestRate(double newInterestRate) {
        annualInterestRate = newInterestRate;
    }
    
    public double calculateMonthlyPayment(int numberOfPayments) {
        double monthlyInterest = annualInterestRate / 12;
        double monthlyPayment = principle * ( monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -numberOfPayments)));
        return monthlyPayment;
    }
    
    public static void main(String[] args) {

        LoanAccount.setAnnualInterestRate(0.01);
        LoanAccount loan1 = new LoanAccount(5000);
        LoanAccount loan2 = new LoanAccount(31000);
        
        System.out.println("Monthly payments for loan1 of $5000.00 and loan2 $3100.00 for 3, 5, and 6 year loans at 1% interest.");
        System.out.println("Loan\t3 years\t5 years\t6 years");
        System.out.printf("loan1\t%.2f\t%.2f\t%.2f\n", loan1.calculateMonthlyPayment(36), loan1.calculateMonthlyPayment(60), loan1.calculateMonthlyPayment(72));
        System.out.printf("loan2\t%.2f\t%.2f\t%.2f\n", loan2.calculateMonthlyPayment(36), loan2.calculateMonthlyPayment(60), loan2.calculateMonthlyPayment(72));
        System.out.println("");
        
        LoanAccount.setAnnualInterestRate(0.05);
        System.out.println("Monthly payments for loan1 of $5000.00 and loan2 $3100.00 for 3, 5, and 6 year loans at 5% interest.");
        System.out.println("Loan\t3 years\t5 years\t6 years");
        System.out.printf("loan1\t%.2f\t%.2f\t%.2f\n", loan1.calculateMonthlyPayment(36), loan1.calculateMonthlyPayment(60), loan1.calculateMonthlyPayment(72));
        System.out.printf("loan2\t%.2f\t%.2f\t%.2f\n", loan2.calculateMonthlyPayment(36), loan2.calculateMonthlyPayment(60), loan2.calculateMonthlyPayment(72));
    }
}
