/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author abdel
 */
public class Assignment2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        CarLoan carLoan = new CarLoan(25000.00, 4.25, 72, "IRQ3458977");
        Address propertyAddress = new Address("321 Main Street", "State College", "PA", "16801");
        PrimaryMortgage propertyLoan = new PrimaryMortgage(250000.00, 3.1, 360, 35.12, propertyAddress);
        UnsecuredLoan unsecuredLoan = new UnsecuredLoan(5000.00, 10.75, 48);
        
        //Print out the load information for each loan using the toString() method.
        System.out.format("%n%s%s%s%n", carLoan, propertyLoan, unsecuredLoan);
        
        //parsing a CSV file into Scanner class constructor  
        Scanner sc = new Scanner(new File("C:\\Users\\abdel\\Desktop\\Penn State\\2022 Spring\\CMPSC221\\Assignment2\\src\\assignment2"));  
        sc.useDelimiter(",");   //sets the delimiter pattern
        
        while (sc.hasNext()) {
            System.out.print(sc.next());  //find and returns the next complete token from this scanner  
        }
        
        sc.close();  //closes the scanner 
    }
}