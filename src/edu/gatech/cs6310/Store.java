package edu.gatech.cs6310;

import java.util.TreeMap;

public class Store 
{
	public String storeName;
	public String revenue;
	
	TreeMap<String,Item> items = new TreeMap<String,Item>();
	TreeMap<String,Drone> drones = new TreeMap<String,Drone>();
	TreeMap<String,Order> orders = new TreeMap<String,Order>();
	
	public Store(String storeName, String revenue) 
	{
		this.storeName = storeName;
		this.revenue = revenue;
	}
	
    // Returns true if the item name already exists
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
	
	public void addItem(String itemName, String weight)
	{
		items.put(itemName,new Item(itemName,weight));
	}
	
	public int itemWeight(String itemName)
	{
    	int result = -1;
    	for (String i : items.keySet())
    	{
    		if(items.get(i).itemName.equals(itemName))
    		{
    			result = items.get(i).itemWeight;
    		}
    	}
    	return result;
	}
	
	public void addOrder(String orderID, String assignedDroneID, String assignedCustomerUserName)
	{
		orders.put(orderID,new Order(storeName,orderID,assignedDroneID,assignedCustomerUserName));
	}
	
    public void displayItems()
    {
    	sortItemNames();
    }
    
    public void sortItemNames()
    {
    	for (String i : items.keySet())
    	{
    		System.out.println(i + "," + items.get(i).itemWeight);
    	}
    }
    
	
	public void addDrone(String droneID, String weightCapacity, String tripsUntilMaintenance)
	{
		drones.put(droneID,new Drone(droneID,weightCapacity,tripsUntilMaintenance,storeName));
	}
	
    public void displayDrones()
    {
    	sortDroneIDs();
    }
    
    public void sortDroneIDs()
    {
    	for (String i : drones.keySet())
    	{
    		System.out.print("droneID:" + i + 
    				",total_cap: " + drones.get(i).weightCapacity + 
    				",num_orders:" + drones.get(i).totalOrders + 
    				",remaining_cap:" + drones.get(i).remainingWeightCapacity + 
    				",trips_left:" + drones.get(i).tripsUntilMaintenance);
    		if(drones.get(i).currentlyFlying)
    		{
    			System.out.print(",flown_by:" + drones.get(i).currentPilotFirstName + "_"+ drones.get(i).currentPilotLastName);
    		}
    		System.out.println("");
    	}
    }
    
    public void assignDroneToPilot(String droneID, DronePilot pilot)
    {
    	if(!pilot.currentlyFlyingDrone && !drones.get(droneID).currentlyFlying)
    	{
    		pilot.flyDrone(droneID);
    		drones.get(droneID).assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    	else if(pilot.currentlyFlyingDrone && drones.get(droneID).currentlyFlying)
    	{
    		pilot.removeDrone();
    		drones.get(pilotCurrentDroneID(pilot)).removePilot();
    		
    		pilot.flyDrone(droneID);
    		drones.get(droneID).assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    	else if(pilot.currentlyFlyingDrone)
    	{
    		drones.get(pilotCurrentDroneID(pilot)).removePilot();
    		pilot.flyDrone(droneID);
    		drones.get(droneID).assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    	else if(drones.get(droneID).currentlyFlying)
    	{
    		// unassignment of the pilot not referenced here from its current drone is done in
    		// DeliveryService.java
    		drones.get(droneID).removePilot();
    		drones.get(droneID).assignPilot(pilot.firstName,pilot.lastName,pilot.accountID);
    	}
    }
    
    public boolean droneIsFlying(String droneID)
    {
    	return drones.get(droneID).currentlyFlying;
    }
    
    
    
    public String pilotCurrentDroneID(DronePilot pilot)
    {
    	String index = "";
    	for (String i : drones.keySet())
    	{
    		if(drones.get(i).droneID.equals(pilot.droneID))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
    
    public String droneCurrentPilotID(String droneID, DronePilot pilot)
    {
    	String index = "";
    	for (String i : drones.keySet())
    	{
    		if(drones.get(i).currentPilotFirstName.equals(pilot.firstName))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
    
    public void assignOrderToDrone(String orderID, String assignedDroneID, String assignedCustomerUserNames)
    {
    	orders.put(orderID,new Order(storeName,orderID,assignedDroneID,assignedCustomerUserNames));
    	drones.get(assignedDroneID).addOrder(orderID,orders.get(orderID));
    }
    
    public void displayOrders()
    {
    	sortOrders();
    }
    
	public void sortOrders()
	{
		for (String i : orders.keySet())
	   	{
	   		System.out.println("orderID:" + orders.get(i).orderID);
	   		orders.get(i).displayLines();
	    }
	}
	
	public void addItemToOrder(String itemName, int quantity, int unitPrice, String orderID)
	{
		if(!orders.get(orderID).itemAlreadyExists(itemName))
		{
			items.get(itemName).itemPrice = unitPrice;
			orders.get(orderID).addLine(itemName,quantity,unitPrice,items.get(itemName).itemWeight);			
			
		}
		else
		{
			System.out.println("ERROR:item_already_ordered");
		}
	}
    
    public boolean availableSpaceOnDrone(String orderID, int quantity, int weight)
    {
    	boolean result = false;
    	String droneID = orderIDCurrentDrone(orderID);
    	if(drones.get(droneID).remainingWeightCapacity > (quantity*weight))
    	{
    			result = true;
    	}
    	return result;
    }
    
    public boolean availableSpaceOnDrone(String orderID, int totalWeight, String droneID)
    {
    	boolean result = false;
    	if(drones.get(droneID).remainingWeightCapacity > (totalWeight))
    	{
    			result = true;
    	}
    	return result;
    }
    
    public int getItemWeight(String itemName)
    {
    	return items.get(itemName).itemWeight;
    }
    
    public int getTotalPrice(String orderID)
    {
    	return orders.get(orderID).calculateTotalPrice();
    }
    
    public void orderPurchased(String orderID)
    {
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
    	if(availableSpaceOnDrone(orderID,orders.get(orderID).calculateTotalWeight(),droneID))
    	{
    		drones.get(orderIDCurrentDrone(orderID)).cancelOrder(orderID);
    		drones.get(droneID).addOrder(orderID,orders.get(orderID));
    		result = true;
    	}
    	
    	return result;
    }
    
    public void addToRevenue(String orderID)
    {
    	revenue = revenue + getTotalPrice(orderID);
    }
    
    // Returns the index of drone pilot assigned to the current drone ID
    public String orderIDCurrentDrone(String orderID)
    {
    	String droneID = "";
    	for (String i : drones.keySet())
    	{
    		if(drones.get(i).orders.get(orderID).orderID.equals(orderID))
    		{
    			droneID = i;
    		}
    	}
    	return droneID;
    }
/*
	private int calculateIncomingRevenue()
	{
		
	}
	
	public void assignOrderToDrone()
	{
		
	}
	
	private boolean compareWeight()
	{
		
	}
	
	private void buyDrone()
	{
		
	}
	
	private void hireEmployee()
	{
		
	}
	
	*/
}