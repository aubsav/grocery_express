//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: Store
// 
// Notes:
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

import java.util.TreeMap;

public class Store 
{
	public String storeName;
	public int revenue;
	
	public int totalPurchases;
	public int totalTransfers;
	
	TreeMap<String,Item> items = new TreeMap<String,Item>();
	TreeMap<String,Drone> drones = new TreeMap<String,Drone>();
	TreeMap<String,Order> orders = new TreeMap<String,Order>();
	
	public Store(String storeName, String revenue) 
	{
		this.storeName = storeName;
		this.revenue = Integer.parseInt(revenue);
		
		totalPurchases = 0;
		totalTransfers = 0;
	}
	

//////////////////////////////////////////////////////////////////////////////////////////
//
// Name: itemNameExists(String itemName)
//
// Returns: boolean
//
// Purpose: Returns true if the item name already exists
//
//////////////////////////////////////////////////////////////////////////////////////////
	
    public boolean itemNameExists(String itemName)
    {
    	boolean result = false;
    	for (String i : items.keySet())
    	{
    		if(items.get(i).itemName.equals(itemName))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
	
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: assignDroneToPilot(String droneID, DronePilot pilot)
//
//Returns: void
//
//Purpose: assigns drone to pilot and pilot to drone
//         removes associated pilot from previous drone done in DeliveryService.java
//         removes associated drone from previous pilot
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public void assignDroneToPilot(String droneID, DronePilot pilot)
    {
    	Drone drone = drones.get(droneID);
    	Drone previousDrone = drones.get(pilotCurrentDroneID(pilot));
    	
    	boolean pilotAlreadyFlyingDrone = pilot.currentlyFlyingDrone;
    	boolean droneAlreadyFlying = drone.currentlyFlying;
    	
    	if(!pilotAlreadyFlyingDrone && !droneAlreadyFlying)
    	{
    		pilot.flyDrone(droneID);
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    	else if(pilotAlreadyFlyingDrone && droneAlreadyFlying)
    	{
    		pilot.removeDrone();
    		previousDrone.removePilot();
    		
    		pilot.flyDrone(droneID);
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    	else if(pilotAlreadyFlyingDrone)
    	{
    		previousDrone.removePilot();
    		pilot.flyDrone(droneID);
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    	else if(droneAlreadyFlying)
    	{
    		drone.removePilot();
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: assignOrderToDrone(String orderID, String assignedDroneID, String assignedCustomerUserNames)
//
//Returns: void
//
//Purpose: adds a new order to the orders TreeMap
//         adds the order to the drone's orders TreeMap
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public void assignOrderToDrone(String orderID, String assignedDroneID, String assignedCustomerUserNames)
    {
    	orders.put(orderID,new Order(storeName,orderID,assignedDroneID,assignedCustomerUserNames));
    	drones.get(assignedDroneID).addOrder(orderID,orders.get(orderID));
    }
   
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: addItemToOrder(String itemName, int quantity, int unitPrice, String orderID)
//
//Returns: void
//
//Purpose: adds price to item
//         adds line to the orders TreeMap
//         finds the drone carrying this item
//         adds weight to the drone's total weight
//
//////////////////////////////////////////////////////////////////////////////////////////  
    
	public void addItemToOrder(String itemName, int quantity, int unitPrice, String orderID)
	{
		items.get(itemName).itemPrice = unitPrice;
		orders.get(orderID).addLine(itemName,quantity,unitPrice,items.get(itemName).itemWeight);			
		String currentDroneID = orderIDCurrentDrone(orderID);
		drones.get(currentDroneID).totalOrderWeight += (items.get(itemName).itemWeight*quantity);
	}
   
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: availableSpaceOnDrone(String orderID, int quantity, int weight)
//
//Returns: boolean
//
//Purpose: find drone ID based on order ID
//         returns true if the drone has enough space for the given weight
// 
//////////////////////////////////////////////////////////////////////////////////////////
	
    public boolean availableSpaceOnDrone(String orderID, int quantity, int weight)
    {
    	
    	boolean result = false;
    	String droneID = orderIDCurrentDrone(orderID);
    	if((drones.get(droneID).weightCapacity - drones.get(droneID).totalOrderWeight) > (quantity*weight))
    	{
    			result = true;
    	}
    	return result;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: availableSpaceOnNewDrone(String orderID, int totalWeight, String droneID)
//
//Returns: boolean
//
//Purpose: given a droneID
//         returns true if the drone has enough space for the given weight
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public boolean availableSpaceOnNewDrone(int totalWeight, String droneID)
    {
    	boolean result = false;
    	if((drones.get(droneID).weightCapacity - drones.get(droneID).totalOrderWeight) > (totalWeight))
    	{
    			result = true;
    	}
    	return result;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: getTotalOverload()
//
//Returns: int
//
//Purpose: add all overloads for all drones
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public int getTotalOverload()
    {
    	int total = 0;
    	for (String i : drones.keySet())
    	{
    		total += drones.get(i).overload;
    	}
    	return total;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
//Name: addToRevenue(String orderID)
//
//Returns: void
//
//Purpose: adds order price to revenue when order is purchased
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public void addToRevenue(String orderID)
    {
    	revenue +=  orders.get(orderID).calculateTotalPrice();
    }
    
    
 
//////////////////////////////////////////////////////////////////////////////////////////
//
// Indexing Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
    
/////////////////////////////////////////////////////////////////
//Purpose: Returns the drone ID that matches the pilot's drone ID 
/////////////////////////////////////////////////////////////////
    
    public String pilotCurrentDroneID(DronePilot pilot)
    {
    	String droneID = "";
    	for (String i : drones.keySet())
    	{
    		if(drones.get(i).droneID.equals(pilot.droneID))
    		{
    			droneID = i;
    		}
    	}
    	return droneID;
    }
    
//////////////////////////////////////////////////////////
//Purpose: Returns the drone ID that matches the order ID 
//////////////////////////////////////////////////////////
    
    public String orderIDCurrentDrone(String orderID)
    {
    	String droneID = "";
    	for (String i : drones.keySet())
    	{    		
    		if(drones.get(i).orders.containsKey(orderID))
    		{
    			droneID = i;
    			break;
    		}
    	}
    	return droneID;
    }
    

//////////////////////////////////////////////////////////////////////////////////////////
//
// Order Modification Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public void orderPurchased(String orderID)
    {
    	totalPurchases += 1;
    	addToRevenue(orderID);
    	drones.get(orderIDCurrentDrone(orderID)).deliverOrder(orderID);
    	orders.remove(orderID);
    }
    
    public void orderCancelled(String orderID)
    {
    	drones.get(orderIDCurrentDrone(orderID)).cancelOrder(orderID);
    	orders.remove(orderID);
    }
    
    public boolean transferOrder(String orderID, String droneID)
    {
    	boolean result = false;
    	if(availableSpaceOnNewDrone(orders.get(orderID).calculateTotalWeight(),droneID))
    	{
    		totalTransfers += 1;
    		drones.get(orderIDCurrentDrone(orderID)).cancelOrder(orderID);
    		drones.get(droneID).addOrder(orderID,orders.get(orderID));
    		result = true;
    	}
    	
    	return result;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
// Add Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
    
	public void addItem(String itemName, String weight)
	{
		items.put(itemName,new Item(itemName,weight));
	}
	
	public void addOrder(String orderID, String assignedDroneID, String assignedCustomerUserName)
	{
		orders.put(orderID,new Order(storeName,orderID,assignedDroneID,assignedCustomerUserName));
	}
	
	public void addDrone(String droneID, String weightCapacity, String tripsUntilMaintenance)
	{
		drones.put(droneID,new Drone(droneID,weightCapacity,tripsUntilMaintenance));
	}
    
//////////////////////////////////////////////////////////////////////////////////////////
//
// Display Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public void displayItems()
    {
    	for (String i : items.keySet())
    	{
    		System.out.println(i + "," + items.get(i).itemWeight);
    	}
    }
    
    public void displayOrders()
    {
		for (String i : orders.keySet())
	   	{
	   		System.out.println("orderID:" + orders.get(i).orderID);
	   		orders.get(i).displayLines();
	    }
    }
    
    public void displayDrones()
    {
    	for (String i : drones.keySet())
    	{
    		System.out.print("droneID:" + i + 
    				",total_cap:" + drones.get(i).weightCapacity + 
    				",num_orders:" + drones.get(i).totalOrders + 
    				",remaining_cap:" + (drones.get(i).weightCapacity - drones.get(i).totalOrderWeight) + 
    				",trips_left:" + drones.get(i).tripsUntilMaintenance);
    		if(drones.get(i).currentlyFlying)
    		{
    			System.out.print(",flown_by:" + drones.get(i).currentPilotFirstName + "_"+ drones.get(i).currentPilotLastName);
    		}
    		System.out.println("");
    	}
    }
}