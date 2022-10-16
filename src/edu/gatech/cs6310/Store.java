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
	private String storeName;
	private int revenue;
	
	private int totalPurchases;
	private int totalTransfers;
	
	public TreeMap<String,Item> items = new TreeMap<String,Item>();
	public TreeMap<String,Drone> drones = new TreeMap<String,Drone>();
	public TreeMap<String,Order> orders = new TreeMap<String,Order>();
	
	public Store(String storeName, String revenue) 
	{
		this.storeName = storeName;
		this.revenue = Integer.parseInt(revenue);
		
		totalPurchases = 0;
		totalTransfers = 0;
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
    	Drone previousDrone = drones.get(findDroneIDOfCurrentPilot(pilot));
    	
    	boolean pilotAlreadyFlyingDrone = pilot.getCurrentlyFlyingDrone();
    	boolean droneAlreadyFlying = drone.getCurrentlyFlying();
    	
    	if(!pilotAlreadyFlyingDrone && !droneAlreadyFlying)
    	{
    		pilot.flyDrone(droneID);
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.getAccountID());
    	}
    	else if(pilotAlreadyFlyingDrone && droneAlreadyFlying)
    	{
    		pilot.removeDrone();
    		previousDrone.removePilot();
    		
    		pilot.flyDrone(droneID);
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.getAccountID());
    	}
    	else if(pilotAlreadyFlyingDrone)
    	{
    		previousDrone.removePilot();
    		pilot.flyDrone(droneID);
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.getAccountID());
    	}
    	else if(droneAlreadyFlying)
    	{
    		drone.removePilot();
    		drone.assignPilot(pilot.firstName,pilot.lastName,pilot.getAccountID());
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
    	orders.put(orderID,new Order(orderID,assignedDroneID,assignedCustomerUserNames));
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
		orders.get(orderID).addLine(itemName,quantity,unitPrice,items.get(itemName).getItemWeight());			
		String currentDroneID = findDroneIDOfCurrentOrder(orderID);
		drones.get(currentDroneID).setTotalOrderWeight(items.get(itemName).getItemWeight()*quantity);
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
	
    public boolean availableSpaceOnDrone(String droneID,int totalWeight)
    {
    	boolean result = false;
    	if(drones.get(droneID).getAvailableWeight() > (totalWeight))
    	{
    			result = true;
    	}
    	return result;
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
    
    private String findDroneIDOfCurrentPilot(DronePilot pilot)
    {
    	String droneID = "";
    	for (String i : drones.keySet())
    	{
    		if(drones.get(i).getDroneID().equals(pilot.getDroneID()))
    		{
    			droneID = i;
    		}
    	}
    	return droneID;
    }
    
//////////////////////////////////////////////////////////
//Purpose: Returns the drone ID that matches the order ID 
//////////////////////////////////////////////////////////
    
    public String findDroneIDOfCurrentOrder(String orderID)
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
    	drones.get(findDroneIDOfCurrentOrder(orderID)).deliverOrder(orderID);
    	orders.remove(orderID);
    }
    
    public void orderCancelled(String orderID)
    {
    	drones.get(findDroneIDOfCurrentOrder(orderID)).cancelOrder(orderID);
    	orders.remove(orderID);
    }
    
    public boolean transferOrder(String orderID, String droneID)
    {
    	boolean result = false;
    	if(availableSpaceOnDrone(droneID,orders.get(orderID).calculateTotalWeight()))
    	{
    		totalTransfers += 1;
    		drones.get(findDroneIDOfCurrentOrder(orderID)).cancelOrder(orderID);
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
		orders.put(orderID,new Order(orderID,assignedDroneID,assignedCustomerUserName));
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
    		System.out.println(i + "," + items.get(i).getItemWeight());
    	}
    }
    
    public void displayOrders()
    {
		for (String i : orders.keySet())
	   	{
	   		System.out.println("orderID:" + orders.get(i).getOrderID());
	   		orders.get(i).displayLines();
	    }
    }
    
    public void displayDrones()
    {
    	for (String i : drones.keySet())
    	{
    		System.out.print("droneID:" + i + 
    				",total_cap:" + drones.get(i).getWeightCapacity() + 
    				",num_orders:" + drones.get(i).getTotalOrders() + 
    				",remaining_cap:" + drones.get(i).getAvailableWeight() + 
    				",trips_left:" + drones.get(i).getTripsUntilMaintenance());
    		
    		if(drones.get(i).getCurrentlyFlying())
    		{
    			System.out.print(",flown_by:" + drones.get(i).getCurrentPilotFirstName() + "_"+ drones.get(i).getCurrentPilotLastName());
    		}
    		System.out.println("");
    	}
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
// Getter Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
	
	public String getStoreName()
	{
		return storeName;
	}
	
	public int getRevenue()
	{
		return revenue;
	}
	
	public int getTotalPurchases()
	{
		return totalPurchases;
	}
	
	public int getTotalTransfers()
	{
		return totalTransfers;
	}
	
    public int getTotalOverload()
    {
    	int total = 0;
    	for (String i : drones.keySet())
    	{
    		total += drones.get(i).getOverload();
    	}
    	return total;
    }
	
}