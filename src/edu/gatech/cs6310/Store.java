package edu.gatech.cs6310;

import java.util.ArrayList;

public class Store 
{
	public String storeName;
	public String revenue;
	
	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Drone> drones = new ArrayList<Drone>();
	//ArrayList<DronePilot> dronePilot = new ArrayList<DronePilot>();
	
	public Store(String storeName, String revenue) 
	{
		this.storeName = storeName;
		this.revenue = revenue;
	}
	
    // Returns true if the item name already exists
    public boolean itemNameExists(String itemName)
    {
    	boolean result = false;
    	for(int i = 0; i < items.size(); i++)
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
		items.add(new Item(itemName,weight));
	}
	
    public void displayItems()
    {
    	sortItemNames();
    }
    
    public void sortItemNames()
    {
    	for(int i = 0; i < items.size(); i++)
    	{
    		System.out.println(items.get(i).itemName + "," + items.get(i).itemWeight);
    	}
    }
    
    // Returns true if the droneID already exists
    public boolean droneIDExists(String droneID)
    {
    	boolean result = false;
    	for(int i = 0; i < drones.size(); i++)
    	{
    		if(drones.get(i).droneID.equals(droneID))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
	
	public void addDrone(String droneID, String weightCapacity, String tripsUntilMaintenance)
	{
		drones.add(new Drone(droneID,weightCapacity,tripsUntilMaintenance));
	}
	
    public void displayDrones()
    {
    	sortDroneIDs();
    }
    
    public void sortDroneIDs()
    {
    	for(int i = 0; i < drones.size(); i++)
    	{
    		System.out.print("droneID:" + drones.get(i).droneID + 
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
    	if(!pilot.currentlyFlyingDrone && !drones.get(indexOfDroneID(droneID)).currentlyFlying)
    	{
    		pilot.flyDrone(droneID);
    		drones.get(indexOfDroneID(droneID)).assignPilot(pilot.firstName,pilot.lastName);
    	}
    	else if(pilot.currentlyFlyingDrone && drones.get(indexOfDroneID(droneID)).currentlyFlying)
    	{
    		pilot.removeDrone();
    		drones.get(indexOfPilotCurrentDroneID(pilot)).removePilot();
    		
    		pilot.flyDrone(droneID);
    		drones.get(indexOfDroneID(droneID)).assignPilot(pilot.firstName,pilot.lastName);
    	}
    	else if(pilot.currentlyFlyingDrone)
    	{
    		drones.get(indexOfPilotCurrentDroneID(pilot)).removePilot();
    		pilot.flyDrone(droneID);
    		drones.get(indexOfDroneID(droneID)).assignPilot(pilot.firstName,pilot.lastName);
    	}
    	else if(drones.get(indexOfDroneID(droneID)).currentlyFlying)
    	{
    		// unassignment of the pilot not referenced here from its current drone is done in
    		// DeliveryService.java
    		drones.get(indexOfDroneID(droneID)).removePilot();
    		drones.get(indexOfDroneID(droneID)).assignPilot(pilot.firstName,pilot.lastName);
    	}
    }
    
    public boolean droneIsFlying(String droneID)
    {
    	return drones.get(indexOfDroneID(droneID)).currentlyFlying;
    }
    
    
    public int indexOfDroneID(String droneID)
    {
    	int index = 0;
    	for(int i = 0; i < drones.size(); i++)
    	{
    		if(drones.get(i).droneID.equals(drones))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
    
    public int indexOfPilotCurrentDroneID(DronePilot pilot)
    {
    	int index = 0;
    	for(int i = 0; i < drones.size(); i++)
    	{
    		if(drones.get(i).droneID.equals(pilot.droneID))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
    
    public int indexOfDroneCurrentPilotID(String droneID, DronePilot pilot)
    {
    	int index = 0;
    	for(int i = 0; i < drones.size(); i++)
    	{
    		if(drones.get(i).currentPilotFirstName.equals(pilot.firstName))
    		{
    			index = i;
    		}
    	}
    	return index;
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