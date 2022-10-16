//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: Line
// 
// Notes: 
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

public class Line 
{
	private String itemName;
	private int quantity;
	private int itemPrice;
	private int itemWeight;
	
	public Line(String itemName, int quantity, int itemPrice, int itemWeight)
	{
		this.itemName = itemName;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		this.itemWeight = itemWeight;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
//
// Getter Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
	
	public String getItemName()
	{
		return itemName;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public int getItemPrice()
	{
		return itemPrice;
	}
	
	public int getItemWeight()
	{
		return itemWeight;
	}
}
