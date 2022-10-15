package edu.gatech.cs6310;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class DeliveryService {
	
	ArrayList<Store> stores = new ArrayList<Store>();
	ArrayList<DronePilot> dronePilots = new ArrayList<DronePilot>();

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";
        
        
        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if (tokens[0].equals("make_store")) 
                {
                	if(!storeNameExists(tokens[1]))
                	{
                		stores.add(new Store(tokens[1], tokens[2]));
                        System.out.println("OK:change_completed");
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_already_exists");
                	}
                } 
                
                else if (tokens[0].equals("display_stores")) 
                {
                	sortStoreNames();
                    System.out.println("OK:display_completed");

                } 
                
                else if (tokens[0].equals("sell_item")) 
                {
                	if(storeNameExists(tokens[1]))
                	{
                		if(!stores.get(indexOfStoreName(tokens[1])).itemNameExists(tokens[2]))
                		{
                			stores.get(indexOfStoreName(tokens[1])).addItem(tokens[2],tokens[3]);
                			System.out.println("OK:change_completed");
                		}
                		else
                		{
                			System.out.println("ERROR:item_identifier_already_exists");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist ");
                	}

                } 
                else if (tokens[0].equals("display_items")) 
                {
                	if(storeNameExists(tokens[1]))
                	{
                		stores.get(indexOfStoreName(tokens[1])).displayItems();
                	}

                } 
                else if (tokens[0].equals("make_pilot")) 
                {
                    if(!pilotAccountExists(tokens[1]))
                	{
                    	if(!pilotLicenseExists(tokens[6]))
                    	{
                    		dronePilots.add(new DronePilot(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7]));
                    		System.out.println("OK:change_completed");
                    	}
                    	else
                    	{
                    		System.out.println("ERROR:pilot_license_already_exists");
                    	}
                	}
                	else
                	{
                		System.out.println("ERROR:pilot_identifier_already_exists");
                	}
                    
                } 
                else if (tokens[0].equals("display_pilots")) 
                {
                	sortPilotAccounts();
                    System.out.println("OK:display_completed");
                } 
                else if (tokens[0].equals("make_drone")) 
                {
                    //System.out.println("store: " + tokens[1] + ", drone: " + tokens[2] + ", capacity: " + tokens[3] + ", fuel: " + tokens[4]);
                	if(storeNameExists(tokens[1]))
                	{
                		if(!stores.get(indexOfStoreName(tokens[1])).droneIDExists(tokens[2]))
                		{
                			stores.get(indexOfStoreName(tokens[1])).addDrone(tokens[2],tokens[3],tokens[4]);
                			System.out.println("OK:change_completed");
                		}
                		else
                		{
                			System.out.println("ERROR:drone_identifier_already_exists ");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist ");
                	}
                } 
               
                else if (tokens[0].equals("display_drones")) 
                {
                	if(storeNameExists(tokens[1]))
                	{
                		stores.get(indexOfStoreName(tokens[1])).displayDrones();
                	}
                	
                } 
                else if (tokens[0].equals("fly_drone")) 
                {
                	if(storeNameExists(tokens[1]))
                	{
                		if(stores.get(indexOfStoreName(tokens[1])).droneIDExists(tokens[2]))
                		{
                    		if(pilotAccountExists(tokens[3]))
                    		{
                    			if(stores.get(indexOfStoreName(tokens[1])).droneIsFlying(tokens[2]))
                    			{
                    				dronePilots.get(indexOfDroneIDCurrentPilot(tokens[2])).removeDrone();
                    			}
                    			stores.get(indexOfStoreName(tokens[1])).assignDroneToPilot(tokens[2],dronePilots.get(indexOfDronePilotAccountID(tokens[3])));
                    			System.out.println("OK:change_completed");
                    		}
                    		else
                    		{
                    			System.out.println("ERROR:pilot_identifier_does_not_exist ");
                    		}
                		}
                		else
                		{
                			System.out.println("ERROR:drone_identifier_does_not_exist");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist ");
                	}
                } 
                else if (tokens[0].equals("make_customer")) 
                {
                    System.out.print("account: " + tokens[1] + ", first_name: " + tokens[2] + ", last_name: " + tokens[3]);
                    System.out.println(", phone: " + tokens[4] + ", rating: " + tokens[5] + ", credit: " + tokens[6]);

                } else if (tokens[0].equals("display_customers")) {
                    System.out.println("no parameters needed");

                } else if (tokens[0].equals("start_order")) {
                    System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", drone: " + tokens[3] + ", customer: " + tokens[4]);

                } else if (tokens[0].equals("display_orders")) {
                    System.out.println("store: " + tokens[1]);

                } else if (tokens[0].equals("request_item")) {
                    System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", item: " + tokens[3] + ", quantity: " + tokens[4] + ", unit_price: " + tokens[5]);

                } else if (tokens[0].equals("purchase_order")) {
                    System.out.println("store: " + tokens[1] + ", order: " + tokens[2]);

                } else if (tokens[0].equals("cancel_order")) {
                    System.out.println("store: " + tokens[1] + ", order: " + tokens[2]);

                } else if (tokens[0].equals("transfer_order")) {
                    System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", new_drone: " + tokens[3]);

                } else if (tokens[0].equals("display_efficiency")) {
                    System.out.println("no parameters needed");

                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
    
    // Returns true if the store name already exists
    public boolean storeNameExists(String storeName)
    {
    	boolean result = false;
    	for(int i = 0; i < stores.size(); i++)
    	{
    		if(stores.get(i).storeName.equals(storeName))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
    
    public void sortStoreNames()
    {
    	ArrayList<String> storeNames = new ArrayList<>();
    	ArrayList<String> index = new ArrayList<>();
    	for(int i = 0; i < stores.size(); i++)
    	{
    		System.out.println("name:" + stores.get(i).storeName + ",revenue:" + stores.get(i).revenue);
    	}
    }
    
    public int indexOfStoreName(String storeName)
    {
    	int index = 0;
    	for(int i = 0; i < stores.size(); i++)
    	{
    		if(stores.get(i).storeName.equals(storeName))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
    
    // Returns true if the pilot account ID already exists
    public boolean pilotAccountExists(String accountID)
    {
    	boolean result = false;
    	for(int i = 0; i < dronePilots.size(); i++)
    	{
    		if(dronePilots.get(i).accountID.equals(accountID))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
    
    // Returns true if the pilot license ID already exists
    public boolean pilotLicenseExists(String licenseID)
    {
    	boolean result = false;
    	for(int i = 0; i < dronePilots.size(); i++)
    	{
    		if(dronePilots.get(i).licenseID.equals(licenseID))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
    
    public void sortPilotAccounts()
    {
    	for(int i = 0; i < dronePilots.size(); i++)
    	{
    		System.out.println("name:" + dronePilots.get(i).firstName + "_" + dronePilots.get(i).lastName + ",phone:" + dronePilots.get(i).phoneNumber + ",taxID:" + dronePilots.get(i).taxID + ",licenseID:" + dronePilots.get(i).licenseID+ ",experience:" + dronePilots.get(i).experience);
    	}
    }
    
    // Returns the index of drone pilot with the current pilot account ID
    public int indexOfDronePilotAccountID(String accountID)
    {
    	int index = 0;
    	for(int i = 0; i < dronePilots.size(); i++)
    	{
    		if(dronePilots.get(i).accountID.equals(accountID))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
    
    
    // Returns the index of drone pilot assigned to the current drone ID
    public int indexOfDroneIDCurrentPilot(String droneID)
    {
    	int index = 0;
    	for(int i = 0; i < dronePilots.size()-1; i++)
    	{
    		if(dronePilots.get(i).droneID.equals(droneID))
    		{
    			index = i;
    		}
    	}
    	return index;
    }
}
