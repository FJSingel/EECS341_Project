


	import java.sql.DriverManager;
	import java.sql.Connection;
	import java.sql.SQLException;
	import java.sql.*;
	import java.util.*;
	 
public class CostcoProject 
{	 
	  public static void main(String[] argv)
	  {
	 
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
	 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
	 
		try {
			//This uses mysql to connect to the "mydb" database with credentials "root" and "password"
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/costcoproject","root", "password");
	 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
			System.out.println("You've made it, take control of your database now!\n");
			//end loop 
		} else {
			System.out.println("Failed to make connection!");
		}

		Scanner kb = new Scanner(System.in);
		boolean live = true;
		System.out.println("Taking input");
		while(live)
		{	
			System.out.print("Main:");
			String input = kb.nextLine();
			System.out.println(input);
			if(input.equals("/?") || input.equals("help"))
			{
				System.out.println("Possible Commands:");
				System.out.println("AddItem\t --This registers a new item into the database");
				System.out.println("AddStore\t --This registers a new Store into the database");
				System.out.println("AddVendor\t --This registers a new Vendor into the database");
				System.out.println("AddCustomer\t --This registers a new Customer into the database");
				System.out.println("ListContents\t --This command will list all entities in a table");
				System.out.println("ListTables\t --This command will list all tables");
				System.out.println("OpenCustomer\t --This command will open a basket for a customer");
				System.out.println("CountContents\t --This command will count the number of entries in a table");
				System.out.println("AddToBasket\t --This command will add items to a basket");
				System.out.println("Restock\t --This command will restock a store with a product");
				System.out.println("Checkout\t --This command will generate a receipt for a basket and remove items from stock");
				System.out.println("RestockAlerts\t --This command will generate a list of every item at 5 stock or less");
				System.out.println("HotItems\t --This command will generate a list of most popular items");
				System.out.println("MostExpensive\t --This command will generate a list of the 10 most expensive items by MSRP");
				System.out.println("Cheapest\t --This command will generate a list of the least expensive items in stockXX");
				System.out.println("BRANDS?X?X?X");
				System.out.println("ViewReceipts\t --This command will list all receipts for perusal");
				System.out.println("Quit");
			}else if(input.equals("AddItem"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (";
					System.out.print("\nMSRP: ");
					rawInstr += kb.nextLine() + ", \'";
					System.out.print("\nName: ");
					rawInstr += kb.nextLine() + "\', \'";
					System.out.print("\nCategory: ");
					rawInstr += kb.nextLine() + "\', ";
					System.out.print("\nMass: ");
					rawInstr += kb.nextLine() + ", \'";
					System.out.print("\nBrand: ");
					rawInstr += kb.nextLine() + "\');";
					instruction.executeUpdate(rawInstr);
					System.out.println("Added item successfully!");
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Item not added.");
					e.printStackTrace();
				}
			}else if(input.equals("AddStore"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO store (Address, Phone, Hours) VALUES (\'";
					System.out.print("\nAddress: ");
					rawInstr += kb.nextLine() + "\', ";
					System.out.print("\n(No -'s)Phone: ");
					rawInstr += kb.nextLine() + ", \'";
					System.out.print("\nHours: ");
					rawInstr += kb.nextLine() + "\')";
					instruction.executeUpdate(rawInstr);
					System.out.println("Added store successfully!");
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Store not added.");
					e.printStackTrace();
				}
			}else if(input.equals("AddVendor"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO vendor (Name, Address, Phone) VALUES (\'";
					System.out.print("\nName: ");
					rawInstr += kb.nextLine() + "\', \'";
					System.out.print("\nAddress: ");
					rawInstr += kb.nextLine() + "\',";
					System.out.print("\n(No -'s)Phone: ");
					rawInstr += kb.nextLine() + ")";
					instruction.executeUpdate(rawInstr);
					System.out.println("Added vendor successfully!");
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Vendor not added.");
					e.printStackTrace();
				}
			}else if(input.equals("AddToBasket"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO Basket_has_item (Item_Iid, Basket_Bid, Quantity, ActualPrice) VALUES (";
					System.out.print("\nItem ID: ");
					rawInstr += kb.nextLine() + ", ";
					System.out.print("\nBasket ID: ");
					String Bid = kb.nextLine();
					rawInstr += Bid + ", ";
					System.out.print("\nQuantity: ");
					String Quantity = kb.nextLine();
					rawInstr += Quantity + ", ";
					System.out.print("\nActual Price: ");
					String Actual = kb.nextLine();
					rawInstr += Actual + ")";

					instruction.executeUpdate(rawInstr);
					
					//Keep running total in basket
					instruction.executeUpdate("update basket set Total = Total + ("+Quantity+"*"+Actual+") where Bid = "+Bid+";");
					
					System.out.println("Item added to cart successfully!");
				}
				catch (SQLException e) {
					System.out.println("Invalid addition. Item not added.");
					e.printStackTrace();
				}
			}else if(input.equals("AddCustomer"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO customer (PhoneNum, Email, Address, CreditCardNum, Name) VALUES (";
					System.out.print("\n(No -'s)Phone: ");
					rawInstr += kb.nextLine() + ", \'";
					System.out.print("\nEmail: ");
					rawInstr += kb.nextLine() + "\', \'";
					System.out.print("\nAddress: ");
					rawInstr += kb.nextLine() + "\', ";
					System.out.println("\nCredit Card Number: ");
					rawInstr += kb.nextLine() + ", \'";
					System.out.println("Name: ");
					rawInstr += kb.nextLine() + "\')";
					instruction.executeUpdate(rawInstr);
					System.out.println("Added customer successfully!");
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Customer not added.");
					e.printStackTrace();
				}
			}else if(input.equals("Checkout"))
			{
				try {
					System.out.print("\nID of Basket to checkout: ");
					String Bid = kb.nextLine();
					Statement instruction = connection.createStatement();
					String rawInstr = "SELECT DISTINCT item.Iid FROM basket_has_item AS basket, item AS item, "
						+ " store_has_item as store, storecustomer_has_basket as owned WHERE basket.quantity > store.quantity"
						+ " AND basket.Item_Iid = item.Iid AND store.Item_Iid = item.Iid AND owned.Basket_Bid = basket.Basket_Bid "
						+ "AND owned.Store_Sid = store.store_sid AND owned.Basket_Bid = " + Bid + ";";
					
					//Print all items possible
					ResultSet resultat = instruction.executeQuery(rawInstr);
					System.out.println("\nStock more of the following items before checking out\n-------------------------------------");
					
					boolean allStocked = true;
					
					//print each column until no columns left to print.
					while(resultat.next()){
						int count = 1;
						while(true)
						{
							try
							{
								System.out.print(resultat.getString(count) + "\t");
								allStocked = false;
								count++;
							}
							catch(SQLException e)
							{
								System.out.println();
								break;
							}
						}
					}
					
					if(allStocked)
					{
						System.out.println("(None)\nChecked out customer successfully!");
						
						//remove purchased stock
						ResultSet contents = instruction.executeQuery("SELECT Distinct basket.Item_Iid, store.Store_sid, basket.Quantity FROM basket_has_item as basket, storecustomer_has_basket as owned, "
								+"store_has_item as store WHERE store.Item_Iid = basket.Item_Iid AND owned.Basket_Bid = basket.Basket_Bid AND "
								+"owned.Store_Sid = store.store_sid AND owned.Basket_Bid = "+Bid+";");
						while(contents.next()){
							try
							{
								Statement instruction2 = connection.createStatement();
								String IiD = contents.getString(1);
								String SiD = contents.getString(2);
								String QTY = contents.getString(3);
								instruction2.executeUpdate("update store_has_item set Quantity = Quantity - "+QTY+" where Item_IiD = "+IiD+" AND Store_SiD = "+SiD+";");
							}
							catch(SQLException e)
							{
								System.out.println("Something broke while decrementing");
								e.printStackTrace();
								break;
							}
						}
						
						Statement instruction3 = connection.createStatement();
						//generate receipt
						ResultSet receipt = instruction3.executeQuery("SELECT Distinct item.name, basket.Item_Iid, basket.Quantity, "
								+"basket.ActualPrice FROM basket_has_item as basket, storecustomer_has_basket as owned, store_has_item as store, "
								+"item as item WHERE store.Item_Iid = basket.Item_Iid AND owned.Basket_Bid = basket.Basket_Bid "+
								"AND owned.Store_Sid = store.store_sid AND item.Iid = basket.Item_Iid AND owned.Basket_Bid = "+Bid+";");
						
						double subtotal = 0;
						
						String tab = "Bill of Receipt\n----------------\n";
						while(receipt.next()){
							try
							{
								String Name = receipt.getString(1);
								String Iid = receipt.getString(2);
								String QTY = receipt.getString(3);
								String Price = receipt.getString(4);
								tab += Name + "("+Iid+")\t $" + Price + "x" + QTY + " = " + (Double.parseDouble(Price) * Integer.parseInt(QTY)) + "\n";
								subtotal += (Double.parseDouble(Price) * Integer.parseInt(QTY));
								

							}
							catch(SQLException e)
							{
								System.out.println("Something broke while generating receipt");
								System.out.println(tab);
								e.printStackTrace();
								break;
							}
						}
						tab += "\nTotal: " + subtotal;
						instruction3.executeUpdate("update storecustomer_has_basket set Receipt = \'"+tab+"\' where Basket_BiD = "+Bid+";");
					}else
					{
						System.out.println("Insufficient stock to fill order");
					}
					
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Customer not checked out.");
					e.printStackTrace();
				}
			}else if(input.equals("Restock"))
			{
				try {
					//Create entry in vendor supplies
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO vendor_supplies_item_to_store (Vendor_Vid, Item_Iid, Store_Sid, Price, Quantity, Date) VALUES (";
					System.out.print("\nVendor ID: ");
					String Vid = kb.nextLine();
					rawInstr += Vid + ", ";
					System.out.print("\nItem ID: ");
					String Iid = kb.nextLine();
					rawInstr += Iid + ", ";
					System.out.print("\nStore ID: ");
					String Sid = kb.nextLine();
					rawInstr += Sid + ", ";
					System.out.print("\nPrice: ");
					String price = kb.nextLine();
					rawInstr += price + ", ";
					System.out.print("\nQuantity: ");
					String QTY = kb.nextLine();
					rawInstr += QTY + ", \'";
					System.out.print("\nDate: ");
					String Date = kb.nextLine();
					rawInstr += Date + "\')";
					
					
					instruction.executeUpdate(rawInstr);
					System.out.println("Placed restock successfully!");
					
					//Check if store has stocked this before
					ResultSet contents = instruction.executeQuery("SELECT Distinct Item_Iid, Store_Sid, Quantity FROM store_has_item WHERE Item_Iid = "+Iid+" AND Store_Sid = " + Sid);
					//If no prior results
					if (!contents.first())
					{
						instruction.executeUpdate("INSERT INTO store_has_item (Store_Sid, Item_Iid, Quantity) VALUES ("+Sid+", "+Iid+", "+QTY+")");
					}else {
						instruction.executeUpdate("update store_has_item set Quantity = Quantity + "+QTY+" where Item_IiD = "+Iid+" AND Store_SiD = "+Sid+";");
					}
					System.out.println("Increased stock successfully!");
					
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Order not stocked.");
					e.printStackTrace();
				}
				
				
			}else if(input.equals("Sudo"))
			{
				try {
					Statement instruction = connection.createStatement();
					System.out.println("Enter direct SQL command:");
					String rawInstr = kb.nextLine();
					instruction.executeUpdate(rawInstr);
					System.out.println("Command processed successfully!");
				}
				catch (SQLException e) {
					System.out.println("Command not processed.");
					e.printStackTrace();
				}
			}else if(input.equals("ListTables"))
			{
				System.out.println("Vendor");
				System.out.println("Store");
				System.out.println("Item");
				System.out.println("Basket");
				System.out.println("Customer");
				System.out.println("Store_has_Item");
				System.out.println("Basket_has_Item");
				System.out.println("StoreCustomer_has_basket");
				System.out.println("Vendor_Supplies_Item_to_Store");
				System.out.println("StoreCustomer_has_Basket");
				
			}else if(input.equals("ListContents"))
			{
				try {
					Statement instruction = connection.createStatement(); 
					
					String rawInstr = "SELECT * FROM ";
					System.out.print("\nTable to List: ");
					String title = kb.nextLine();
					rawInstr += title;
					
					//Print all items possible
					ResultSet resultat = instruction.executeQuery(rawInstr);
					
					//print each column until no columns left to print.
					PrintQuery(title, resultat);
					
				}
				catch (SQLException e) {
					System.out.println("Instructions Failed!");
				}
			}else if(input.equals("CountContents"))
			{
				System.out.print("\nTable to Count: ");
				String title = kb.nextLine();
				System.out.print("\n"+title+": " + CountContents(title, connection));
				
			}else if(input.equals("RestockAlerts"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "SELECT * FROM store_has_item where Quantity <= 5";
					ResultSet resultat = instruction.executeQuery(rawInstr);
					PrintQuery(" SID | IID | QTY", resultat);
				} catch (SQLException e) {
					System.out.println("Instructions Failed!");
				}
			}else if(input.equals("MostExpensive"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "SELECT * FROM costcoproject.item ORDER BY MSRP DESC limit 10;";
					ResultSet resultat = instruction.executeQuery(rawInstr);
					PrintQuery(" IID | MSRP | Name | Category | Mass | Brand ", resultat);
				} catch (SQLException e) {
					System.out.println("Instructions Failed!");
				}

			}else if(input.equals("HotItems"))
			{
				try {
					Statement instruction = connection.createStatement();
					String rawInstr = "SELECT item . *, SUM(basket.quantity) as QTY FROM Basket_has_item as basket, item as item WHERE Basket_Bid IN (SELECT Basket_Bid FROM storecustomer_has_basket WHERE Receipt != '') AND item.Iid = basket.Item_Iid GROUP BY IID;";
					ResultSet resultat = instruction.executeQuery(rawInstr);
					PrintQuery(" IID | MSRP | Name | Category | Mass | Brand | QTY ", resultat);
				} catch (SQLException e) {
					System.out.println("Instructions Failed!");
				}

			}
			else if(input.equals("OpenCustomer"))
			{
				try {
					Statement makeBasket = connection.createStatement();
					makeBasket.executeUpdate("INSERT INTO Basket (Total) VALUES (0)");
					System.out.println("Empty basket made.");
					
					Statement instruction = connection.createStatement();
					String rawInstr = "INSERT INTO StoreCustomer_has_Basket (Store_Sid, Customer_PhoneNum, Basket_Bid, Receipt) VALUES (";
					System.out.print("\nStore's ID: ");
					rawInstr += kb.nextLine() + ", ";
					System.out.print("\nCustomer's Phone#: ");
					
					//Query newest basket number
					int basketNum = CountContents("basket", connection);
					rawInstr += kb.nextLine() + ", " + basketNum + ", \'\')";
					instruction.executeUpdate(rawInstr);
					System.out.println("Added customer successfully with basket " + basketNum + "!");//*/
				}
				catch (SQLException e) {
					System.out.println("Poor parameters input. Customer not opened.");
					e.printStackTrace();
				}
			}else if(input.equals("Quit"))
			{
				live = false;
			}
			else{
				System.out.println("Command unrecognized. Try \'/?\' for help.");
			}
		}
		
		/*/populate database
		try {
			Statement instruction = connection.createStatement();

			//Initialization of database (ONLY LET THIS RUN ONCE as each run accumulates these if not commented out)
			//This will populate the database chosen above if you don't have data points to use
			/*
			instruction.executeUpdate("INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (5.0, 'Banana', 'Food', 50, 'Dole');");
			instruction.executeUpdate("INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (3.50, 'Milk', 'Food', 550, 'Dairyland');");
			instruction.executeUpdate("INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (2.50, 'Captain Crunch', 'Food', 250, 'GM');");
			instruction.executeUpdate("INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (1.99, 'Butter', 'Food', 200, 'Dairyland');");
			instruction.executeUpdate("INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (4.0, 'Apple', 'Food', 250, 'Dole');");
			instruction.executeUpdate("INSERT INTO item (MSRP, Name, Category, Mass, Brand) VALUES (3.3, 'Grapes', 'Food', 350, 'Dole');");
			//*


		}
		catch (SQLException e) {
			System.out.println("Population Failed!");
			e.printStackTrace();
			return;
		}//*
		
		try {
			Statement instruction = connection.createStatement(); 
			
			//Print all items possible
			ResultSet resultat = instruction.executeQuery("SELECT Iid,MSRP,Name,Category,Mass,Brand FROM item");
			System.out.println("\nItems\n-------------------");
			while(resultat.next()){
				System.out.println(resultat.getString(1) + "\t" + resultat.getString(2) + "\t" + resultat.getString(3) + "\t" + resultat.getString(4) + "\t" + resultat.getString(5) + "\t" + resultat.getString(6));
			}
			
		}
		catch (SQLException e) {
			System.out.println("Instructions Failed!");
			e.printStackTrace();
		}//*/
		System.out.println("\nDone");
	  }	
	  public static int CountContents(String title, Connection connection)
	  {
		  try {
				Statement instruction = connection.createStatement(); 
				
				String rawInstr = "SELECT COUNT(*) FROM " + title;
				
				//Print all items possible
				ResultSet resultat = instruction.executeQuery(rawInstr);
				resultat.next();
				return Integer.parseInt(resultat.getString(1));
			}
			catch (SQLException e) {
				System.out.println("Instructions Failed!");
				e.printStackTrace();
				return -1;
			}
	  }
	  public static void PrintQuery(String title, ResultSet query)
	  {
		  
			System.out.println("\n"+title+"\n-----------------------------------------------------------------");
			
			//print each column until no columns left to print.
			try {
				while(query.next()){
					int count = 1;
					while(true)
					{
						try
						{
							String rawOut = query.getString(count);
							rawOut = rawOut.replace("\n", "");
							System.out.print(rawOut + "\t");
							count++;
						}
						catch(SQLException e)
						{
							System.out.println();
							break;
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
}
