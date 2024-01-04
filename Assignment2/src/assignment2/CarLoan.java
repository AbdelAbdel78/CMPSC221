/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author abdel
 */
public class CarLoan extends LoanAccount {
    
    private String vehicleVIN;
    
    public CarLoan(double principle, double annualInterestRate, int months, String vin) {
        super(principle, annualInterestRate, months);
        vehicleVIN = vin;
    }
    
    public String toString() {
        return super.toString() + String.format("\nVehicle VIN: %s\n", vehicleVIN);
    }
}