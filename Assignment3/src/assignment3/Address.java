/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment3;

/**
 *
 * @author abdel
 */
public class Address {
    
    private String street;
    private String city;
    private String state;
    private String zipcode;
    
    public Address(String street, String city, String state, String zipcode) {
        this.street = street;
        this. city = city;
        this. state = state;
        this.zipcode = zipcode;
    }
    
    public String getStreet() { return street; }
    
    public String getCity() { return city; }
    
    public String getState() { return state; }
    
    public String getZipcode() { return zipcode; }
    
    public String toString() {
        return  String.format("\t%s\n\t%s, %s %s", getStreet(), getCity(), getState(), getZipcode());
    }
}
