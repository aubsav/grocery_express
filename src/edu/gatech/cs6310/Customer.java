//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: Customer
// 
// Notes: 
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

public class Customer extends User
{
	public int credit;
	public String userName;
	public int customerRating;
	public int credits;
	
	public Customer(String userName, String firstName, String lastName, String phoneNumber, String customerRating, String credits) 
	{
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.customerRating = Integer.parseInt(customerRating);
		this.credits = Integer.parseInt(credits);
	}	
	
	public void purchaseOrder(int totalPrice)
	{
		credits = credits - totalPrice;
	}


}
