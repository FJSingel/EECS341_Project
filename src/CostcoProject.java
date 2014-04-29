


	import java.sql.DriverManager;
	import java.sql.Connection;
	import java.sql.SQLException;
	import java.sql.*;
	import java.util.*;
	import java.io.*;
	 
public class CostcoProject {	 
	  public static void main(String[] argv) {
	 
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root", "password");
	 
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
			String input = kb.nextLine();
			System.out.println(input);
			if(input.equals("/?"))
			{
				System.out.println("Possible Commands:");
				System.out.println("AddItem\t --This registers a new item into the database");
				System.out.println("List\t --This command will list all entities in a table");
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
					return;
				}
			}else if(input.equals("List"))
			{
				try {
					Statement instruction = connection.createStatement(); 
					
					String rawInstr = "SELECT * FROM ";
					System.out.print("\nTable to List: ");
					String title = kb.nextLine();
					rawInstr += title;
					
					//Print all items possible
					ResultSet resultat = instruction.executeQuery(rawInstr);
					System.out.println("\n"+title+"\n-------------------");
					
					//print each column until no columns left to print.
					while(resultat.next()){
						int count = 1;
						while(true)
						{
							try
							{
								System.out.print(resultat.getString(count) + "\t");
								count++;
							}
							catch(SQLException e)
							{
								System.out.println();
								break;
							}
						}
					}
					
				}
				catch (SQLException e) {
					System.out.println("Instructions Failed!");
					e.printStackTrace();
					return;
				}
			}else if(input.equals("Quit"))
			{
				live = false;
			}
			else{
				System.out.println("Command unrecognized. Try \'/?\' for help.");
			}
		}
		
		//populate database
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
			//*/


		}
		catch (SQLException e) {
			System.out.println("Population Failed!");
			e.printStackTrace();
			return;
		}
		
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
			return;
		}
		System.out.println("\nDone");
	  }	
}
