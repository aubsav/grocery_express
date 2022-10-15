package edu.gatech.cs6310;

public class Drone 
{
	public String droneID;
	public int weightCapacity;
	public String tripsUntilMaintenance;

	
	public int totalOrders;
	public int totalOrderWeight;
	
	public boolean currentlyFlying;
	public String currentPilotFirstName;
	public String currentPilotLastName;
	
	public int remainingWeightCapacity = weightCapacity - totalOrderWeight;
  
	public Drone(String droneID, String weightCapacity, String tripsUntilMaintenance)
	{
		this.droneID = droneID;
		this.weightCapacity = Integer.parseInt(weightCapacity);
		this.tripsUntilMaintenance = tripsUntilMaintenance;
	}
	
	public void assignPilot(String pilotFirstName, String pilotLastName)
	{
		currentlyFlying = true;
		currentPilotFirstName = pilotFirstName;
		currentPilotLastName = pilotLastName;
	}
	
	public void removePilot()
	{
		currentlyFlying = false;
		currentPilotFirstName = "";
		currentPilotLastName = "";
	}
	
}
