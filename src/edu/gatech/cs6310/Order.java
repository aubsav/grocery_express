//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: Order
// 
// Notes: 
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

import java.util.TreeMap;

public class Order
{
	private String orderID;
	private String assignedDroneID;
	private String assignedCustomerUserName;
	
	public TreeMap<String,Line> lines = new TreeMap<String,Line>();
	
	public Order(String orderID, String assignedDroneID, String assignedCustomerUserName) 
	{
		this.orderID = orderID;
		this.assignedDroneID = assignedDroneID;
		this.assignedCustomerUserName = assignedCustomerUserName;
	}
	
	public void addLine(String itemName, int quantity, int unitPrice, int weight)
	{
		lines.put(itemName,new Line(itemName,quantity,unitPrice,weight));
	}

	public boolean itemAlreadyExists(String itemName)
    {
    	boolean result = false;
    	for (String i : lines.keySet())
    	{
    		if(lines.get(i).getItemName().equals(itemName))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
	
	public int calculateTotalPrice()
    {
    	int result = 0;
    	for (String i : lines.keySet())
    	{
    		result = result + (lines.get(i).getQuantity() * lines.get(i).getItemPrice());
    	}
    	return result;
    }
	
	public int calculateTotalWeight()
    {
    	int result = 0;
    	for (String i : lines.keySet())
    	{
    		result += (lines.get(i).getQuantity() * lines.get(i).getItemWeight());
    	}
    	return result;
    }
	
	public void displayLines()
	{
		for (String i : lines.keySet())
		{
			System.out.println("item_name:" + i +
					",total_quantity:" + lines.get(i).getQuantity() +
					",total_cost:" + (lines.get(i).getItemPrice()*lines.get(i).getQuantity()) +
					",total_weight:" + (lines.get(i).getItemWeight()*lines.get(i).getQuantity()));
		}
	}
	
	public String getOrderID()
	{
		return orderID;
	}
	
	public String getAssignedDroneID()
	{
		return assignedDroneID;
	}
	
	public String getAssignedCustomerUserName()
	{
		return assignedCustomerUserName;
	}
}
