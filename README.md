Malabe Tuk-Tuk & Three-Wheeler Spare Parts Depot Management System

A JavaFX-based desktop application developed to manage inventory, dealers, and point-of-sale (POS) operations for a three-wheeler spare parts business. The system demonstrates object-oriented programming principles, file handling, searching and sorting algorithms, and a graphical user interface using JavaFX.

## Running the Application

Requirements

- Java JDK 21
- JavaFX SDK 21
- IntelliJ IDEA (recommended)

 Steps

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Configure the JavaFX SDK if it is not already configured.
4. Ensure the required VM options for JavaFX are set.
5. Run the application by executing:

Launcher.java

The Launcher class starts the JavaFX application and opens the main management interface.

Overview

The application was developed as part of a Java coursework project to replace manual record-keeping with a digital management system. It allows staff to manage spare parts inventory, maintain dealer records, process customer purchases, and automatically generate audit logs.

Features

Inventory Management

Add new spare parts
Edit existing part details
Delete spare parts
Search parts by:
Part ID
Part Name
Category
Sort inventory by:
Category
Part ID
Automatic low stock detection

Inventory persistence using text files
Dealer Management
Add new dealers
Edit dealer information
Delete dealers
Search dealers
Sort dealers by location
Store dealer records in text files

Point of Sale (POS)
Select parts from inventory
Enter purchase quantities
Add items to shopping cart
Remove selected cart items
Clear entire cart
Checkout system
Automatic inventory quantity updates after checkout


Prevent purchasing more items than available stock
Discount System

Bulk Discount
Applies a 5% discount when purchasing 3 or more units of the same part
Applied individually to qualifying cart items

Synergy Discount
Applies an additional 10% discount to the entire cart when it contains:
At least one Engine part
At least one Electrical part

Discount Summary

Displays:

Bulk discount status
Synergy discount status
Total savings
Final discounted total
Audit Logging

Every important system action is automatically recorded into an audit log, including:

Adding inventory items
Updating inventory
Deleting inventory
Dealer management actions
Customer checkout transactions

Logs are stored in a text file for future reference
