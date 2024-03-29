//////////////////////////////////////////////////////////////////////////////////////////
//
// Project: grocery_express
//
// Author: Aubrey Savage
//
// Class: DeliveryService
// 
// Notes: Used the base code provided on CS6310 Git Hub
//
//////////////////////////////////////////////////////////////////////////////////////////

package edu.gatech.cs6310;
import java.util.Scanner;
import java.util.TreeMap;

public class DeliveryService {
	
	public TreeMap<String,Store> stores = new TreeMap<String,Store>();
	public TreeMap<String,DronePilot> dronePilots = new TreeMap<String,DronePilot>();
	public TreeMap<String,Customer> customers = new TreeMap<String,Customer>();
	
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

                if (wholeInputLine.substring(0,2).equals( "//"))
                {
                	// do nothing
                }
                
                else if (tokens[0].equals("make_store")) 
                {
                	String storeName = tokens[1];
                	String revenue = tokens[2];
                	
                	if(!stores.containsKey(storeName))
                	{
                		stores.put(storeName,new Store(storeName, revenue));
                        System.out.println("OK:change_completed");
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_already_exists");
                	}
                } 
                
                else if (tokens[0].equals("display_stores")) 
                {
                	displayStoreNames();
                    System.out.println("OK:display_completed");

                } 
                
                else if (tokens[0].equals("sell_item")) 
                {
                	String storeName = tokens[1];
                	String itemName = tokens[2];
                	String itemWeight = tokens[3];
                	
                	if(stores.containsKey(storeName))
                	{
                		if(!stores.get(storeName).items.containsKey(itemName))
                		{
                			stores.get(storeName).addItem(itemName,itemWeight);
                			System.out.println("OK:change_completed");
                		}
                		else
                		{
                			System.out.println("ERROR:item_identifier_already_exists");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}

                } 
                else if (tokens[0].equals("display_items")) 
                {
                	String storeName = tokens[1];
                	
                	if(stores.containsKey(storeName))
                	{
                		stores.get(storeName).displayItems();
                		System.out.println("OK:display_completed");
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}

                } 
                else if (tokens[0].equals("make_pilot")) 
                {
                	String accountID = tokens[1];
                	String firstName = tokens[2];
                	String lastName = tokens[3];
                	String phoneNumber = tokens[4];
                	String taxID = tokens[5];
                	String licenseID = tokens[6];
                	String experience = tokens[7];
                	
                    if(!dronePilots.containsKey(accountID))
                	{
                    	if(!pilotLicenseExists(licenseID))
                    	{
                    		dronePilots.put(accountID,new DronePilot(accountID,firstName,lastName,phoneNumber,taxID,licenseID,experience));
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
                	displayPilotAccounts();
                    System.out.println("OK:display_completed");
                } 
                else if (tokens[0].equals("make_drone")) 
                {
                	String storeName = tokens[1];
                	String droneID = tokens[2];
                	String capacity = tokens[3];
                	String fuel = tokens[4];
                	
                	if(stores.containsKey(storeName))
                	{
                		if(!stores.get(storeName).drones.containsKey(droneID))
                		{
                			stores.get(storeName).addDrone(droneID,capacity,fuel);
                			System.out.println("OK:change_completed");
                		}
                		else
                		{
                			System.out.println("ERROR:drone_identifier_already_exists");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                } 
               
                else if (tokens[0].equals("display_drones")) 
                {
                	String storeName = tokens[1];
                	if(stores.containsKey(storeName))
                	{
                		stores.get(storeName).displayDrones();
                		System.out.println("OK:display_completed");
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                	
                } 
                else if (tokens[0].equals("fly_drone")) 
                {
                	String storeName = tokens[1];
                	String droneID = tokens[2];
                	String accountID = tokens[3];
                	
                	if(stores.containsKey(storeName))
                	{
                		if(stores.get(storeName).drones.containsKey(droneID))
                		{
                			if(dronePilots.containsKey(tokens[3]))
                			{
                    			if(stores.get(storeName).drones.get(droneID).getCurrentlyFlying())
                    			{
                    				dronePilots.get(droneIDCurrentPilot(droneID)).removeDrone();
                    			}
                    			stores.get(storeName).assignDroneToPilot(droneID,dronePilots.get(accountID));
                    			System.out.println("OK:change_completed");
                    		}
                    		else
                    		{
                    			System.out.println("ERROR:pilot_identifier_does_not_exist");
                    		}
                		}
                		else
                		{
                			System.out.println("ERROR:drone_identifier_does_not_exist");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                } 
                else if (tokens[0].equals("make_customer")) 
                {
                	String userName = tokens[1];
                	String firstName = tokens[2];
                	String lastName = tokens[3];
                	String phoneNumber = tokens[4];
                	String rating = tokens[5];
                	String credit = tokens[6];
                	
                	if(!customers.containsKey(userName))
                	{
                		customers.put(userName,new Customer(userName,firstName,lastName,phoneNumber,rating,credit));
                		System.out.println("OK:change_completed");
                	}
                	else
                	{
                		System.out.println("ERROR:customer_identifier_already_exists");
                	}
                } 
                else if (tokens[0].equals("display_customers")) 
                {
                	displayCustomerUserNames();
                	System.out.println("OK:display_completed");

                } 
                else if (tokens[0].equals("start_order")) 
                {
                    String storeName = tokens[1];
                	String orderID = tokens[2];
                	String droneID = tokens[3];
                	String userName = tokens[4];
                	
                	if(stores.containsKey(storeName))
                	{
                    	if(!stores.get(storeName).orders.containsKey(orderID))
                    	{
                    		if(stores.get(storeName).drones.containsKey(droneID))
                    		{
                    			if(customers.containsKey(userName))
                        		{
                        			stores.get(storeName).assignOrderToDrone(orderID,droneID,userName);
                        			System.out.println("OK:change_completed");
                        		}
                    			else
                    			{
                    				System.out.println("ERROR:customer_identifier_does_not_exist");
                    			}
                    		}
                    		else
                    		{
                    			System.out.println("ERROR:drone_identifier_does_not_exist");
                    		}
                    	}
                    	else
                    	{
                    		System.out.println("ERROR:order_identifier_already_exists");
                    	}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                }
                else if (tokens[0].equals("display_orders")) 
                {
                	String storeName = tokens[1];
                	if(stores.containsKey(storeName))
                	{
                		stores.get(storeName).displayOrders();
                		System.out.println("OK:display_completed");
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                } 
                else if (tokens[0].equals("request_item")) 
                {
                    String storeName = tokens[1];
                	String orderID = tokens[2];
                	String itemName = tokens[3];
                	int quantity = Integer.parseInt(tokens[4]);
                	int unitPrice = Integer.parseInt(tokens[5]);
                	
                	if(stores.containsKey(storeName))
                	{
                		if(stores.get(storeName).orders.containsKey(orderID))
                		{
                			if(stores.get(storeName).items.containsKey(itemName))
                    		{
                				if(!stores.get(storeName).orders.get(orderID).lines.containsKey(itemName))
                				{
                					String currentUserName = stores.get(storeName).orders.get(orderID).getAssignedCustomerUserName();
                				
                					if(customers.get(currentUserName).getCredits() >= (quantity*unitPrice))
                					{
                					
                						String droneID = stores.get(storeName).findDroneIDOfCurrentOrder(orderID);
                						int totalWeight = (quantity * stores.get(storeName).items.get(itemName).getItemWeight());
                						
                						if(stores.get(storeName).availableSpaceOnDrone(droneID,totalWeight))
                						{
                							stores.get(storeName).addItemToOrder(itemName,quantity,unitPrice,orderID);
                							System.out.println("OK:change_completed");
                						}
                						else
                						{
                							System.out.println("ERROR:drone_cant_carry_new_item");
                						}
                					}
                					
                					else
                					{
                						System.out.println("ERROR:customer_cant_afford_new_item");
                					}
                				}
                				else
                				{
                					System.out.println("ERROR:item_already_ordered");
                				}
                    		}
                			else
                			{
                				System.out.println("ERROR:item_identifier_does_not_exist");
                			}
                		}
                		else
                		{
                			System.out.println("ERROR:order_identifier_does_not_exist");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                } 
                else if (tokens[0].equals("purchase_order")) 
                {
                	String storeName = tokens[1];
                	String orderID = tokens[2];
                	
                	if(stores.containsKey(storeName))
                	{
                		if(stores.get(storeName).orders.containsKey(orderID))
                		{
                			String currentDroneID = stores.get(storeName).findDroneIDOfCurrentOrder(orderID);
                			String currentPilot = stores.get(storeName).drones.get(currentDroneID).getCurrentPilotAccountID();
                			String currentUserName = stores.get(storeName).orders.get(orderID).getAssignedCustomerUserName();
                			
                			if(currentPilot != "")
                			{
                				if(stores.get(storeName).drones.get(currentDroneID).getTripsUntilMaintenance() > 0)
                				{
                					int totalPrice = stores.get(storeName).orders.get(orderID).calculateTotalPrice();
                					customers.get(currentUserName).purchaseOrder(totalPrice);
                					stores.get(storeName).orderPurchased(orderID);
                					dronePilots.get(currentPilot).deliverOrder();
                					System.out.println("OK:change_completed");
                				}
                				else
                				{
                					System.out.println("ERROR:drone_needs_fuel");
                				}
                			}
                			else
                			{
                				System.out.println("ERROR:drone_needs_pilot");
                			}
                		}
                		else
                		{
                			System.out.println("ERROR:order_identifier_does_not_exist");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}
                } 
                else if (tokens[0].equals("cancel_order")) 
                {
                	String storeName = tokens[1];
                	String orderID = tokens[2];
                	
                	if(stores.containsKey(storeName))
                	{
                		if(stores.get(storeName).orders.containsKey(orderID))
                		{
                			stores.get(storeName).orderCancelled(orderID);
                			System.out.println("OK:change_completed");
                		}
                		else
                		{
                			System.out.println("ERROR:order_identifier_does_not_exist");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}

                } 
                else if (tokens[0].equals("transfer_order")) 
                {
                    String storeName = tokens[1];
                    String orderID = tokens[2];
                    String newDroneID = tokens[3];
                    
                	if(stores.containsKey(storeName))
                	{
                		if(stores.get(storeName).orders.containsKey(orderID))
                		{
                			if(stores.get(storeName).drones.containsKey(newDroneID))
                			{
                				String assignedDroneID = stores.get(storeName).orders.get(orderID).getAssignedDroneID();
                				if(assignedDroneID.equals(newDroneID))
                				{
                					System.out.println("OK:new_drone_is_current_drone_no_change");
                				}
                				else if(stores.get(storeName).transferOrder(orderID,newDroneID))
                				{
                					System.out.println("OK:change_completed");
                				}
                				else
                				{
                					System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
                				}
                			}
                			else
                			{
                				System.out.println("ERROR:drone_identifier_does_not_exist");
                			}
                		}
                		else
                		{
                			System.out.println("ERROR:order_identifier_does_not_exist");
                		}
                	}
                	else
                	{
                		System.out.println("ERROR:store_identifier_does_not_exist");
                	}

                } 
                else if (tokens[0].equals("display_efficiency")) 
                {
                	displayEfficiency();
                    System.out.println("OK:display_completed");

                } 
                else if (tokens[0].equals("stop")) 
                {
                    System.out.println("stop acknowledged");
                    break;

                } 
                else 
                {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
    
///////////////////////////////////////////////////////////////
// Purpose: Returns true if the pilot license ID already exists
///////////////////////////////////////////////////////////////
    public boolean pilotLicenseExists(String licenseID)
    {
    	boolean result = false;
    	for (String i : dronePilots.keySet())
    	{
    		if(dronePilots.get(i).getLicenseID().equals(licenseID))
    		{
    			result = true;
    		}
    	}
    	return result;
    }
    
    
/////////////////////////////////////////////////////////////////////////////////
// Purpose: Returns the account ID of drone pilot assigned to the current drone ID
/////////////////////////////////////////////////////////////////////////////////
    
    public String droneIDCurrentPilot(String droneID)
    {
    	String accountID = "";
    	for (String i : dronePilots.keySet())
    	{
    		if(dronePilots.get(i).getDroneID().equals(droneID))
    		{
    			accountID = i;
    		}
    	}
    	return accountID;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////
//
// Display Functions
//
//////////////////////////////////////////////////////////////////////////////////////////
    
    public void displayStoreNames()
    {
    	for (String i : stores.keySet())
    	{
    		System.out.println("name:" + i + ",revenue:" + stores.get(i).getRevenue());
    	}
    }
    
    public void displayCustomerUserNames()
    {
    	for (String i : customers.keySet())
    	{
    		System.out.println("name:" + customers.get(i).firstName + "_" + customers.get(i).lastName + 
    				",phone:" + customers.get(i).phoneNumber + 
    				",rating:" + customers.get(i).getCustomerRating() + 
    				",credit:" + customers.get(i).getCredits());
    	}
    }
    
    public void displayEfficiency()
    {
       	for (String i : stores.keySet())
    	{
       		System.out.println("name:"+stores.get(i).getStoreName() +
    			",purchases:" + stores.get(i).getTotalPurchases() +
    			",overloads:" + stores.get(i).getTotalOverload() +
    			",transfers:" + stores.get(i).getTotalTransfers());
    	}
    }
    
    public void displayPilotAccounts()
    {
    	for (String i : dronePilots.keySet())
    	{
    		System.out.println("name:" + dronePilots.get(i).firstName + "_" + dronePilots.get(i).lastName + 
    				",phone:" + dronePilots.get(i).phoneNumber + 
    				",taxID:" + dronePilots.get(i).getTaxID() + 
    				",licenseID:" + dronePilots.get(i).getLicenseID() + 
    				",experience:" + dronePilots.get(i).getExperience());
    	}
    }

}
