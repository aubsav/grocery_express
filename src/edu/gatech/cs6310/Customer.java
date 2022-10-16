package edu.gatech.cs6310;

import java.util.ArrayList;
import java.util.TreeMap;

public class Customer extends User
{
	public int credit;
	private int totalOrderAmount;
	
	public String userName;
	public int customerRating;
	public int credits;
	
	TreeMap<String,Order> orders = new TreeMap<String,Order>();
	
	public Customer(String userName, String firstName, String lastName, String phoneNumber, String customerRating, String credits) 
	{
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.customerRating = Integer.parseInt(customerRating);
		this.credits = Integer.parseInt(credits);
	}

	public void addToOrder(int totalAmount)
	{
		credits = credits - totalAmount;
	}
	
	
	public void purchaseOrder(int totalPrice)
	{
		credits = credits - totalPrice;
	}


}
