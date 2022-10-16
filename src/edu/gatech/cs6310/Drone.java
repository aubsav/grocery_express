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
	private String droneID;
	private int weightCapacity;
	private int tripsUntilMaintenance;
	
	private int totalOrders;
	private int totalOrderWeight;
	private int overload;
	
	private boolean currentlyFlying;
	private String currentPilotFirstName;
	private String currentPilotLastName;
	private String currentPilotAccountID;
	
	public TreeMap<String,Order> orders = new TreeMap<String,Order>();
  
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
	
	public void setTotalOrderWeight(int additionalWeight)
	{
		totalOrderWeight += additionalWeight;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
//
// Getter Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
	
	public String getDroneID()
	{
		return droneID;
	}
	
	public int getWeightCapacity()
	{
		return weightCapacity;
	}
	
	public int getTripsUntilMaintenance()
	{
		return tripsUntilMaintenance;
	}
	
	public int getOverload()
	{
		return overload;
	}
	
	public int getTotalOrders()
	{
		return totalOrders;
	}
	
	public String getCurrentPilotAccountID()
	{
		return currentPilotAccountID;
	}
	
	public String getCurrentPilotFirstName()
	{
		return currentPilotFirstName;
	}
	
	public String getCurrentPilotLastName()
	{
		return currentPilotLastName;
	}
	
	public boolean getCurrentlyFlying()
	{
		return currentlyFlying;
	}
	
	public int getAvailableWeight()
	{
		return (weightCapacity - totalOrderWeight);
	}
}
