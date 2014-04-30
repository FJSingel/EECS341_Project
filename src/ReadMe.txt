README
Frank Singel
EECS 341 Project

General information

This is interfaced through a generic command prompt.
All previously generated data is in the MWB file, along with the schema.

Command List

/?: Brings up possible commands.
AddItem: This registers a new item into the database.
AddStore: This registers a new Store into the database.
AddVendor: This registers a new Vendor into the database.
AddCustomer: This registers a new Customer into the database.
ListContents: This command will list all entities in a table.
ListTables: This command will list all tables.
OpenCustomer: This command will open a basket for a customer.
CountContents: This command will count the number of entries in a table.
AddToBasket: This command will add items to a basket.
Restock: This command will restock a store with a product.
Checkout: This command will generate a receipt for a basket and remove items from stock.
RestockAlerts: This command will generate a list of every item at 5 stock or less.
HotItems: This command will generate a list of most popular items.
MostExpensive: This command will generate a list of the 10 most expensive items by MSRP.
Cheapest: This command will generate a list of the 10 least expensive items in stock.
ViewReceipts: This command will list all receipts for perusal.
ViewOpenBaskets: This command will list all basket contents that haven't been purchased.
SudoQuery: This command will allow you to produce your own queries to the DB.
Quit: Exits the client.