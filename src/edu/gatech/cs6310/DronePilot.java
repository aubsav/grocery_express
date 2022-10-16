//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: DronePilot
// 
// Notes: Pilots can only fly one drone at a time
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;

public class DronePilot extends User
{
	private String accountID;
	private String licenseID;
	private String taxID;
	private int experience;
	
	private boolean currentlyFlyingDrone;
	private String droneID;
	
	public DronePilot(String accountID, String firstName, String lastName, String phoneNumber, String taxID, String licenseID, String experience) 
	{
		this.accountID = accountID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.taxID = taxID;
		this.licenseID = licenseID;
		this.experience = Integer.parseInt(experience);
		
		droneID = "";
		currentlyFlyingDrone = false;
	}
	
	public void flyDrone(String droneID)
	{
		currentlyFlyingDrone = true;
		this.droneID = droneID;
	}
	
	public void removeDrone()
	{
		currentlyFlyingDrone = false;
		droneID = "";
	}
	
	public void deliverOrder()
	{
		experience += 1;
	}
	
	public String getAccountID()
	{
		return accountID;
	}
	
	public String getDroneID()
	{
		return droneID;
	}
	
	public String getLicenseID()
	{
		return licenseID;
	}	
	
	public String getTaxID()
	{
		return taxID;
	}
	
	public int getExperience()
	{
		return experience;
	}
	
	public boolean getCurrentlyFlyingDrone()
	{
		return currentlyFlyingDrone;
	}

}