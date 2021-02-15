# Project Members: Lucy Van Kleunen, Bryce Ikeda, Aman Satya


# Project Video Link (Google Drive)
https://drive.google.com/file/d/1rOho9Ycv53AmOG16WbOziSLjYJqQwVqR/view?ts=5fca6dc8

# Instructions for running the code:

As is, the code should run a Java application with no special set up instructions. On the login screen, simply click "Login" and you should be able to enter the application with no verification step. Then you can interact with the game as shown in our demo video.

# Instructions for running the code with the database connection:

To run the code with the database connection for the login, uncomment the lines in LoginScreen.java that create a database connection and send a database query on login. Use the below mentioned credentials to login.

Username: blackjack
Password: blackjack

# Instructions for setting up the database:
The platform that was have used for developing this application is " Intellij". Below are the steps for setting up the database:
1. Navigate to "DATABASE" in Intellij.
2. Click on the " Data Sources and Drivers" , there select the type of Driver you want to use, in this case " Sqlite".
3. Once you are done with that, you could the see the location of your database.
4. Download "sqlite.JDBC.3.7.2.jar" from online. Here is the link for that http://www.java2s.com/Code/Jar/s/Downloadsqlitejdbc372jar.htm
5. *IMPORTANT* Keep this jar file inside the project environment.
6. The "identifier.sqlite:main.db is our database that you will find the src folder. Upload that to the  environment so that you can access the table.
7. Use the username and password mentioned above, for the login page.
