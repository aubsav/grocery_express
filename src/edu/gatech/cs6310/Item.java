//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: Item
// 
// Notes:
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

public class Item 
{
	public String itemName;
	public int itemWeight;
	public int itemPrice;
	public String itemDescription;
	
	public Item(String itemName, String itemWeight) 
	{
		this.itemName = itemName;
		this.itemWeight = Integer.parseInt(itemWeight);
	}
}
