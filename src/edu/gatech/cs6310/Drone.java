//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: Drone
// 
// Notes: 
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

import java.util.TreeMap;

public class Drone 
{
	public String droneID;
	public int weightCapacity;
	public int tripsUntilMaintenance;
	
	public int totalOrders;
	public int totalOrderWeight;
	public int overload;
	
	public boolean currentlyFlying;
	public String currentPilotFirstName;
	public String currentPilotLastName;
	public String currentPilotAccountID;
	
	TreeMap<String,Order> orders = new TreeMap<String,Order>();
  
	public Drone(String droneID, String weightCapacity, String tripsUntilMaintenance)
	{
		this.droneID = droneID;
		this.weightCapacity = Integer.parseInt(weightCapacity);
		this.tripsUntilMaintenance = Integer.parseInt(tripsUntilMaintenance);
		
		currentPilotAccountID = "";
	}
	
	public void assignPilot(String pilotFirstName, String pilotLastName, String accountID)
	{
		currentlyFlying = true;
		currentPilotFirstName = pilotFirstName;
		currentPilotLastName = pilotLastName;
		currentPilotAccountID = accountID;
	}
	
	public void removePilot()
	{
		currentlyFlying = false;
		currentPilotFirstName = "";
		currentPilotLastName = "";
		currentPilotAccountID = "";
	}
	
	public void addOrder(String orderID, Order order)
	{
		orders.put(orderID,order);
		totalOrders = orders.size();
		totalOrderWeight += orders.get(orderID).calculateTotalWeight();
	}
	
	public void addItem(String orderID, Order order)
	{
		orders.put(orderID,order);
		totalOrders = orders.size();
		totalOrderWeight += orders.get(orderID).calculateTotalWeight();
	}
	
	public void deliverOrder(String orderID)
	{
		tripsUntilMaintenance -= tripsUntilMaintenance;
		totalOrderWeight -= orders.get(orderID).calculateTotalWeight();
		orders.remove(orderID);
		totalOrders = orders.size();
		overload = overload + totalOrders;
	}
	
	public void cancelOrder(String orderID)
	{
		totalOrderWeight -= orders.get(orderID).calculateTotalWeight();
		orders.remove(orderID);
		totalOrders = orders.size();
	}
	
}
