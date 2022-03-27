# Taxi Service Pet Project
#### Describe

This application implements the possibility of registering a user who can
log in accordingly, can create car manufacturers, cars, new drivers, assign drivers to the car.
The application writes logs during authorization, there is also a filter that redirects from the main page
to the authorization / registration page if the user is not authorized. The application is built on the Servlet API


#### Project designed on 3-layer architecture:

1. Controllers (Presentation tier)
2. Services (Application logic tier)
3. DAO (Data tier)


#### Technologies::

1. Tomcat 
2. MYSQL  
4. JSTL 
5. JSP 
6. HTML, CSS 
7. JDBC 
8. Maven
9. Log4j


#### Steps to launch the application:

1. You have to install MySQL ,and you can initialize tables with commands from the resources/initialization_db.sql file
2. Change in /util/ConnectionUtil.java class fields "URL", "user" and "password" to access the created table
3. In src/core/resources/log4j2.xml in line <File name="LogToFile" File name="path"> you need to change "path" to your absolute path to the file .log
4. Configure and run Apache Tomcat
