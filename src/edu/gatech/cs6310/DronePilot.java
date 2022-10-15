//////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Class: DronePilot
//
// Written by: Aubrey Savage
//
//////////////////////////////////////////////////////
package edu.gatech.cs6310;

import java.util.ArrayList;

public class DronePilot extends User
{
	public String accountID;
	public String licenseID;
	public String taxID;
	public String experience;
	
	public boolean currentlyFlyingDrone;
	public String droneID;
	
	private boolean validLicense;
	
	public DronePilot(String accountID, String firstName, String lastName, String phoneNumber, String taxID, String licenseID, String experience) 
	{
		this.accountID = accountID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.taxID = taxID;
		this.licenseID = licenseID;
		this.experience = experience;
	}
	
	public void flyDrone(String droneID)
	{
		currentlyFlyingDrone = true;
		this.droneID = droneID;
	}
	
	public void removeDrone()
	{
		currentlyFlyingDrone = false;
		//droneID = "";
	}

}