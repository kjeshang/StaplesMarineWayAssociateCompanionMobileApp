# StaplesMarineWayAssociateCompanionMobileApp
> **Kunal Jeshang, Technology Sales Area Representative** (_Project Timeline:_ May 2023 to August 2023)

## Table of Contents
1. [Problem Statement](#Problem-Statement)
2. [Recommended Solution](#Recommended-Solution)
3. [Technology Requirements](#Technology-Requirements)
4. [Database](#Database)
5. [Application Flow](#Application-Flow)
6. **[Video Demonstration](#Video-Demonstration)** (_Skip to this section to see how the app actually works!_)
7. [Concluding Remarks](#Concluding-Remarks)

## Problem Statement
As a Technology Sales Area Representative (i.e., Tech Sales Associate), it is my job to connect, share, and partner with customers to help meet their technology requirements, and by extension, facilitate transactions for technology items to meet customer requirements. The nature of this role may focus on customer interaction and negotiation, although in order to successfully perform the aforementioned, an Associate must have a strong understanding on where to locate items in the store; regardless whether it is a technology item or not. This is because a customer does not have any way to differentiate between a Tech Associate, Office Essentials/Aisles Associate, and a Print Shop Associate. A customer would simply approach the closest person wearing a Staples Uniform with an Associate name tag. Therefore, in order to maintain an optimal level of customer service, constructing a business solution to swiftly and efficiently meet the requirements of customers will help. This is especially the case for newly hired and part-time associates where they may lack the seasoned experience of precisely knowing where items are located in the store. Furthermore, having a referential app would assist associates to retrieve items in a systematic manner upon customer interaction and/or through preparing “Buy Online Pickup In-Store” (BOPIS) orders.

Another significant aspect of this role is to perform basic inventory management in the event that there are no customers in the store, or there are customers that did not require assistance and wish to shop independently. Basic inventory management pertains to systematically populating shelves with products, and binning excess products in designated stock locations. In the event products are removed from their stock locations to place on the sales floor or to sell a customer, the inventory levels must be updated accordingly in the AS400 system. Conversely, in the event that a products need to be placed back into their stock locatins, the inventory levels must also be updated in the AS400 system. This system can be accessed on the desktop computers in the store, or remotely using a Motorola scanner. In theory, the aforementioned is okay but in reality it can take a lot of time for associate to walk to a computer and access AS400. Also, despite having a scanner to check and update inventory levels, the device was ancient and the battery ended up running out of charge with 1 to 2 hours, which is not practical in a full work day. Having a mobile app to keep track of binning of specific products & quantities would prove beneficial, and then updates could be manually inputted at the end of the work day on a desktop computer using the AS400 system.

## Recommended Solution
As the associate is required to fully mobile throughout the store to best interact with customers, retrieve items, and prepare pickup orders in advance of customer pickup. Considering that the role is required to be roam around the store freely, it would be more beneficial to create a mobile application. 

As my current experience with mobile application development involves using Java and SQLite to create an Android application. Thus, I will use the aforementioned technology stacks. Furthermore, working in a retail environment can be fast paced and at times involve a lot of multi-tasking. Any oppurtunity to reduce lag time would be advantageous, as in a retail environment, customers tend to lack patience. Thus, programming a native mobile application for the Android ecosystem would be advantageous in terms of speed. Having a server-less database such as SQLite is the only option for the purpose of reducing the likelihood of data leakage of internal store information when using a database hosted online (e.g., Firebase). Furthermore, I opt to not make the mobile application available on the Android store as this is not a certified business tool of Staples Canada. If there are any other members of the Staples Marine Way branch that wish to utilize this application, I would load it on to their mobile phone directly from my computer. The only condition would be that they have an Android phone.

The mobile application is essentially a simple CRUD (Create, Read, Update, Delete) application with a SQLite backend. As hinted earlier, the mobile application would serve as a repository of products containing SKU, name, location in store, specifications, and other related information. In addition, a "to-do list" feature would be available to assist with binning procedures to make inventory management more seamless and quick.

## Technology Requirements
* Computer
    * ASUS Vivobook Windows 11
* Software
    * Java Development Kit 20
    * Android Studio Flamingo
* Programming Language
    * Java
* Design Pattern & Architecture
    * Object Oriented Programming
    * Singleton Database

## Database
* Database Name = ProductData.db
* Database Table Names = Product, Bin

Below is a breakdown of the **Product** table columns.
|Column|Abbreviated Name|Data Type|Primary Key?|Additional Notes|
|--|--|--|--|--|
|Item Number||Text|Yes|SKU of product|
|Item Name||Text|No|Name of the product|
|Typical Store Location|Store Location|Text|No|Where the product would be located in terms of shelf and/or bin location|
|General Notes||Text|No|Useful notes, user stories, use cases, and opinions regarding the product that could help inform the customer make a purchase.|
|Staples Website Link|Website Link|Text|No|Contains the direct Staples website link of the product, which can be useful to gain an in-depth understanding of product description & specifications. The link is also helpful as it contains an image of the product.|
|YouTube Review Video Link|Review Video Link|Text|No|A YouTube video link that can be used to provide an alternate-objective opinion regarding a product.|

Below is a breakdown of the **Bin** table columns.
|Column|Abbreviated Name|Data Type|Primary Key?|Additional Notes|
|--|--|--|--|--|
|Bin ID||Integer|Yes|Autoincremented Numerical identification for each bin entry.|
|Stock In Count||Integer|No|Quantity of item that must be kept in an inventory (i.e., bin) location due to excess, lack of space, and/or unspecified location on the sales floor.|
|Stock Out Count||Integer|No|Quantity of item that are extracted to give a customer and/or place on the sales floor on an aisle shelf.|
|Item Name||Text|No|Name of the product.|
|Item Number||Text|No; Foreign Key|Identical to Item Number in Product table.|

## Application Flow

### Classes

|Class Name|Layout|Description|
|--|--|--|
|DatabaseHelper.java|N/A|This is the Java class that instantiates the database, and performs the following. <ul> <li>Creates the product & bin tables</li> <li>Creates columns/variables of the column table</li> <li>Construct create, read, update, and delete functions that apply the respective SQL statements</li> </ul>|
|Product.java|N/A|This is the Java class that instantiates the “Product” object whereby its variables align with the database columns. Further functions such as constructors, getters, setters, and a toString method are created.|
|Product_RecyclerViewAdapter.java & Product_RecyclerViewInterface.java|N/A|These are the Java classes that help generate a resource effective listview layout of both products and bin entries in the respective layouts.|
|MainActivity_v2.java, MainActivity.java (_deprecated_)|activity_main_v2.xml, recycler_view_row.xml, activity_main.xml (_deprecated_), listview_layout.xml (_deprecated_), |This Java class helps to render the main screen of the application. Specifically, the list of products that is selectable for viewing/editing, search bar, and add product button. It utilizes both the DatabaseHelper and Product classes.|
|AddActivity.java|activity_add.xml|This Java class helps to render the add product screen of the application. One would be able to insert a new non-existing product into the database. It utilizes both the DatabaseHelper and Product classes.|
|EditActivity.java|activity_edit.xml|Upon selection of the list view in the main activity screen, the item number of the selected product is saved for further information to be retrieved and populated in the edit activity screen. This Java class helps to render an existing product’s information as well as facilitate updates to product information. Deletion of productions can also be done as well. It utilizes both the DatabaseHelper and Product classes.|
|BinEntry.java|N/A|This is the Java class that instantiates the “BinEntry” object whereby its variables align with the database columns. Further functions such as constructors, getters, setters, and a toString method are created.|
|SearchableAdapter.java|N/A|This Java class helps to render the searchable text field in the add bin entry screen. This text field helps to find names of products that one wishes to bin-in or bin-out.|
|BinActivity.java|activity_bin.xml, recycler_view_row.xml|This Java class helps to render the bin entry screen of the application. Specifically, the list of product bin entries that is selectable for viewing/editing, filteration parameters via a radio button list (i.e., all entries, stock-in count, stock-out count), and add bin entry button. It utilizes both the DatabaseHelper and BinEntry classes.|
|BinAddActivity.java|activity_bin_add.xml|This Java class helps to render the add bin entry screen of the application. One would be able to insert a new non-existing bin entry into the database, given that the product related to that bin exists in the product table. It utilizes both the DatabaseHelper and Product classes.|
|BinEditActivity.java|activity_bin_edit.xml|Upon selection of the list view in the bin activity screen, the bin entry's identification number is saved for further information to be retrieved and populated in the bin edit activity screen. This Java class helps to render an existing bin entry's details as well as facilitate updates for stock-in & stock-out count. Deletion of bin entries can also be done as well. It utilizes both the DatabaseHelper and BinEntry classes.|

### Layout Screens
Below are the XML layouts that are used by the above mentioned classes to render the screens in the mobile application.

* The **activity_main.xml** and **listview_layout.xml** layouts that are used by MainActivity.java. However, both the Java class and XML layouts are deprecated and not used in the current version of the application.
    
    |activity_main.xml|listview_layout.xml|
    |--|--|
    |![activity_main.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/activity_main%20XML.png?raw=true)|![listview_layout.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/listview_layout%20XML.png?raw=true)|
    
* The **activity_main_v2.xml** and **recycler_view_row.xml** layouts are used by MainActivity_v2.java.

    |activity_main_v2.xml|recycler_view_row.xml|
    |--|--|
    |![activity_main_v2.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Significant%20Update/activity_main_v2.png?raw=true)|![recycler_view_row.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Significant%20Update/recycler_view_row.png?raw=true)|

* The **activity_add.xml** layout used by AddActivity.java.

    ![activity_add.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/activity_add%20XML.png?raw=true)

* The **activity_edit.xml** layout used by EditActivity.java

    ![activity_edit.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/activity_edit%20XML.png?raw=true)

* The **activity_bin.xml** and **recycler_view_row.xml** layouts used by BinActivity.java.

    |activity_bin.xml|recycler_view_row.xml|
    |--|--|
    |![activity_bin.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Significant%20Update/activity_bin.png?raw=true)|![recycler_view_row.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Significant%20Update/recycler_view_row.png?raw=true)|

* The **activity_bin_add.xml** layout used by BinAddActivity.java.

    ![activity_bin_add.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Significant%20Update/activity_bin_add.png?raw=true)

* The **activity_bin_edit.xml** layout used by BinEditActivity.java.

    ![activity_bin_edit.xml](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Significant%20Update/activity_bin_edit.png?raw=true)

### Application Screenshots

Please refer to the [Application Screenshot Annotations](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Application%20Screenshot%20Annotations.pdf) PDF document containing the same screenshots shown in the table below in the event the image quality and/or annotation legibility is not optimal.

|Screenshot|Description|
|--|--|
|![1_Startup Application.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/1.png?raw=true)|The splash screen of the application.|
|![2_MainActivity.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/2.png?raw=true)|The first version for the main screen of the application.|
|![2_MainActivity_v2.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/3.png?raw=true)|The current version of the main screen of the application.|
|![3_MainActivity - Search by SKU.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/4.png?raw=true)|The first version for the main screen when filtering products by SKU.|
|![3_MainActivity_v2 - Search by SKU](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/5.png?raw=true)|The current version of the main screen when filtering products by SKU.|
|![4_MainActivity - Search by Product Name.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/6.png?raw=true)|The first version of the main screen when filtering products by item name.|
|![4_MainActivity_v2 - Search by Product Name](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/7.png?raw=true)|The current version of the main screen when filtering products by item name.|
|![5_AddActivity.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/8.png?raw=true)|The add product screen.|
|![6_EditActivity - Edit Mode not toggled.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/9.png?raw=true)|The update product screen when edit mode is not toggled.|
|![7_EditActivity - Edit Mode toggled.png](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/10.png?raw=true)|The update product screen when edit mode is toggled.|
|![8_BinActivity](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/11.png?raw=true)|The product binning list as it would appear with bin entries.|
|![9_BinAddActivity](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/12.png?raw=true)|The add product bin entry screen without details provided.|
|![10_BinAddActivity - Search by Product Name](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/13.png?raw=true)|Search for product to bin using item name.|
|![10_BinAddActivity - Bin Entry Completed](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/14.png?raw=true)|Product bin entry completed.|
|![11_BinEditActivity](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/Application%20Screenshot%20Annotations%20-%20Updated/15.png?raw=true)|The edit product bin entry screen.|

## Video Demonstration

Below is a YouTube video showing a video demonstration of how the **Current Version** of the application works. ***Please click on the image below to be redirected to the video***.

[![Staples Marine Way Associate Companion App (UPDATED) Demonstration Video by Kunal Jeshang](https://github.com/kjeshang/StaplesMarineWayAssociateCompanionMobileApp/blob/main/Screenshots/thumbnail.png?raw=true)](https://youtu.be/jZBAHgg20pI "Staples Marine Way Associate Companion App (UPDATED) Demonstration Video by Kunal Jeshang")

To see a demonstration video of the first version of the application, [Click Here](https://youtu.be/OnMvtPF6_20).

## Concluding Remarks
Working on this project was a fruitful experience as I was able to connect the skills I gained from my post-secondary education and apply it in my new role. With Staples stores typically being of a large square footage, having a mobile application to assist associates of all experience levels to systematically retrieve items for customers would not only standardize but enhance service quality. If Staples were to use such a mobile application, then it would have to host a database server that the mobile application would retrieve information from. I am thankful to have been able to revise my mobile application development, database, and Java programming skills by completing this project. I look forward to learning about more products around the Staples store, inputting new products into the mobile application, and using it to systematically create, share, and partner with customers to best meet their requirements. I am thankful for having supportive managers and colleagues that were open to have a look at my project. I look forward to learn and grow with the Staples Marine Way team.
