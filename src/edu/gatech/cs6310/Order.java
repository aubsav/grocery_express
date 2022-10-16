package edu.gatech.cs6310;

import java.util.ArrayList;
import java.util.TreeMap;

public class Order
{
	public String assignedStore;
	public String orderID;
	public String assignedDroneID;
	public String assignedCustomerUserName;
	
	public int weight;
	
	TreeMap<String,Line> lines = new TreeMap<String,Line>();
	
	public Order(String assignedStore, String orderID, String assignedDroneID, String assignedCustomerUserName) 
	{
		this.assignedStore = assignedStore;
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
    		if(lines.get(i).itemName.equals(itemName))
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
    		result = result + (lines.get(i).quantity * lines.get(i).itemPrice);
    	}
    	return result;
    }
	
	public int calculateTotalWeight()
    {
    	int result = 0;
    	for (String i : lines.keySet())
    	{
    		result += (lines.get(i).quantity * lines.get(i).itemWeight);
    	}
    	return result;
    }
	
	public void displayLines()
	{
		for (String i : lines.keySet())
		{
			System.out.println("item_name:" + i +
					",total_quantity:" + lines.get(i).quantity +
					",total_cost:" + (lines.get(i).itemPrice*lines.get(i).quantity) +
					",total_weight:" + (lines.get(i).itemWeight*lines.get(i).quantity));
		}
	}
}
